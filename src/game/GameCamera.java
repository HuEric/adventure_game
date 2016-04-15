package game;

import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import game.framework.Observer;
import game.framework.Room;
import game.framework.Subject;

public class GameCamera extends Observer
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

	public void	update(Vector3f pos)
	{
		if (pos != null)
			_cam.setLocation(new Vector3f(pos.x, _cam.getLocation().y, pos.z));
	}

	public void	setDrag(boolean cursorState)
	{
		_flyCam.setDragToRotate(cursorState);
	}
	
	public void		changeScene(Subject newRoom)
	{
		// Observe another room if the Camera is observing Room
		if (_subject instanceof Room)
			this.setSubject(newRoom);

		// Reset Camera location
		_cam.setLocation(new Vector3f(0f, 14f, 3f));
	}
	
	public Camera	getCamera()
	{
		return (_cam);
	}
	
	public void		setCameraLocation(Vector3f location)
	{
		_cam.setLocation(location);
	}
	
	public void		setCameraLookAt(Vector3f lookAtPos, Vector3f worldUp)
	{
		_cam.lookAt(lookAtPos, worldUp);
	}
}
