package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.util.AudioManager;
import com.mygdx.game.util.GamePreferences;
import com.mygdx.game.world.Assets;


/**
 * @Author Gabe Werick Main class we call upon to do things in the game like
 *         restarting, starting, and pausing
 */
public class DungeonGameMain extends Game {

	/**
	 * Creates the game with a worldRenderer and worldController and unpauses it
	 */
	public void create() {
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// Load assets
		Assets.instance.init(new AssetManager());
		
		// Load perferences for audio settings and start playing music
		GamePreferences.instance.load();
		AudioManager.instance.play(Assets.instance.music.song01);
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}