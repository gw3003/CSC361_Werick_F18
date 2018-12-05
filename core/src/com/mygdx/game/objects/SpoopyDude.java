package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * @author Gabe Werick
 *
 * This is the class for the bad guy (the player!)
 */
public class SpoopyDude extends AbstractGameObject{

	public static final String TAG = SpoopyDude.class.getName();
	private TextureRegion boss;
	
	public enum VIEW_DIRECTION {LEFT, RIGHT};
	public VIEW_DIRECTION viewDirection;
	
	/**
	 * Constructor that just inits!
	 */
	public SpoopyDude ()
	{
		init();
	}
	
	/**
	 * Initialize the boss
	 */
	public void init()
	{
		dimension.set(1,1);
		boss = Assets.instance.phantom.phantom;
		origin.set(dimension.x/2, dimension.y / 2);
		viewDirection = VIEW_DIRECTION.RIGHT;
	}
	
	/**
	 * Updates the boss
	 */
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		position.x += body.getLinearVelocity().x * deltaTime;
		position.y += body.getLinearVelocity().y * deltaTime;
	}
	
	/**
	 * Renders the boss in the world
	 */
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		//Draw the image
		reg = boss;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);
	}
	
	/**
	 * Moves the player up when called
	 */
	public void moveNorth()
	{
		body.applyLinearImpulse(0, 1.0f, position.x, position.y, true);
	}
	
	/**
	 * Moves player down when called
	 */
	public void moveSouth()
	{
		body.applyLinearImpulse(0, -1.0f, position.x, position.y, true);
	}
	
	/**
	 * Moves player left when called
	 */
	public void moveWest()
	{
		body.applyLinearImpulse(-1.0f, 0, position.x, position.y, true);
	}
	
	/**
	 * Moves player right when called
	 */
	public void moveEast()
	{
		body.applyLinearImpulse(1.0f, 0, position.x, position.y, true);
	}

}
