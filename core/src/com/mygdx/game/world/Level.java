package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.AbstractGameObject;
import com.mygdx.game.objects.Coin;
import com.mygdx.game.objects.Door;
import com.mygdx.game.objects.DungeonBackground;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.SpoopyDude;
import com.mygdx.game.objects.Wall;

public class Level
{
	public Array<DungeonBackground> floor;
	public Array<Door> door;
	public Array<Wall> wall;
	public Array<Enemy> enemy;
	public Array<Coin> coin;
	public SpoopyDude phantom;
	public static final String TAG = Level.class.getName();

	public enum BLOCK_TYPE
	{
		EMPTY(0, 0, 0), // Black
		WALL(255, 255, 0), // Yellow
		FLOOR(0, 0, 255), // Blue
		SPAWNPOINT(255, 255, 255), // White
		DOOR(255, 0, 0), // Red
		ENEMY(255, 0, 255), // pink
		COIN(0, 255, 0); // green

		private int color;

		private BLOCK_TYPE(int r, int g, int b)
		{
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor(int color)
		{
			return this.color == color;
		}

		public int getColor()
		{
			return color;
		}
	}

	public Level(String filename)
	{
		init(filename);
	}

	private void init(String filename)
	{
		floor = new Array<DungeonBackground>();
		door = new Array<Door>();
		wall = new Array<Wall>();
		enemy = new Array<Enemy>();
		coin = new Array<Coin>();
		phantom = null;

		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		// scan pixels from top-left to bottom-right
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++)
		{
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++)
			{
				AbstractGameObject obj = null;
				float offsetWidth = -50f;
				int size = 1;
				// height grows from bottom to top
				float invertY = pixelY * -1;
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				// find matching color value to identify block type (x,y)
				// point and create the corresponding game object if there is a match

				// empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel))
				{
					// do nothing
				}

				// floor
				else if (BLOCK_TYPE.FLOOR.sameColor(currentPixel))
				{
					obj = new DungeonBackground(size);
					obj.position.set(pixelX + offsetWidth, invertY);
					floor.add((DungeonBackground) obj);

				}

				// door
				else if (BLOCK_TYPE.DOOR.sameColor(currentPixel))
				{
					obj = new Door(size);
					obj.position.set(pixelX + offsetWidth, invertY);
					door.add((Door) obj);
				}

				// Wall
				else if (BLOCK_TYPE.WALL.sameColor(currentPixel))
				{
					obj = new Wall(size);
					obj.position.set(pixelX + offsetWidth, invertY);
					wall.add((Wall) obj);
				}

				// Where player starts
				else if (BLOCK_TYPE.SPAWNPOINT.sameColor(currentPixel))
				{
					obj = new SpoopyDude();
					obj.position.set(pixelX + offsetWidth + -22, invertY + 8);
					phantom = (SpoopyDude) obj;

					// add in floor tile where player starts
					obj = new DungeonBackground(size);
					obj.position.set(pixelX + offsetWidth, invertY);
					floor.add((DungeonBackground) obj);
				}

				// Enemy
				else if (BLOCK_TYPE.ENEMY.sameColor(currentPixel))
				{
					obj = new Enemy();
					obj.position.set(pixelX + offsetWidth, invertY);
					enemy.add((Enemy)obj);
					
					// add in floor tile where player starts
					obj = new DungeonBackground(size);
					obj.position.set(pixelX + offsetWidth, invertY);
					floor.add((DungeonBackground) obj);
				}
				
				//Coin
				else if (BLOCK_TYPE.COIN.sameColor(currentPixel))
				{
					obj = new Coin();
					obj.position.set(pixelX + offsetWidth, invertY);
					coin.add((Coin) obj);
					
					// add in floor tile where player starts
					obj = new DungeonBackground(size);
					obj.position.set(pixelX + offsetWidth, invertY);
					floor.add((DungeonBackground) obj);
				}

				// unknown object/pixel color
				else
				{
					int r = 0xff & (currentPixel >>> 24); // red color channel
					int g = 0xff & (currentPixel >>> 16); // green color channel
					int b = 0xff & (currentPixel >>> 8); // blue color channel
					int a = 0xff & currentPixel; // alpha channel
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g
							+ "> b<" + b + "> a<" + a + ">");
				}
			}
		}
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");

	}

	/**
	 * Causes the objects to draw to the screen
	 * 
	 * @param batch
	 */
	public void render(SpriteBatch batch)
	{
		// Draw room backgrounds
		for (DungeonBackground dung : floor)
			dung.render(batch);

		// Draw door
		for (Door door : door)
			door.render(batch);

		// Draw wall
		for (Wall wall : wall)
			wall.render(batch);
		
		// Draw enemies
		for (Enemy enemy: enemy)
			enemy.render(batch);
		
		// Draw Coins
		for (Coin coin: coin)
			coin.render(batch);

		// Draw player Character
		phantom.render(batch);
	}

	/**
	 * Causes objects to update
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		phantom.update(deltaTime);

		// TODO
		/*
		 * for (Door door : door) door.update(deltaTime);
		 */

	}
}
