package game.framework;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import game.GameManager;

public class DoorWithLock extends Door
{
	/**
	 * DoorWithLock Attributes
	 */
	protected boolean	_isLocked = true;

	public DoorWithLock()
	{		
	}

	/**
	 * DoorWithLock Constructor
	 * @param r1
	 * @param r2
	 * @param isClosed
	 * @param name
	 * @param isLocked
	 */
	public DoorWithLock(Room r1, Room r2, boolean isClosed, String name, boolean isLocked)
	{
		super(r1, r2, isClosed, name);
		_isLocked = isLocked;
	}

	/**
	 * Initialize DoorWithLock Graphics
	 */
	@Override
	public void initializeGraphic()
	{
		ColorRGBA doorColor;

		if (_isLocked)						// If locked, Magenta color
			doorColor = ColorRGBA.Magenta;
		else if (_isClosed)					// If closed, Red Color
			doorColor = ColorRGBA.Red;
		else								// If opened, Green Color
			doorColor = ColorRGBA.Green;
		
		// Create DoorWithLock Graphics
		Mesh doorMesh = new Box(1, 1, 1);
		this.initializeGameObject(GameManager.getInstance().getAssetManager(), doorColor, doorMesh, _name);
		this.getGeometry().scale(0.1f, 1, 5);
	}

	/**
	 * Open a Door if unlocked
	 */
	@Override
	public void open()
	{
		if (!_isLocked)
			_isClosed = false;
	}

	/**
	 * Lock the Door
	 */
	public void lock()
	{
		_isLocked = true;
	}
	
	/**
	 * Unlock the Door
	 */
	public void unlock()
	{
		_isLocked = false;
	}
}
