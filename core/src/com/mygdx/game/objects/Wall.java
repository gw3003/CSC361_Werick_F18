package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * 
 * @author Gabe Werick
 *
 */
public class Wall extends AbstractGameObject{

	private TextureRegion wall;

	private int size;

	/**
	 * Constructor
	 * @param size how big the wall should be
	 */
	public Wall(int size)
	{
		this.size = size;
		init();
	}
	
	/**
	 * Initializes size of wall
	 */
	private void init()
	{
		dimension.set(size, size);
		origin.set(dimension.x/2, dimension.y/2);
		
		wall = Assets.instance.levelDecoration.wall;
		
	}
	
	/**
	 * Draws the wall texture
	 * @param batch spritebatch to be used
	 */
	private void drawWall(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		reg = wall;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	/**
	 * Renders spritebatch
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		drawWall(batch);
	}
}
