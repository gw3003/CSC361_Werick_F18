package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.AbstractGameObject;
import com.mygdx.game.objects.Door;
import com.mygdx.game.objects.DungeonBackground;
import com.mygdx.game.objects.Wall;

public class Level
{
	public Array<DungeonBackground> floor;
	public Array<Door> door;
	public Array<Wall> wall;
	public static final String TAG = Level.class.getName();

	public enum BLOCK_TYPE
	{
		EMPTY(0, 0, 0), // Black
		WALL(255, 255, 0), // Yellow
		FLOOR(0, 0, 255), // Blue
		DOOR(255, 0, 0); // Red

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

		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		// scan pixels from top-left to bottom-right
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++)
		{
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++)
			{
				AbstractGameObject obj = null;
				float offsetWidth = -50f;
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
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

					int size = 1;
					obj = new DungeonBackground(size);
					
					obj.position.set(pixelX+offsetWidth, pixelY);
					floor.add((DungeonBackground) obj);

				}

				// door
				else if (BLOCK_TYPE.DOOR.sameColor(currentPixel))
				{
					int size = 1;
					obj = new Door(size);
					obj.position.set(pixelX+offsetWidth, pixelY);
					door.add((Door) obj);
				}
				
				//Wall
				else if(BLOCK_TYPE.WALL.sameColor(currentPixel))
				{
					int size = 1;
					obj = new Wall(size);
					obj.position.set(pixelX+offsetWidth, pixelY);
					wall.add((Wall)obj);
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

	public void render(SpriteBatch batch)
	{
		// Draw room backgrounds
		for (DungeonBackground dung : floor)
			dung.render(batch);

		// Draw door
		for (Door door : door)
			door.render(batch);
		
		//Draw wall
		for (Wall wall : wall)
			wall.render(batch);
	}
}
