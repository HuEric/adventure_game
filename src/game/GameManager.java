package game;

import com.jme3.asset.AssetManager;
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
	// Game State (used to receive inputs)
	private boolean			_isRunning		= false;
	// Game Camera
	private GameCamera		_cam = null;
	

	/**
	 * Getters
	 */
	
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
	
	public GameCamera getGameCamera()
	{
		return (_cam);
	}
	
	
	public enum Direction
	{
		Up, Down, Left, Right
	};

	/**
	 * Initialize GameManager
	 * @param assetManager	Reference to Asset Manager
	 * @param rootNode		Reference to Root Node
	 * @param inputManager	Reference to Input Manager
	 * @param cam			Reference to Game Camera
	 * @param flyCam		Reference to Fly Camera
	 */
	public void initialize(AssetManager assetManager, Node rootNode, InputManager inputManager,
			Camera cam, FlyByCamera flyCam)
	{
		System.out.println("GameManager: Initializing...");

		// Set references
		_assetManager = assetManager;
		_inputManager = inputManager;
		_rootNode = rootNode;
		_cam = new GameCamera(cam, flyCam);

		// Initialize Game Map
		_map = new Map();
		_map.initialize();

		// Initialize Player
		_player = PlayerFactory.getInstance().createPlayer();
		_player.initialize("Player 1", null);
	}

	/**
	 * Initialize Inputs
	 */
	public void initializeInput()
	{
		// Initialize Player Inputs
		_player.initializeInput();
	}

	/**
	 * Initialize Graphics
	 */
	public void initializeGraphic()
	{
		// Initialize Map Graphics
		_map.initializeGraphic();
		// Initialize Player Graphics
		_player.initializeGraphic();
		
		// Get the first Room of the Map
		Room startingRoom = (Room) (_map.getLocations().nextElement());

		// Load the first Room
		this.changeScene(_player.moveToRoom(startingRoom));
		
		// Set the Camera location to have a top-down view
		Vector3f loc = new Vector3f(0f, 14f, 3f);
		_cam.setCameraLocation(loc);
		// Look at the center
		_cam.setCameraLookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, -90f));
		
		// Camera observe Starting Room
		_cam.setSubject(startingRoom);
		
		// Enable Cursor
		_cam.setDrag(true);
		_inputManager.setCursorVisible(true);
	}

	/**
	 * Pause the Game (disable inputs)
	 */
	public void pause()
	{
		_isRunning = false;
	}

	/**
	 * Resume the Game (enable inputs)
	 */
	public void unPause()
	{
		_isRunning = true;
	}

	/**
	 * Load a new Scene
	 * @param previousRoom
	 */
	public void changeScene(Room previousRoom)
	{
		// Disable Inputs before changing scene
		this.pause();

		// Get the new scene from player
		Room newRoom = _player.getCurrentRoom();
		Node newScene = newRoom.getNode();

		// Load graphically the new scene
		// Detach from root node previous scene
		_rootNode.detachAllChildren();
		_rootNode.attachChild(newScene);
		_rootNode.attachChild(_player.getGeometry());
		
		// Camera observe new scene
		_cam.changeScene(newRoom);
		
		// Enable Inputs once scene loaded
		this.unPause();
	}
}
