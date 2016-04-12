package game.framework;

import java.util.ArrayList;
import java.util.Enumeration;

import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
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
	// Name of the player
	protected String			_name	= null;
	// Room the player is in
	protected Room				_room	= null;
	// Items the player is carrying
	protected ArrayList<Object>	_items	= null;
	// Player Movement Speed
	protected float _speed = 1.5f;
	
	// Non-solid collision shape
	protected GhostControl _sensor = null;
	
	public Player()
	{
		System.out.println("Player Created");

		_items = new ArrayList<Object>();
	}

	public void initialize(String name, ArrayList<Object> startingItem)
	{
		System.out.println("Player: Initializing...");

		_name = name;
		if (startingItem != null)
			_items.addAll(startingItem);
	}

	@Override
	public void initializeGraphic()
	{
		System.out.println("Player: Initializing Graphic...");

		ColorRGBA playerColor = ColorRGBA.Cyan;
		Box playerMesh = new Box(1, 1, 1);
		this.initializeGameObject(GameManager.getInstance().getAssetManager(), playerColor, playerMesh, _name);
		_geom.scale(0.5f, 0.5f, 0.5f);
		_geom.setLocalTranslation(0, _geom.getLocalScale().y, 0);
	}

	public void initializeInput()
	{
		InputManager inputManager = GameManager.getInstance().getInputManager();

		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_G));
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_F));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_H));
		inputManager.addMapping("LClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("RClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));

		inputManager.addListener(analogListener, "Up", "Down", "Left", "Right");
		inputManager.addListener(actionListener, "LClick", "RClick");
	}

	// Listener User input
	private AnalogListener analogListener = new AnalogListener()
	{
		public void onAnalog(String name, float value, float tpf)
		{
			boolean isRunning = GameManager.getInstance().getGameState();
			Geometry player = GameManager.getInstance().getPlayer().getGeometry();
			if (isRunning)
			{
				if (name.equals("Up"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x, v.y, v.z - value * _speed);
				}
				if (name.equals("Down"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x, v.y, v.z + value * _speed);
				}
				if (name.equals("Right"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x + value * _speed, v.y, v.z);
				}
				if (name.equals("Left"))
				{
					Vector3f v = player.getLocalTranslation();
					player.setLocalTranslation(v.x - value * _speed, v.y, v.z);
				}
			}
			else
			{
				System.out.println("Game paused");
			}
		}
	};

	private ActionListener actionListener = new ActionListener()
	{
		public void onAction(String name, boolean isPressed, float tpf)
		{
			boolean isRunning = GameManager.getInstance().getGameState();
			if (isRunning)
			{
				if ((name.equals("LClick") || name.equals("RClick")) && isPressed)
				{
					Node rootNode = GameManager.getInstance().getRootNode();
					Camera cam = GameManager.getInstance().getCamera();
					InputManager inputManager = GameManager.getInstance().getInputManager();
					
					CollisionResults results = new CollisionResults();
					Vector2f click2d = inputManager.getCursorPosition();
					Vector3f click3d = cam.getWorldCoordinates(
							new Vector2f(click2d.getX(), click2d.getY()), 0f);

					Vector3f dir = cam.getWorldCoordinates(
							new Vector2f(click2d.getX(), click2d.getY()), 1f).
							subtractLocal(click3d);
					
					Ray ray = new Ray(click3d, dir);
					
					rootNode.collideWith(ray, results);
					
					if (results.size() > 0)
					{
						Geometry target = results.getClosestCollision().getGeometry();
						Door door = null;
						Player currentPlayer = GameManager.getInstance().getPlayer();
						door = currentPlayer.getCurrentRoom().getDoorByGeometry(target);
						if (door != null)
						{
							if (name.equals("LClick"))
							{
								if (door.isOpen())
								{
									System.out.println("Open !");
									door.close();
									target.getMaterial().setColor("Color", ColorRGBA.Green);
								}
								else
								{
									System.out.println("Close !");
									door.open();
									target.getMaterial().setColor("Color", ColorRGBA.Red);
								}
							}
							if (name.equals("RClick"))
							{
								System.out.println("RClick");
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
	
	// Returns an enumeration of the items the player is carrying
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

		// Return the last room
		return lastRoom;
	}

	public Room getCurrentRoom()
	{
		return (_room);
	}
}
