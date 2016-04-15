package game.framework;

import com.jme3.math.Vector3f;

/**
 * Observer Pattern
 */
public abstract class Observer
{
	protected Subject _subject = null;
	
	public void setSubject(Subject subject)
	{
		// Detach from the previous one if exist
		if (_subject != null)
			_subject.detach(this);
		
		// Attach to the new Subject
		_subject = subject;
		_subject.attach(this);
	}
	
	public abstract void update(Vector3f pos);
}
