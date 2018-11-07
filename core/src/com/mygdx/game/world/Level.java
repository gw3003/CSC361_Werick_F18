package com.mygdx.game.world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.DungeonBackground;
import com.mygdx.game.util.Constants;

public class Level
{
	public Array<DungeonBackground> rooms;
	
	public Level(String filename)
	{
		init(filename);
	}
	
	private void init(String filename)
	{
		//open dng file
		try
		{
			FileReader fr = new FileReader(Constants.LEVEL);
			Scanner dng = new Scanner(fr);
		} catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		
		
		
		//read how many rooms there are
	}
	
	public void render(SpriteBatch batch)
	{
		
	}
}
