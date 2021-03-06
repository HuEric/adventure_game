package game.framework;

import java.util.ArrayList;
import java.util.Enumeration;

import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;

import java.util.Collections;

import game.GameManager;
import game.framework.factories.DoorFactory;
import game.framework.factories.DoorWithLockFactory;
import game.framework.factories.WallFactory;

public class Room extends MapSite
{
	/**
	 * Room Attributes
	 */
	// Room number
	protected int				_number;
	// Room name
	protected String			_name;
	// Room's walls
	protected ArrayList<Wall>	_walls		= null;
	// Player in the Room
	protected ArrayList<Player>	_players	= null;
	// Objects in the Room
	protected ArrayList<Object>	_things		= null;

	/**
	 * Room Constructor
	 * @param number Room number
	 */
	public Room(int number)
	{
		System.out.println("Room " + number + " Created");

		_number = number;
		_name = "Room " + number;
		_walls = new ArrayList<Wall>();
		_players = new ArrayList<Player>();
		_things = new ArrayList<Object>();
	}

	/**
	 * Initialize Room
	 * Room contains 4 walls
	 */
	public void initialize()
	{
		int wallNumber = 4;
		
		for (int i = 0; i < wallNumber; i++)
		{
			// Create Wall
			Wall newWall = WallFactory.getInstance().createWall();
			newWall.initialize(null, "Wall " + wallNumber);
			_walls.add(newWall);
		}
	}

	/**
	 * Initialize Room Graphics
	 */
	@Override
	public void initializeGraphic()
	{
		System.out.println(_name + ": Initializing Graphic...");

		// Initialize Game Scene
		this.initializeGameScene(_name);
		// Create Floor Graphics
		String floorName = "Floor " + _name;
		this.initializeFloor(GameManager.getInstance().getAssetManager(), floorName);
		
		// Create Wall Graphics
		int wallNumber = 4;
		for (int i = 0; i < wallNumber; i++)
		{
			_walls.get(i).initializeGraphic();
		}

		// Left Wall Position
		Geometry leftWall = _walls.get(0).getGeometry();
		leftWall.setLocalTranslation(-(5 - leftWall.getLocalScale().x), leftWall.getLocalScale().y, 0);
		this.addGameObjectToScene(leftWall);

		// Top Wall Position
		Geometry upWall = _walls.get(1).getGeometry();
		upWall.setLocalTranslation(0, upWall.getLocalScale().y, -(5 - leftWall.getLocalScale().x));
		upWall.rotate(0, FastMath.DEG_TO_RAD * 90f, 0);
		this.addGameObjectToScene(upWall);

		// Right Wall Position
		Geometry rightWall = _walls.get(2).getGeometry();
		rightWall.setLocalTranslation((5 - rightWall.getLocalScale().x), rightWall.getLocalScale().y, 0);
		this.addGameObjectToScene(rightWall);

		// Down Wall Position
		Geometry downWall = _walls.get(3).getGeometry();
		downWall.setLocalTranslation(0, downWall.getLocalScale().y, (5 - downWall.getLocalScale().x));
		downWall.rotate(0, -FastMath.DEG_TO_RAD * 90f, 0);
		this.addGameObjectToScene(downWall);
	}
	
	/**
	 * Link two rooms and create the door between
	 * @param roomToLink Room to be linked to this one
	 * @param direction Position of the second Room from this one
	 * @param isLockedDoor Type of the Door
	 */
	public void roomToLinkWith(Room roomToLink, GameManager.Direction direction, boolean isLockedDoor)
	{
		int wallNumber = 0;
		switch (direction)
		{
		case Left:
			wallNumber = 0;
			break;
		case Up:
			wallNumber = 1;
			break;
		case Right:
			wallNumber = 2;
			break;
		case Down:
			wallNumber = 3;
			break;
		}

		Door newDoor;
		// Create Door following the type
		String doorName = "Door " + this._name + " to " + roomToLink.getName();
		if (isLockedDoor)
			newDoor = DoorWithLockFactory.getInstance().createDoor(this, roomToLink, true, doorName, false);
		else
			newDoor = DoorFactory.getInstance().createDoor(this, roomToLink, true, doorName);
		
		// Set the door to the wall
		this.setDoorOnWall(wallNumber, newDoor);
		
		// Find the opposite wall number
		if (wallNumber == 0 || wallNumber == 1)
			wallNumber += 2;
		else
			wallNumber -= 2;
		
		// Set the Door in the other Room
		roomToLink.setDoorOnWall(wallNumber, newDoor);
	}

	/**
	 * Set the Door to the Wall
	 * @param wallNumber The Wall number
	 * @param newDoor The Door to be assigned
	 */
	public void setDoorOnWall(int wallNumber, Door newDoor)
	{
		Wall currentWall = _walls.get(wallNumber);
		currentWall.setDoor(newDoor);
	}

	/**
	 * Find with the Geometry if the Door is from this Room
	 * @param doorGeo Door Geometry
	 * @return Door
	 */
	public Door getDoorByGeometry(Geometry doorGeo)
	{
		Enumeration<Wall> walls = Collections.enumeration(_walls);
		while (walls.hasMoreElements())
		{
			Wall currentWall = walls.nextElement();
			if (currentWall.getGeometry().getName() == doorGeo.getName())
				return currentWall.getDoor();
		}
		return null;
	}
	/**
	 * Getters
	 */
	public String getName()
	{
		return (_name);
	}
	
	/**
	 * Player Enter this Room
	 */
	@Override
	void enter(Player p)
	{
		System.out.println(_name + ": " + p._name + " entering");

		if (_players.contains(p) == false)
			_players.add(p);
	}

	/**
	 * Player Exit this Room
	 */
	@Override
	void exit(Player p)
	{
		_players.remove(p);
	}

	/**
	 *  Returns an enumeration of the players in the room
	 * @return Player List
	 */
	public Enumeration<Player> players()
	{
		return Collections.enumeration(_players);
	}

	/**
	 *  Returns true if player p is in the room
	 * @param p Player
	 * @return Result
	 */
	public boolean hasPlayer(Player p)
	{
		return _players.contains(p);
	}

	/**
	 *  Returns an enumeration of the movable things in the room
	 * @return Object List
	 */
	public Enumeration<Object> things()
	{
		return Collections.enumeration(_things);
	}

	/**
	 *  Returns true if the movable thing t is in the room
	 * @param t Object
	 * @return Result
	 */
	public boolean hasThing(Object t)
	{
		return _things.contains(t);
	}

	/**
	 *  Adds the movable thing t to the room
	 * @param t Object to be added
	 */
	public void addThing(Object t)
	{
		_things.add(t);
	}

	/**
	 *  Removes the movable thing t from the room
	 * @param t Object to be removed
	 */
	public void removeThing(Object t)
	{
		_things.remove(t);
	}
}
