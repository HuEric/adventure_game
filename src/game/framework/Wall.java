package game.framework;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import game.GameManager;
import game.GameObject;

public class Wall extends GameObject
{
	private Door _door = null;
	private String _name = null;
	
	public Wall()
	{
		
	}

	public void initialize(Door d, String name)
	{
		_door = d;
		_name = name;
	}
	
	public Door getDoor()
	{
		return _door;
	}
	
	public void setDoor(Door door)
	{
		_door = door;
	}

	@Override
	public void initializeGraphic()
	{
		if (_door != null)
		{
			if (_door instanceof DoorWithLock)
				((DoorWithLock)_door).initializeGraphic();
			else
				((Door)_door).initializeGraphic();
		}
		else
		{
			ColorRGBA wallColor = ColorRGBA.Gray;
			Mesh wallMesh = new Box(1, 1, 1);
			this.initializeGameObject(GameManager.getInstance().getAssetManager(), wallColor, wallMesh, _name);
			this.getGeometry().scale(0.1f, 1, 5f);
		}
	}
	
	@Override
	public Geometry getGeometry()
	{
		if (_door != null)
			return (_door.getGeometry());
		return _geom;
	}

}
