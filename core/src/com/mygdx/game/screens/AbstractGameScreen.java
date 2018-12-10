package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.world.Assets;

/**
 * Abstract class for the various game screens
 * 
 * @author Gabe Werick
 */
public abstract class AbstractGameScreen implements Screen
{

	protected Game game;

	public AbstractGameScreen(Game game)
	{
		this.game = game;
	}

	/**
	 * Calls for the game to be drawn
	 */
	public abstract void render(float deltaTime);

	/**
	 * Allows us to resize the screen
	 */
	public abstract void resize(int width, int height);

	/**
	 * Shows the screen for when it should be used
	 */
	public abstract void show();

	/**
	 * Hides the screen when not being used
	 */
	public abstract void hide();

	/**
	 * Pauses the game
	 */
	public abstract void pause();

	/**
	 * Resumes the game from being paused
	 */
	public void resume()
	{
		Assets.instance.init(new AssetManager());
	}

	/**
	 * Gets rid of unused elements
	 */
	public void dispose()
	{
		Assets.instance.dispose();
	}

}
