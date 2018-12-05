package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * @author Gabe Werick
 */
public class DungeonBackground extends AbstractGameObject
{
	
	private TextureRegion dungeonBackground;
	
	private int size;
	
	/**
	 * Constructor
	 * @param size how big the background should be
	 */
	public DungeonBackground(int size)
	{
		this.size = size;
		init();
	}
	
	/**
	 * Initializes size of background
	 */
	private void init()
	{
		dimension.set(size, size);
		
		dungeonBackground = Assets.instance.levelDecoration.background;
		
	}
	
	private void drawBackground(SpriteBatch batch, float offsetX, float offsetY) 
	{
		TextureRegion reg = null;
		
		reg = dungeonBackground;
		batch.draw(reg.getTexture(), position.x, position.y + origin.y, origin.x, origin.y, dimension.x,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawBackground(batch, 0.0f, 0.0f);
	}

}
