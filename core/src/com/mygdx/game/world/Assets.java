package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.Constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * @author Gabe Werick Handles texture loading using a texture atlas.
 */
public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;

	public AssetBoss boss;
	public AssetPhantom phantom;
	public AssetArcher archer;
	public AssetArchmage archmage;
	public AssetSwordsman swordsman;
	public AssetLevelDecoration levelDecoration;
	public AssetDoor door;
	public AssetMusic music;
	public AssetCoin coin;
	public AssetFonts fonts;
	public AssetSounds sounds;

	// singleton: prevent instantiation from other classes
	private Assets() {
	}

	/**
	 * Load up the texture atlas.
	 * 
	 * @param assetManager The asset manager this class will use.
	 */
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);

		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);

		// load music
		assetManager.load("music/keith303_-_brand_new_highscore.mp3", Music.class);
		
		assetManager.load("sounds/pickup_coin.wav", Sound.class);

		// start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		// create game resource objects
		boss = new AssetBoss(atlas);
		phantom = new AssetPhantom(atlas);
		archer = new AssetArcher(atlas);
		archmage = new AssetArchmage(atlas);
		swordsman = new AssetSwordsman(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
		door = new AssetDoor(atlas);
		music = new AssetMusic(assetManager);
		coin = new AssetCoin(atlas);
		fonts = new AssetFonts();
		sounds = new AssetSounds(assetManager);

	}

	/**
	 * Tell the asset manager to to unload assets.
	 */
	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}

	/**
	 * Called when the asset manager has an error with an asset, prints out an error
	 * log.
	 */
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception) throwable);
	}

	/**
	 * 
	 * @author Gabe Werick
	 * 
	 *         font stuff
	 *
	 */
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts() {
			// Create three fonts using Libgdx' 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		}
	}
	
	/**
	 * @author Gabe Werick
	 * 
	 * holds sound info
	 *
	 */
	public class AssetSounds
	{
		
		public final Sound pickupcoin;
		
		public AssetSounds(AssetManager am)
		{
			pickupcoin = am.get("sounds/pickup_coin.wav", Sound.class);
		}
	}

	/**
	 * Class that holds assets for decoration
	 * 
	 */
	public class AssetLevelDecoration {

		public final AtlasRegion background;
		public final AtlasRegion wall;

		/**
		 * Initialize atlas regions.
		 * 
		 * @param atlas The texture atlas being used.
		 */
		public AssetLevelDecoration(TextureAtlas atlas) {
			background = atlas.findRegion("dungeonBackground");
			wall = atlas.findRegion("wall");
		}
	}

	public class AssetCoin {
		public final AtlasRegion coin;
		public final Animation<TextureRegion> animCoin;

		public AssetCoin(TextureAtlas atlas) {
			coin = atlas.findRegion("item_gold_coin");
			
			//Animate the coin
			Array<AtlasRegion> regions = atlas.findRegions("anim_gold_coin");
			AtlasRegion region = regions.first();
			for(int i = 0; i < 10; i++)
			{
				regions.insert(0, region);
			}
			animCoin = new Animation<TextureRegion>(1.0f/20.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
		}
	}

	/**
	 * @author Gabe Werick This class holds info for the Boss texture
	 */
	public class AssetBoss {
		public final AtlasRegion boss;

		/**
		 * Sets head to hold the reference to the correct region for boss
		 * 
		 * @param atlas Texture atlas
		 */
		public AssetBoss(TextureAtlas atlas) {
			boss = atlas.findRegion("bossStand");
		}
	}

	/**
	 * @author Gabe Werick This class holds info for rock edge and middle texture
	 */
	public class AssetDoor {
		public final AtlasRegion doorNormal;
		public final AtlasRegion bossDoor;

		/**
		 * sets edge and middle to hold references to the appropriate areas
		 * 
		 * @param atlas Texture atlas
		 */
		public AssetDoor(TextureAtlas atlas) {
			doorNormal = atlas.findRegion("normalDoor");
			bossDoor = atlas.findRegion("bossDoor");
		}
	}

	/**
	 * @author Gabe Werick This class holds info for the phantom texture
	 */
	public class AssetPhantom {
		public final AtlasRegion phantom;

		/**
		 * Sets head to hold the reference to the correct region for phantom
		 * 
		 * @param atlas Texture atlas
		 */
		public AssetPhantom(TextureAtlas atlas) {
			phantom = atlas.findRegion("grueStand");
		}
	}

	/**
	 * @author Gabe Werick This class holds info for the Archer texture
	 */
	public class AssetArcher {
		public final AtlasRegion archer;

		/**
		 * Sets head to hold the reference to the correct region for archer
		 * 
		 * @param atlas Texture atlas
		 */
		public AssetArcher(TextureAtlas atlas) {
			archer = atlas.findRegion("archerStand");
		}
	}

	/**
	 * @author Gabe Werick This class holds info for the Archmage texture
	 */
	public class AssetArchmage {
		public final AtlasRegion archmage;

		/**
		 * Sets head to hold the reference to the correct region for archer
		 * 
		 * @param atlas Texture atlas
		 */
		public AssetArchmage(TextureAtlas atlas) {
			archmage = atlas.findRegion("archmageStand");
		}
	}

	/**
	 * @author Gabe Werick This class holds info for the Archmage texture
	 */
	public class AssetSwordsman {
		public final AtlasRegion swordsman;

		/**
		 * Sets head to hold the reference to the correct region for archer
		 * 
		 * @param atlas Texture atlas
		 */
		public AssetSwordsman(TextureAtlas atlas) {
			swordsman = atlas.findRegion("swordsmanStand");
		}
	}

	/**
	 * 
	 * @author Gabe Werick
	 * 
	 *         holds info for music
	 *
	 */
	public class AssetMusic {
		public final Music song01;

		public AssetMusic(AssetManager am) {
			song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
		}
	}

}