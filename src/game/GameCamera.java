package game;

import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import game.framework.Observer;
import game.framework.Player;
import game.framework.Room;
import game.framework.Subject;

/**
 * GameCamera Object
 * Containing the Camera plus Fly Camera (Game engine components)
 * Basic Camera manipulation methods
 * Proxy Pattern
 */
public class GameCamera extends Observer implements IGameInput, ActionListener
{
	// Reference to Game Camera
	private Camera			_cam			= null;
	// Game Camera properties
	private FlyByCamera		_flyCam			= null;

	public GameCamera(Camera cam, FlyByCamera flyCam)
	{
		_cam = cam;
		_flyCam = flyCam;
		
		// Default settings
		// Set the Camera location to have a top-down view
		Vector3f loc = new Vector3f(0f, 14f, 3f);
		_cam.setLocation(loc);
		// Look at the center
		_cam.lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, -90f));
	}

	/**
	 * Update called from the subject
	 * Observer Pattern
	 */
	@Override
	public void	update(Vector3f pos)
	{
		if (pos != null)
			_cam.setLocation(new Vector3f(pos.x, _cam.getLocation().y, pos.z));
	}

	/**
	 * Set Drag functionality of the Camera
	 * @param cursorState Drag State
	 */
	public void	setDrag(boolean dragState)
	{
		_flyCam.setDragToRotate(dragState);
	}
	
	/**
	 * Change Scene
	 * @param newRoom Next Room
	 */
	public void		changeScene(Subject newRoom)
	{
		// Observe another room if the Camera is observing Room
		if (_subject instanceof Room)
			this.setSubject(newRoom);

		// Reset Camera location
		_cam.setLocation(new Vector3f(0f, 14f, 0f));
	}
	
	/**
	 * Getters
	 */
	public Camera	getCamera()
	{
		return (_cam);
	}
	
	/**
	 * Set Camera Location
	 * @param location Vector3 of the Location
	 */
	public void		setCameraLocation(Vector3f location)
	{
		_cam.setLocation(location);
	}
	
	/**
	 * Camera look At
	 * @param lookAtPos Position to look at
	 * @param worldUp World Up 
	 */
	public void		setCameraLookAt(Vector3f lookAtPos, Vector3f worldUp)
	{
		_cam.lookAt(lookAtPos, worldUp);
	}

	/**
	 * Manage Received Action Inputs
	 */
	@Override
	public void onAction(String name, boolean isPressed, float tpf)
	{
		if (name.equals("View Player") && isPressed)
		{
			System.out.println("Observing Player");
			Player player = GameManager.getInstance().getPlayer();
			this.setSubject(player);
		}
		if (name.equals("View Room") && isPressed)
		{
			System.out.println("Observing Room");
			Room room = GameManager.getInstance().getPlayer().getCurrentRoom();
			this.setSubject(room);
			_cam.setLocation(new Vector3f(0f, 14f, 0f));
		}
	}

	/**
	 * Initialize Inputs
	 */
	@Override
	public void initializeInput()
	{
		System.out.println("GameCamera: Initializing Inputs...");
		InputManager inputManager = GameManager.getInstance().getInputManager();

		// Movement Inputs
		inputManager.addMapping("View Player", new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping("View Room", new KeyTrigger(KeyInput.KEY_R));
		inputManager.addListener(this, "View Player", "View Room");
	}
}
