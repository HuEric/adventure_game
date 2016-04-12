package game;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public abstract class GameScene extends GameObject
{
	/**
	 * Scene Node (every GameObject will be attached to it)
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

		// Randomly generate a color
		ColorRGBA color = ColorRGBA.randomColor();
		// Box Mesh
		Mesh mesh = new Box(1, 1, 1);
		
		// Initialize the floor with the mesh
		this.initializeGameObject(assetManager, color, mesh, floorName);
		
		// Scale and Translate the Floor to the position
		this.getGeometry().scale(floorLength, floorHeight, floorWidth);
		this.getGeometry().setLocalTranslation(0, -(_geom.getLocalScale().y), 0);
		// Attach the floor to the Scene Node
		this.addGameObjectToScene(this);
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
}
