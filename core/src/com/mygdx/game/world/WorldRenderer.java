package com.mygdx.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author Gabe Werick Contains methods for displaying the world
 */
public class WorldRenderer implements Disposable
{
	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private WorldController worldController;
	
	private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
	private Box2DDebugRenderer b2debugRenderer;

	/**
	 * Constructor for WorldRenderer, initializes worldController then calls init
	 * 
	 * @param worldController
	 */
	public WorldRenderer(WorldController worldController)
	{
		this.worldController = worldController;
		init();
	}
	
	/**
	 * This method calls the world rendering methods
	 * 
	 * @param batch the SpriteBatch to be used
	 */
	private void renderWorld(SpriteBatch batch)
	{
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		if(DEBUG_DRAW_BOX2D_WORLD)
		{
			b2debugRenderer.render(worldController.b2world, camera.combined);
		}
	}

	/**
	 * Initializes the batch and camera variables Camera is set to the
	 * VIEWPORT_WIDTH and HEIGHT found in constants class, then its position is set
	 * to 0,0,0
	 */
	private void init()
	{
		batch = new SpriteBatch();

		// set up camera
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);

		// update camera with changes
		camera.update();
		
		//GUI camera
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();
		
		b2debugRenderer = new Box2DDebugRenderer();
	}

	/**
	 * Calls renderWorld
	 */
	public void render()
	{
		renderWorld(batch);
		renderGUI(batch);
	}

	/**
	 * Renders GUI elements
	 * @param batch
	 */
	public void renderGUI(SpriteBatch batch)
	{
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		
		renderGUIScore(batch);
		
		batch.end();
	}
	
	/**
	 * Renders the score
	 * @param batch
	 */
	public void renderGUIScore(SpriteBatch batch)
	{
		float x = -15;
		float y = -15;
		float offsetX = 50;
		float offsetY = 50;

		if (worldController.scoreVisual < worldController.score)
		{
			long shakeAlpha = System.currentTimeMillis() % 360;
			float shakeDist = 1.5f;
			offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
			offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;
		}

		batch.draw(Assets.instance.coin.coin, x, y, offsetX, offsetY, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int) worldController.scoreVisual, x + 75, y + 37);
	}

	/**
	 * When called resizes the camera to the called height and width
	 * 
	 * @param width  Desired Height
	 * @param height Desired Width
	 */
	public void resize(int width, int height)
	{
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}

	/**
	 * Calls for the batch to be disposed of
	 */
	@Override
	public void dispose()
	{
		batch.dispose();
	}
}
