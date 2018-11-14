package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * @author Gabe Werick
 */
public class Door extends AbstractGameObject
{
	
	private TextureRegion dungeonBackground;
	
	private int size;
	
	/**
	 * Constructor
	 * @param size how big the door should be
	 */
	public Door(int size)
	{
		this.size = size;
		init();
	}
	
	/**
	 * Initializes size of door
	 */
	private void init()
	{
		dimension.set(size, size);
		
		dungeonBackground = Assets.instance.levelDecoration.background;
		
		origin.x = -dimension.x * 2;
		size += dimension.x * 2;
	}
	
	private void drawDoor(SpriteBatch batch, float offsetX, float offsetY) 
	{
		TextureRegion reg = null;
		float xRel = dimension.x * offsetX;
		
		reg = dungeonBackground;
		batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y, origin.x, origin.y, dimension.x+0.01f,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawDoor(batch, 0.0f, 0.0f);
	}

}
