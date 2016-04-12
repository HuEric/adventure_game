/**
 * 
 */
package game;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/** Basic jMonkeyEngine game template. */
public class AdventureGame extends SimpleApplication
{
	/** Start the jMonkeyEngine application */
	public static void main(String[] args)
	{
		AdventureGame app = new AdventureGame();
		AppSettings settings = new AppSettings(true);

		settings.setTitle("Adventure Game");
		app.setSettings(settings);
		app.start();
	}

	@Override
	/** initialize the scene here */
	public void simpleInitApp()
	{
		GameManager.getInstance().initialize(assetManager, rootNode, inputManager, cam, flyCam);
		GameManager.getInstance().initializeGraphic();
		GameManager.getInstance().initializeInput();
		GameManager.getInstance().unPause();
	}
	
	@Override
	/** (optional) Interact with update loop here */
	public void simpleUpdate(float tpf)
	{
		GameManager.getInstance().update();
	}

	@Override
	/** (optional) Advanced renderer/frameBuffer modifications */
	public void simpleRender(RenderManager rm)
	{
		
	}
}