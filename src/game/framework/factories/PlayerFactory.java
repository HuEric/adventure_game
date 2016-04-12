package game.framework.factories;

import game.framework.Player;

public class PlayerFactory
{
	protected static PlayerFactory _instance = null;

	protected PlayerFactory()
	{
	}

	public static synchronized PlayerFactory getInstance()
	{
		if (_instance == null)
			_instance = new PlayerFactory();
		return _instance;
	}

	public Player createPlayer()
	{
		Player player = new Player();

		return (player);
	}
}
