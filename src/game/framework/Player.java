package game.framework;

import java.util.ArrayList;
import java.util.Enumeration;

import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import java.util.Collections;

import game.GameManager;
import game.GameObject;

public class Player extends GameObject
{
	/**
	 * Player Attributes
	 */
	// Name of the player
	protected String			_name	= null;
	// Room the player is in
	protected Room				_room	= null;
	// Items the player is carrying
	protected ArrayList<Object>	_items	= null;
	// Player Movement Speed
	protected float _speed = 1.5f;
	// Minimum action Distance
	protected float _actionDistance = 2f;
	
	/**
	 * Player Constructor
	 */
	public Player()
	{
		System.out.println("Player Created");

		_items = new ArrayList<Object>();
	}

	/**
	 * Getters
	 */
	public float getActionDistance()
	{
		return (_actionDistance);
	}

	public Room getCurrentRoom()
	{
		return (_room);
	}

	/**
	 * Initialize Player
	 * @param name Player name
	 * @param startingItem Player starting items
	 */
	public void initialize(String name, ArrayList<Object> startingItem)
	{
		System.out.println("Player: Initializing...");

		_name = name;
		if (startingItem != null)
			_items.addAll(startingItem);
	}

	/**
	 * Initialize Player Graphics
	 */
	@Override
	public void initializeGraphic()
	{
		System.out.println("Player: Initializing Graphic...");

		// Player Color
		ColorRGBA playerColor = ColorRGBA.Cyan;
		// Player Graphics
		Box playerMesh = new Box(1, 1, 1);
		this.initializeGameObject(GameManager.getInstance().getAssetManager(), playerColor, playerMesh, _name);
		_geom.scale(0.5f, 0.5f, 0.5f);
		// Player Position
		_geom.setLocalTranslation(0, _geom.getLocalScale().y, 0);
	}

	/**
	 * Initialize Player Inputs
	 */
	public void initializeInput()
	{
		InputManager inputManager = GameManager.getInstance().getInputManager();

		// Movement Inputs
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_G));
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_F));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_H));
		// Action Inputs
		inputManager.addMapping("LClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("RClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));

		// Listeners
		inputManager.addListener(analogListener, "Up", "Down", "Left", "Right");
		inputManager.addListener(actionListener, "LClick", "RClick");
	}

	// Analog Listener
	private AnalogListener analogListener = new AnalogListener()
	{
		public void onAnalog(String name, float value, float tpf)
		{
			boolean isRunning = GameManager.getInstance().getGameState();
			Geometry player = GameManager.getInstance().getPlayer().getGeometry();
			// If Inputs enabled
			if (isRunning)
			{
				// Moving Up
				if (name.equals("Up"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x, v.y, v.z - value * _speed);
					GameManager.getInstance().getPlayer().notifyObserver();
				}
				// Moving Down
				if (name.equals("Down"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x, v.y, v.z + value * _speed);
					GameManager.getInstance().getPlayer().notifyObserver();
				}
				// Moving Right
				if (name.equals("Right"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x + value * _speed, v.y, v.z);
					GameManager.getInstance().getPlayer().notifyObserver();
				}
				// Moving Left
				if (name.equals("Left"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x - value * _speed, v.y, v.z);
					GameManager.getInstance().getPlayer().notifyObserver();
				}
			}
			else
			{
				System.out.println("Game paused");
			}
		}
	};

	/**
	 * Action Listener
	 */
	private ActionListener actionListener = new ActionListener()
	{
		public void onAction(String name, boolean isPressed, float tpf)
		{
			boolean isRunning = GameManager.getInstance().getGameState();
			// If Inputs enabled
			if (isRunning)
			{
				// If there is a mouse click
				if ((name.equals("LClick") || name.equals("RClick")) && isPressed)
				{
					// Preparing for a Ray cast
					Node rootNode = GameManager.getInstance().getRootNode();
					Camera cam = GameManager.getInstance().getGameCamera().getCamera();
					InputManager inputManager = GameManager.getInstance().getInputManager();
					
					CollisionResults results = new CollisionResults();
					// Get Cursor Position on the window
					Vector2f click2d = inputManager.getCursorPosition();
					// Get Cursor 3D Position
					Vector3f click3d = cam.getWorldCoordinates(
							new Vector2f(click2d.getX(), click2d.getY()), 0f);

					// Calculate Cursor Direction
					Vector3f dir = cam.getWorldCoordinates(
							new Vector2f(click2d.getX(), click2d.getY()), 1f).
							subtractLocal(click3d);
					// Ray cast from the cursor position
					Ray ray = new Ray(click3d, dir);
					
					// Save collided objects from the scene in results
					rootNode.collideWith(ray, results);
					
					// If there is a clicked object
					if (results.size() > 0)
					{
						Geometry target = results.getClosestCollision().getGeometry();
						Door door = null;
						Player currentPlayer = GameManager.getInstance().getPlayer();
						// Check if there is a Door
						door = currentPlayer.getCurrentRoom().getDoorByGeometry(target);
						
						// If it's a door
						if (door != null)
						{
							// On Left click
							if (name.equals("LClick"))
							{
								// If Door is closed
								if (door.isClosed())
								{
									System.out.println("Openning Door !");
									door.open();
									target.getMaterial().setColor("Color", ColorRGBA.Green);
								}
								else
								{
									System.out.println("Closing Door !");
									door.close();
									target.getMaterial().setColor("Color", ColorRGBA.Red);
								}
							}
							// On Right Click
							if (name.equals("RClick"))
							{
								Vector3f pLoc = currentPlayer.getGeometry().getWorldTranslation();
								Vector3f dLoc = door.getGeometry().getWorldTranslation();
								// Calculate distance between Player and Door
								Vector3f d = new Vector3f(FastMath.abs(pLoc.x) - FastMath.abs(dLoc.x),
										FastMath.abs(pLoc.y) - FastMath.abs(dLoc.y),
										FastMath.abs(pLoc.z) - FastMath.abs(dLoc.z));
								float distance = FastMath.sqrt(d.x * d.x + d.y * d.y + d.z * d.z);
								
								// If Player can't interact with the Door
								if (distance > currentPlayer.getActionDistance())
									{
										System.out.println("Too far");
										return ;
									}
								// Change Room
								Room nextRoom = door.getOtherRoom(currentPlayer.getCurrentRoom());
								if (nextRoom != null)
								{
									Room lastRoom = null;
									lastRoom = currentPlayer.moveToRoom(nextRoom);
									GameManager.getInstance().changeScene(lastRoom);
								}
							}
						}
					}
					else
					{
						System.out.println("Selection: Nothing" );
					}
				}
			}
			else
			{
				System.out.println("Game paused");
			}
		}
	};
	
	/**
	 * Notify Observers
	 * Observer Pattern
	 */
	@Override
	public void notifyObserver()
	{
		for (int i = 0; i < _obs.size(); i++)
		{
			_obs.get(i).update(this.getGeometry().getWorldTranslation());
		}
	}
	
	/**
	 * Returns an enumeration of the items the player is carrying
	 * Iterator Pattern
	 */
	public Enumeration<Object> items()
	{
		return Collections.enumeration(_items);
	}

	// Returns true if the player has the item i
	public boolean hasItem(Object i)
	{
		return (_items.contains(i));
	}

	// Adds item i to player's inventory if item i is in the room
	public void takeItem(Object i)
	{
		_items.add(i);
	}

	// Removes item i from player's inventory and drops it in the room
	public void dropItem(Object i)
	{
		_items.remove(i);
	}

	// Moves player to room r; returns room exited
	public Room moveToRoom(Room r)
	{
		// Save the last room to return it later
		Room lastRoom = _room;

		// Exit current room
		if (lastRoom != null)
			lastRoom.exit(this);
		// Enter new room
		r.enter(this);
		// Save the new room in Player object
		_room = r;

		// Player Position
		_geom.setLocalTranslation(0, _geom.getLocalScale().y, 0);

		// Return the last room
		return lastRoom;
	}
}
