package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * @author Gabe Werick
 * 
 *         Parent class for game objects
 *
 */
public abstract class AbstractGameObject {
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;

	public Vector2 acceleration;
	public Rectangle bounds;

	public Body body;
	
	public float stateTime;
	public Animation<TextureRegion> animation;

	/**
	 * Builds the game object
	 */
	public AbstractGameObject() {
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	/**
	 * Does the animation stuff
	 * @param animation
	 */
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
		stateTime = 0;
	}

	/**
	 * Updates the object based on the timer
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		stateTime += deltaTime;
		position.set(body.getPosition());
		rotation = body.getAngle() * MathUtils.radiansToDegrees;
	}

	/**
	 * Gets the appearance of the game object and puts it on the level
	 * 
	 * @param batch
	 *            is the sprite batch it uses
	 */
	public abstract void render(SpriteBatch batch);
}
