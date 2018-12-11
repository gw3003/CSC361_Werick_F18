package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.world.WorldController;
import com.mygdx.game.world.WorldRenderer;

public class GameScreen extends AbstractGameScreen
{
	private static final String TAG = GameScreen.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused;

	/**
	 * Constructor for gamescreen, simply calls the parent's constructor
	 * 
	 * @param game
	 */
	public GameScreen(Game game)
	{
		super(game);
	}

	/**
	 * Handles rendering the game screen, doesn't update when the game is paused
	 */
	@Override
	public void render(float deltaTime)
	{
		// Do not update game world when paused
		if (!paused)
		{
			// Update game world by the time that has passed since last update
			worldController.update(deltaTime);
		}

		// Sets the clear screen color to Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);

		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render game world to screen
		worldRenderer.render();
	}

	/**
	 * Sets game window to desired size
	 */
	@Override
	public void resize(int width, int height)
	{
		worldRenderer.resize(width, height);
	}

	/**
	 * Shows the game screen when called
	 */
	@Override
	public void show()
	{
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}

	/**
	 * hides the gamescreen
	 */
	@Override
	public void hide()
	{
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	/**
	 * pauses the gamescreen
	 */
	@Override
	public void pause()
	{
		super.resume();
	}

}
