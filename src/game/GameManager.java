package game;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import game.framework.Map;
import game.framework.Player;
import game.framework.Room;
import game.framework.factories.PlayerFactory;

public class GameManager
{
	/**
	 * Singleton pattern
	 */
	private static GameManager _instance = null;

	private GameManager()
	{
		System.out.println("GameManager Created");
	}

	public static synchronized GameManager getInstance()
	{
		if (_instance == null)
			_instance = new GameManager();
		return _instance;
	}

	/**
	 * GameManager attributes and methods
	 */
	// Reference to the asset Manager
	private AssetManager	_assetManager	= null;
	// Root node of the game
	private Node			_rootNode		= null;
	// Reference to the input Manager
	private InputManager	_inputManager	= null;
	// Game Map
	private Map				_map;
	// The player
	private Player			_player			= null;
	// Game State
	private boolean			_isRunning		= false;
	// Reference to Game Camera
	private Camera			_cam			= null;

	private FlyByCamera		_flyCam			= null;
	

	public AssetManager getAssetManager()
	{
		return (_assetManager);
	}

	public InputManager getInputManager()
	{
		return (_inputManager);
	}

	public Node getRootNode()
	{
		return (_rootNode);
	}
	
	public boolean getGameState()
	{
		return (_isRunning);
	}
	
	public Player getPlayer()
	{
		return (_player);
	}
	
	public Camera getCamera()
	{
		return (_cam);
	}
	
	public enum Direction
	{
		Up, Down, Left, Right
	};

	public void initialize(AssetManager assetManager, Node rootNode, InputManager inputManager,
			Camera cam, FlyByCamera flyCam)
	{
		System.out.println("GameManager: Initializing...");

		_assetManager = assetManager;
		_inputManager = inputManager;
		_rootNode = rootNode;
		_cam = cam;
		_flyCam = flyCam;

		_map = new Map();
		_map.initialize();

		// Initialize Player
		_player = PlayerFactory.getInstance().createPlayer();
		_player.initialize("Player 1", null);
	}

	public void initializeInput()
	{
		_player.initializeInput();
	}

	public void initializeGraphic()
	{
		_map.initializeGraphic();
		_player.initializeGraphic();
		
		Room startingRoom = (Room) (_map.getLocations().nextElement());

		this.changeScene(_player.moveToRoom(startingRoom));
		
		// Set the Camera location to have a top-down view
		Vector3f loc = new Vector3f(0f, 14f, 3f);
		_cam.setLocation(loc);
		// Look at the center
		_cam.lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, -90f));
		
		_flyCam.setDragToRotate(true);
		_inputManager.setCursorVisible(true);
	}

	public void pause()
	{
		_isRunning = false;
	}

	public void unPause()
	{
		_isRunning = true;
	}

	public void changeScene(Room previousRoom)
	{
		this.pause();
		
		Node newScene = _player.getCurrentRoom().getNode();

		_rootNode.detachAllChildren();
		_rootNode.attachChild(newScene);
		_rootNode.attachChild(_player.getGeometry());
		this.unPause();
	}

	public void update()
	{

	}

}
