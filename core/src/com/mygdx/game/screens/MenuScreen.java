package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * @author Gabe Werick
 *
 * This class builds the menu screen
 */
public class MenuScreen extends AbstractGameScreen
{
	private static final String TAG = MenuScreen.class.getName();

	private Stage stage;
	private Skin skinCanyonBunny;

	private Skin skinLibgdx;

	// menu
	private Image imgBackground;
	private Image imgLogo;
	private Image imgInfo;
	private Image imgCoins;
	private Image imgBunny;
	private Button btnMenuPlay;
	private Button btnMenuOptions;

	// options
	private Window winOptions;
	private TextButton btnWinOptSave;
	private TextButton btnWinOptCancel;
	private CheckBox chkSound;
	private Slider sldSound;
	private CheckBox chkMusic;
	private Slider sldMusic;
	private SelectBox<CharacterSkin> selCharSkin;
	private Image imgCharSkin;
	private CheckBox chkShowFpsCounter;

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;
	
	public MenuScreen(Game game)
	{
		super(game);
	}
	
	@Override
	public void render(float deltaTime)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}
}
