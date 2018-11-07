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
		dimension.set(2,2);
		
		dungeonBackground = Assets.instance.levelDecoration.background;
		
		origin.x = -dimension.x * 2;
		size += dimension.x * 2;
	}
	
	private void drawBackground(SpriteBatch batch, float offsetX, float offsetY) 
	{
		TextureRegion reg = null;
		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;
		
		reg = dungeonBackground;
		batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y, origin.x, origin.y, dimension.x+0.01f,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawBackground(batch, 0.0f, 0.0f);
	}

}
