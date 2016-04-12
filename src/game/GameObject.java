package game;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public abstract class GameObject
{
	protected Geometry _geom = null;
	protected Material _mat = null;


	public GameObject()
	{
		
	}

	public void initializeGameObject(AssetManager assetManager, ColorRGBA color, Mesh mesh, String objectName)
	{
		// Create an object from the mesh
		_geom = new Geometry(objectName, mesh);
		// Create a simple blue material
		_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		_mat.setColor("Color", color);
		// Give the object the blue material
		_geom.setMaterial(_mat);
	}
	
	public Geometry getGeometry()
	{
		return _geom;
	}
	
	public abstract void initializeGraphic();
}
