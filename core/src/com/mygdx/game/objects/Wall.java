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
		
		wall = Assets.instance.levelDecoration.wall;
		
	}
	
	/**
	 * Draws the wall texture
	 * @param batch spritebatch to be used
	 * @param offsetX x offset
	 * @param offsetY y offset
	 */
	private void drawWall(SpriteBatch batch, float offsetX, float offsetY) 
	{
		TextureRegion reg = null;
		
		reg = wall;
		batch.draw(reg.getTexture(), position.x, position.y + origin.y, origin.x, origin.y, dimension.x,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	/**
	 * Renders spritebatch
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		drawWall(batch, 0.0f, 0.0f);
	}
}
