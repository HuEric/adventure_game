package game;

public abstract class MapSite
{
	/** Player p enters the location */
	abstract void enter(Player p);
	/** Player p exits the location */
	abstract void exit(Player p);
}
