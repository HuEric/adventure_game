/**
 * 
 */
package game;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

/** Basic jMonkeyEngine game template. */
public class AdventureGame extends SimpleApplication
{
	/** Start the jMonkeyEngine application */
	public static void main(String[] args)
	{
		AdventureGame app = new AdventureGame();
		AppSettings settings = new AppSettings(true);

		// Set Window Title
		settings.setTitle("Adventure Game");
		// Apply Settings
		app.setSettings(settings);
		// Start Game
		app.start();
	}

	@Override
	/** initialize the scene here */
	public void simpleInitApp()
	{
		// Initialize GameManager
		GameManager.getInstance().initialize(assetManager, rootNode, inputManager, cam, flyCam);
		// Initialize Game Graphics
		GameManager.getInstance().initializeGraphic();
		// Initialize Game Inputs
		GameManager.getInstance().initializeInput();
		// Start Receiving Inputs
		GameManager.getInstance().unPause();
	}
}