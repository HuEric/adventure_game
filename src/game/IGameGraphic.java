package game;

import com.jme3.scene.Geometry;

public interface IGameGraphic
{
	public abstract Geometry getGeometry();
	public abstract void initializeGraphic();
}
