package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * @author Gabe Werick
 * 
 * This is the class for enemies
 *
 */
public class Enemy extends AbstractGameObject
{

	private TextureRegion enemy;

	private int size;
	
	/**
	 * Constructor that calls init
	 */
	public Enemy()
	{
		this.size = 1;
		init();
	}
	
	/**
	 * initializes the enemy class
	 */
	private void init()
	{
		dimension.set(size, size);
		origin.set(dimension.x/2, dimension.y/2);
		
		enemy = Assets.instance.archer.archer;
	}
	
	/**
	 * renders the enemy
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		drawEnemy(batch);
	}
	
	/**
	 * Calls the draw method
	 * 
	 * @param batch the spritebatch
	 */
	private void drawEnemy(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		reg = enemy;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}
	
	

}
