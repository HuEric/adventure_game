package game;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import game.framework.Subject;

public abstract class GameScene extends Subject implements IGameGraphic
{
	/**
	 * Graphics Attributes
	 */
	protected GameObject	_floor = null;
	
	/**
	 * Scene Node (every GameObject composing the scene will be attached to it)
	 */
	protected Node _node = null;

	/**
	 * Floor properties
	 */
	protected float floorLength = 5f;
	protected float floorWidth = 5f;
	protected float floorHeight = 0.25f;

	public GameScene()
	{
	}

	/**
	 * Initialize Game Scene
	 * @param gameSceneName Game Scene Name
	 */
	public void initializeGameScene(String gameSceneName)
	{
		// Create a new Node with the name
		_node = new Node(gameSceneName);
	}

	/**
	 * Create a Floor for the Game Scene
	 * @param assetManager	Reference to the Asset Manager
	 * @param floorName		Floor name
	 */
	public void initializeFloor(AssetManager assetManager, String floorName)
	{
		System.out.println("GameScene: Initializing " + floorName + "...");

		_floor = new GameObject();
		
		// Randomly generate a color
		ColorRGBA color = ColorRGBA.randomColor();
		// Box Mesh
		Mesh mesh = new Box(1, 1, 1);
		
		_floor.initializeGameObject(assetManager, color, mesh, floorName);
		Geometry floorGeom = _floor.getGeometry();
		
		// Scale and Translate the Floor to the position
		floorGeom.scale(floorLength, floorHeight, floorWidth);
		floorGeom.setLocalTranslation(0, -(floorGeom.getLocalScale().y), 0);
		// Attach the floor to the Scene Node
		this.addGameObjectToScene(floorGeom);
	}
	
	/**
	 * Attach a Game Object to the scene Node
	 */
	public void addGameObjectToScene(GameObject gameObject)
	{
		_node.attachChild(gameObject.getGeometry());
	}

	public void addGameObjectToScene(Geometry gameObjectGeo)
	{
		_node.attachChild(gameObjectGeo);
	}

	/**
	 * Detach a Game Object to the scene Node
	 */
	public void removeGameObjectToScene(GameObject gameObject)
	{
		_node.detachChildNamed(gameObject.getGeometry().getName());
	}

	public void removeGameObjectToScene(Geometry gameObjectGeo)
	{
		_node.detachChildNamed(gameObjectGeo.getName());
	}

	/**
	 * Getters
	 */
	public Node getNode()
	{
		return (_node);
	}
	
	public Geometry getGeometry()
	{
		return _floor.getGeometry();
	}
}
