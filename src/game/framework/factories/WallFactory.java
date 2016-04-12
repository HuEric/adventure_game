package game.framework.factories;

import game.framework.Wall;

public class WallFactory
{
	protected static WallFactory _instance = null;

	protected WallFactory()
	{
	}

	public static synchronized WallFactory getInstance()
	{
		if (_instance == null)
			_instance = new WallFactory();
		return _instance;
	}

	public Wall createWall()
	{
		Wall newWall = new Wall();

		return (newWall);
	}
}
