package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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

	// singleton: prevent instantiation from other classes
	private Assets() {
	}

	/**
	 * Load up the texture atlas.
	 * 
	 * @param assetManager
	 *            The asset manager this class will use.
	 */
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
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
		//rock = new AssetRock(atlas);
		phantom = new AssetPhantom(atlas);
		archer = new AssetArcher(atlas);
		archmage = new AssetArchmage(atlas);
		swordsman = new AssetSwordsman(atlas);
		//levelDecoration = new AssetLevelDecoration(atlas);
	}

	/**
	 * Tell the asset manager to to unload assets.
	 */
	@Override
	public void dispose() {
		assetManager.dispose();
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
	 * Class that holds assets for decoration
	 * 
	 */
	public class AssetLevelDecoration {
		
		public final AtlasRegion background;

		/**
		 * Initialize atlas regions.
		 * 
		 * @param atlas
		 *            The texture atlas being used.
		 */
		public AssetLevelDecoration(TextureAtlas atlas) {
			background = atlas.findRegion("dungeonBackground");
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
		 * @param atlas
		 *            Texture atlas
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
		 * @param atlas
		 *            Texture atlas
		 */
		public AssetDoor(TextureAtlas atlas) {
			doorNormal = atlas.findRegion("doorNormal");
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
		 * @param atlas
		 *            Texture atlas
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
		 * @param atlas
		 *            Texture atlas
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
		 * @param atlas
		 *            Texture atlas
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
		 * @param atlas
		 *            Texture atlas
		 */
		public AssetSwordsman(TextureAtlas atlas) {
			swordsman = atlas.findRegion("swordsmanStand");
		}
	}

}