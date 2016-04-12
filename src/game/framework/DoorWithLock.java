package game.framework;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import game.GameManager;

public class DoorWithLock extends Door
{

	protected boolean	_isLocked = true;

	public DoorWithLock()
	{
		
	}

	public DoorWithLock(Room r1, Room r2, boolean isClosed, String name, boolean isLocked)
	{
		super(r1, r2, isClosed, name);
		_isLocked = isLocked;
	}

	@Override
	public void initializeGraphic()
	{
		ColorRGBA wallColor;
		
		if (_isLocked)
			wallColor = ColorRGBA.Magenta;
		else if (_isClosed)
			wallColor = ColorRGBA.Red;
		else
			wallColor = ColorRGBA.Green;
		
		Mesh wallMesh = new Box(1, 1, 1);
		this.initializeGameObject(GameManager.getInstance().getAssetManager(), wallColor, wallMesh, _name);
		this.getGeometry().scale(0.1f, 1, 5);
	}

	@Override
	// Can open if unlocked
	public void open()
	{
		if (!_isLocked)
		{
			_isClosed = false;
			this.getGeometry().getMaterial().setColor("Color", ColorRGBA.Green);
		}
	}

	// Lock the Door
	public void lock()
	{
		_isLocked = true;
		this.getGeometry().getMaterial().setColor("Color", ColorRGBA.Magenta);
	}
	
	// Unlock the Door
	public void unlock()
	{
		_isLocked = false;
		this.getGeometry().getMaterial().setColor("Color", ColorRGBA.Red);
	}
}
