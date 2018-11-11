package com.mygdx.game.world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.AbstractGameObject;
import com.mygdx.game.objects.DungeonBackground;
import com.mygdx.game.util.Constants;


public class Level
{
	public Array<DungeonBackground> rooms;
	
	private int roomNum;
	
	public Level(String filename)
	{
		init(filename);
	}
	
	private void init(String filename)
	{
		rooms = new Array<DungeonBackground>();
		//open dng file
		try
		{
			FileReader fr = new FileReader(Constants.LEVEL);
			Scanner dng = new Scanner(fr);
			
			String line = "";
			//check if file version matches current version
			line = dng.nextLine();
			if(!line.equals(Constants.DNG_VERSION))
			{
				System.out.println("Error! wrong dng file version");
				System.exit(0);
			}
			
			//find how many rooms there are
			line = dng.nextLine();
			roomNum = Integer.parseInt(line);
			
			int roomHeight = 0;
			for(int i = 0; i < roomNum ; i++)
			{
				AbstractGameObject obj = new DungeonBackground(2);
				obj.position.set(0,roomHeight);
				rooms.add((DungeonBackground) obj);
				roomHeight += -2;
			}
			dng.close();
			
		} catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		
		
		
		
	}
	
	public void render(SpriteBatch batch)
	{
		//Draw room backgrounds
		for (DungeonBackground dung : rooms)
			dung.render(batch);
	}
}
