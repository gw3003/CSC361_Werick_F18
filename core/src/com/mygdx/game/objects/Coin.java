package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.world.Assets;

/**
 * @author Gabe Werick
 * 
 *         contains code for coin class
 *
 */
public class Coin extends AbstractGameObject {
	private TextureRegion coin;

	public boolean collected = false;

	private int size;

	/**
	 * Constructor that calls init
	 */
	public Coin() {
		this.size = 1;
		init();
	}

	/**
	 * initializes the coin class
	 */
	private void init() {
		dimension.set(size, size);
		origin.set(dimension.x / 2, dimension.y / 2);

		coin = Assets.instance.coin.coin;
	}

	/**
	 * renders the coin
	 */
	@Override
	public void render(SpriteBatch batch) {
		if (!collected) {
			drawCoin(batch);
		}
	}

	/**
	 * Calls the draw method
	 * 
	 * @param batch the spritebatch
	 */
	private void drawCoin(SpriteBatch batch) {
		TextureRegion reg = null;

		reg = coin;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}
	
	/**
	 * @return amount score gets augmented by a coin
	 */
	public int getScore()
	{
		return 150;
	}

}
