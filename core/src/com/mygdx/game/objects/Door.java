package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * @author Gabe Werick
 */
public class Door extends AbstractGameObject
{
	
	private TextureRegion door;
	
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
		
		door = Assets.instance.door.doorNormal;
		
	}
	
	private void drawDoor(SpriteBatch batch, float offsetX, float offsetY) 
	{
		TextureRegion reg = null;

		
		reg = door;
		batch.draw(reg.getTexture(), position.x, position.y + origin.y, origin.x, origin.y, dimension.x+0.01f,
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawDoor(batch, 0.0f, 0.0f);
	}

}
