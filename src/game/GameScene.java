package game;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public abstract class GameScene extends GameObject
{
	protected Node _node = null;
	protected float floorLength = 5f;
	protected float floorWidth = 5f;
	protected float floorHeight = 0.25f;

	public GameScene()
	{
		// TODO Auto-generated constructor stub
	}

	public void initializeGameScene(String gameSceneName)
	{
		_node = new Node(gameSceneName);
	}

	public void initializeFloor(AssetManager assetManager, String floorName)
	{
		System.out.println("GameScene: Initializing " + floorName + "...");

		ColorRGBA color = ColorRGBA.White;
		Mesh mesh = new Box(1, 1, 1);
		
		this.initializeGameObject(assetManager, color, mesh, floorName);
		
		this.getGeometry().scale(floorLength, floorHeight, floorWidth);
		this.getGeometry().setLocalTranslation(0, -(_geom.getLocalScale().y), 0);
		this.addGameObjectToScene(this);
	}
	
	public void addGameObjectToScene(GameObject gameObject)
	{
		_node.attachChild(gameObject.getGeometry());
	}

	public void addGameObjectToScene(Geometry gameObjectGeo)
	{
		_node.attachChild(gameObjectGeo);
	}

	public void removeGameObjectToScene(GameObject gameObject)
	{
		_node.detachChildNamed(gameObject.getGeometry().getName());
	}

	public void removeGameObjectToScene(Geometry gameObjectGeo)
	{
		_node.detachChildNamed(gameObjectGeo.getName());
	}

	public Node getNode()
	{
		return (_node);
	}
}
