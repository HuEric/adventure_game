package game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

import game.framework.Subject;

public class GameObject extends Subject implements IGameGraphic
{
	/**
	 * Graphics Attributes
	 */
	protected Geometry _geom = null;
	protected Material _mat = null;


	public GameObject()
	{
	}

	/**
	 * Initialize a Game Object
	 * @param assetManager	Reference to Asset Manager
	 * @param color			Color of the Game Object
	 * @param mesh			Mesh of the Game Object
	 * @param objectName	Game Object name
	 */
	public void initializeGameObject(AssetManager assetManager, ColorRGBA color, Mesh mesh, String objectName)
	{
		// Create an object from the mesh
		_geom = new Geometry(objectName, mesh);
		// Create a simple material
		_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		// Set the material color
		_mat.setColor("Color", color);
		// Give the object the material
		_geom.setMaterial(_mat);
	}
	
	/**
	 * Getters
	 */
	public Geometry getGeometry()
	{
		return _geom;
	}

	/**
	 * Initialize Graphics
	 */
	@Override
	public void initializeGraphic()
	{
		
	}
}
