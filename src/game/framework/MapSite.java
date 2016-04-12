package game.framework;

import game.GameScene;

public abstract class MapSite extends GameScene
{
	/**
	 *  Player p enters the location
	 */
	abstract void enter(Player p);
	/**
	 *  Player p exits the location
	 */
	abstract void exit(Player p);
}
