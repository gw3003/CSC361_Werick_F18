package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Pixmap;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.objects.Coin;
import com.mygdx.game.objects.Door;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.Wall;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.util.AudioManager;
import com.mygdx.game.util.CameraHelper;
import com.mygdx.game.util.Constants;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 * @author Gabe Werick Contains controls for the game, such as for the camera,
 *         movement, etc.
 */
public class WorldController extends InputAdapter {
	// Tag used for logging purposes
	private static final String TAG = WorldController.class.getName();

	public CameraHelper cameraHelper;
	public Level level;

	public int score;
	public float scoreVisual;

	private boolean goalReached = false;

	// Rectangles for easy collision detection of shame
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	public World b2world;
	private Game game;

	/**
	 * Constructor for WorldController.
	 */
	public WorldController(Game game) {
		this.game = game;
		init();
	}

	/**
	 * Initialization code for WorldController. Useful to call when resetting
	 * objects.
	 */
	public void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		initLevel();
	}

	/**
	 * Initializes the level
	 */
	public void initLevel() {
		level = new Level(Constants.LEVEL);
		cameraHelper.setTarget(level.phantom);
		score = 0;
		scoreVisual = score;
		initPhysics();
	}

	/**
	 * Initializes physics for objects that need physics
	 */
	private void initPhysics() {
		// Create physics world
		if (b2world != null)
			b2world.dispose();
		b2world = new World(new Vector2(0, 0), true);

		// b2world.setContactListener(level.coin);

		initPlayerPhysics();
		initWallPhysics();
		initEnemyPhysics();
		initCoinPhysics();

	}

	/**
	 * I'm out of time so time for collision detection of shame
	 */
	private void testCollisions() {
		r1.set(level.phantom.position.x, level.phantom.position.y, level.phantom.bounds.width,
				level.phantom.bounds.height);

		// Test collision: Phantom <-> Coin
		for (Coin coin : level.coin) {
			if (coin.collected)
				continue;
			r2.set(coin.position.x, coin.position.y, coin.bounds.width, coin.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionPhantomWithCoin(coin);
		}
		
		//Test collision: Phantom <-> Door
		if(!goalReached)
		{
			for(Door door : level.door)
			{
				r2.set(door.position.x, door.position.y, door.bounds.width, door.bounds.height);
				if(!r1.overlaps(r2))
					continue;
				onCollisionWithGoal();
			}
		}
	}
	/**
	 * Ends the game
	 */
	private void onCollisionWithGoal()
	{
		goalReached = true;
		System.out.println("The final score is " + score);
		backToMenu();
	}
	
	/**
	 * sends us back to menu screen
	 */
	private void backToMenu()
	{
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}

	/**
	 * Makes stuff happen when coin gets collected
	 * 
	 * @param coin
	 */
	private void onCollisionPhantomWithCoin(Coin coin) {
		coin.collected = true;
		AudioManager.instance.play(Assets.instance.sounds.pickupcoin);
		score += coin.getScore();
		Gdx.app.log(TAG, "Gold coin Collected!");
	}

	/**
	 * Does the coin physics
	 */
	private void initCoinPhysics() {
		Vector2 origin = new Vector2();
		// Create physics bodies for wall

		for (Coin coin : level.coin) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(coin.position);

			Body body = b2world.createBody(bodyDef);
			coin.body = body;

			PolygonShape polygonShape = new PolygonShape();
			origin.x = coin.origin.x;
			origin.y = coin.origin.y;
			polygonShape.setAsBox(coin.origin.x, coin.origin.y, origin, 0);

			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
	}

	/**
	 * initializes physics for enemy
	 */
	private void initEnemyPhysics() {
		Vector2 origin = new Vector2();
		// Create physics bodies for wall

		for (Enemy enemy : level.enemy) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(enemy.position);

			Body body = b2world.createBody(bodyDef);
			enemy.body = body;

			PolygonShape polygonShape = new PolygonShape();
			origin.x = enemy.origin.x;
			origin.y = enemy.origin.y;
			polygonShape.setAsBox(enemy.origin.x, enemy.origin.y, origin, 0);

			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
	}

	/**
	 * initializes physics for player
	 */
	private void initPlayerPhysics() {
		Vector2 origin = new Vector2();
		// Create physics bodies for Player

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(level.phantom.position);
		bodyDef.position.add(level.phantom.position.x, level.phantom.position.y);
		bodyDef.angle = level.phantom.rotation * MathUtils.degreesToRadians;
		bodyDef.fixedRotation = true;

		int scale = 1;
		level.phantom.scale.set(scale, scale);

		Body body = b2world.createBody(bodyDef);
		level.phantom.body = body;

		PolygonShape polygonShape = new PolygonShape();
		float halfWidth = level.phantom.bounds.width / 2 * scale;
		float halfHeight = level.phantom.bounds.height / 2 * scale;
		origin.y = level.phantom.dimension.y / 2;
		polygonShape.setAsBox(halfWidth, halfHeight);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 75;
		fixtureDef.restitution = 0.5f;
		fixtureDef.friction = 0.5f;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
	}

	/**
	 * Creates physics for walls
	 */
	private void initWallPhysics() {
		Vector2 origin = new Vector2();
		// Create physics bodies for wall

		for (Wall wall : level.wall) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(wall.position);

			Body body = b2world.createBody(bodyDef);
			wall.body = body;

			PolygonShape polygonShape = new PolygonShape();
			origin.x = wall.origin.x;
			origin.y = wall.origin.y;
			polygonShape.setAsBox(wall.origin.x, wall.origin.y, origin, 0);

			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
	}

	/**
	 * Resets game if R is pressed, changes selected sprite if space is pressed.
	 * 
	 * @param keycode The key that was released.
	 * @return false
	 */
	@Override
	public boolean keyUp(int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}

		return false;
	}

	/**
	 * Generates a test pixmap that has a partially transparent red fill, yellow X,
	 * and cyan border.
	 * 
	 * @param width  width of the pixmap to generate.
	 * @param height height of the pixmap to generate.
	 * @return The generated pixmap.
	 */
	private Pixmap createProceduralPixmap(int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		// Draw a yellow-colored X shape on square
		pixmap.setColor(1, 1, 0, 1);
		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, width, height);
		return pixmap;
	}

	/**
	 * Applies updates to the game world many times a second.
	 * 
	 * @param deltaTime How much time has passed since last frame.
	 */
	public void update(float deltaTime) {
		if (goalReached) {

		} else {
			handlePlayerInput(deltaTime);
			handleDebugInput(deltaTime);
		}
		testCollisions();

		if (scoreVisual < score)
			scoreVisual = Math.min(score, scoreVisual + 250 * deltaTime);

		level.update(deltaTime);
		b2world.step(deltaTime, 8, 3);
		cameraHelper.update(deltaTime);
	}

	/**
	 * Handles checking for inputs for player actions
	 * 
	 * @param deltaTime
	 */
	public void handlePlayerInput(float deltaTime) {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			level.phantom.moveNorth();
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			level.phantom.moveWest();
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			level.phantom.moveSouth();
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			level.phantom.moveEast();
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			level.phantom.stopMomentum();
		}

	}

	/**
	 * Tests out movement of sprites and camera.
	 * 
	 * @param deltaTime How much time since last frame.
	 */
	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP))
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			cameraHelper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addzoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addzoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	/**
	 * Moves the Camera in a given direction
	 * 
	 * @param x Horizontal distance.
	 * @param y Vertical distance.
	 */
	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

}