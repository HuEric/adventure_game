package game.framework;

import java.util.ArrayList;

/**
 * Observer Pattern
 */
public abstract class Subject
{
	protected ArrayList<Observer> _obs = new ArrayList<Observer>();
	
	public Subject()
	{
	}

	public void attach(Observer obs)
	{
		if (_obs.contains(obs) == false)
			_obs.add(obs);
	}
	
	public void detach(Observer obs)
	{
		_obs.remove(obs);
	}
	
	public void notifyObserver()
	{
		for (int i = 0; i < _obs.size(); i++)
		{
			_obs.get(i).update(null);
		}
	}
}
