package com.mygdx.game.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.util.AudioManager;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;

/**
 * @author Gabe Werick
 *
 *         This class builds the menu screen
 */
public class MenuScreen extends AbstractGameScreen
{
	private static final String TAG = MenuScreen.class.getName();

	private Stage stage;
	private Skin skinDungeonGame;

	private Skin skinLibgdx;

	// menu
	private Image imgBackground;
	private Image imgGrue;
	private Image imgLogo;
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
	private CheckBox chkShowFpsCounter;

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;

	public MenuScreen(Game game)
	{
		super(game);
	}

	/**
	 * render the menu screen
	 */
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (debugEnabled)
		{
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <= 0)
			{
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}

		stage.act(deltaTime);
		stage.draw();
		stage.setDebugAll(false);
	}

	/**
	 * Resizes the window
	 * 
	 */
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}

	/**
	 * shows the menuscreen
	 */
	@Override
	public void show()
	{
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	/**
	 * hides the menu screen
	 */
	@Override
	public void hide()
	{
		stage.dispose();
		skinDungeonGame.dispose();
		skinLibgdx.dispose();
	}

	/**
	 * menu screen has no need to be paused
	 */
	@Override
	public void pause()
	{
	}

	/**
	 * rebuilds the menu screen
	 */
	private void rebuildStage()
	{
		skinDungeonGame = new Skin(Gdx.files.internal(Constants.SKIN_DUNGEONGAME_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

		skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

		// build all layers
		Table layerBackground = buildBackgroundLayer();
		Table layerObjects = buildObjectsLayer();
		Table layerLogo = buildLogoLayer();
		Table layerControls = buildControlsLayer();
		Table layerOptionsWindow = buildOptionsWindowLayer();

		// assemble stage for menu screen
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerObjects);
		stack.add(layerControls);
		stack.add(layerLogo);
		stage.addActor(layerOptionsWindow);
	}

	/**
	 * Builds the logo layer
	 * 
	 * @return the completed layer
	 */
	private Table buildLogoLayer()
	{
		Table layer = new Table();
		layer.left().top();

		// + Game Logo
		imgLogo = new Image(skinDungeonGame, "logo");
		layer.add(imgLogo);
		layer.row().expandY();
		
		if (debugEnabled)
			layer.debug();
		return layer;
	}

	/**
	 * builds the background layer
	 * 
	 * @return completed layer
	 */
	private Table buildBackgroundLayer()
	{
		Table layer = new Table();
		// + Background
		imgBackground = new Image(skinDungeonGame, "background");
		layer.add(imgBackground);
		return layer;
	}

	/**
	 * builds the objects layer
	 * 
	 * @return completed layer
	 */
	private Table buildObjectsLayer()
	{
		Table layer = new Table();
		// Grue
		imgGrue = new Image(skinDungeonGame, "grue");
		layer.addActor(imgGrue);
		imgGrue.addAction(sequence(moveTo(655, 510), delay(4.0f), moveBy(-70, -100, 0.5f, Interpolation.fade),
				moveBy(-100, -50, 0.5f, Interpolation.fade), moveBy(-150, -300, 1.0f, Interpolation.elasticIn)));
		// imgBunny.setPosition(355, 40);
		return layer;
	}

	/**
	 * build the layer with the controls
	 * 
	 * @return completed layer
	 */
	private Table buildControlsLayer()
	{
		Table layer = new Table();

		layer.right().bottom();
		// + Play Button
		btnMenuPlay = new Button(skinDungeonGame, "play");
		layer.add(btnMenuPlay);
		btnMenuPlay.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				onPlayClicked();
			}
		});

		layer.row();
		// + Options Button
		btnMenuOptions = new Button(skinDungeonGame, "options");
		layer.add(btnMenuOptions);
		btnMenuOptions.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				onOptionsClicked();
			}
		});
		if (debugEnabled)
			layer.debug();

		return layer;
	}

	/**
	 * Starts the game
	 */
	private void onPlayClicked()
	{
		game.setScreen(new GameScreen(game));
	}

	/**
	 * Builds the options window layer
	 * 
	 * @return builds the layer
	 */
	private Table buildOptionsWindowLayer()
	{
		winOptions = new Window("Options", skinLibgdx);
		// + Audio Settings: Sound/Music CheckBox and Volumne Slider
		winOptions.add(buildOptWinAudioSettings()).row();
		// + Debug: Show FPS Counter
		winOptions.add(buildOptWinDebug()).row();
		// + Separator and Buttons (Save, Cancel)
		winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);

		// Make options window slightly transparent
		winOptions.setColor(1, 1, 1, 0.8f);
		// Hide options window by default
		showOptionsWindow(false, false);
		// winOptions.setVisible(false);
		if (debugEnabled)
			winOptions.debug();
		// Let TableLayout recalculate widget sizes and positions
		winOptions.pack();
		// Move options window to bottom right corner
		winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth() - 50, 50);
		return winOptions;
	}

	/**
	 * Builds audio settings window
	 * 
	 * @return audio layer
	 */
	private Table buildOptWinAudioSettings()
	{
		Table tbl = new Table();
		// + Title: "Audio"
		tbl.pad(10, 10, 0, 10);
		tbl.add(new Label("Audio", skinLibgdx, "default-font", Color.ORANGE)).colspan(3);
		tbl.row();
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Sound" label, sound volume slider
		chkSound = new CheckBox("", skinLibgdx);
		tbl.add(chkSound);
		tbl.add(new Label("Sound", skinLibgdx));
		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		tbl.add(sldSound);
		tbl.row();
		// + Checkbox, "Music" label, music volume slider
		chkMusic = new CheckBox("", skinLibgdx);
		tbl.add(chkMusic);
		tbl.add(new Label("Music", skinLibgdx));
		sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		tbl.add(sldMusic);
		tbl.row();
		return tbl;
	}

	/**
	 * Builds a table that contains debug settings.
	 * 
	 * @return the completed layer
	 */
	private Table buildOptWinDebug()
	{
		Table tbl = new Table();
		// + Title: "Debug"
		tbl.pad(10, 10, 0, 10);
		tbl.add(new Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3);
		tbl.row();
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Show FPS Counter" label
		chkShowFpsCounter = new CheckBox("", skinLibgdx);
		tbl.add(new Label("Show FPS Counter", skinLibgdx));
		tbl.add(chkShowFpsCounter);
		tbl.row();
		return tbl;
	}

	/**
	 * builds option window buttons
	 * 
	 * @return the completed layer
	 */
	private Table buildOptWinButtons()
	{
		Table tbl = new Table();
		// + Separator
		Label lbl = null;
		lbl = new Label("", skinLibgdx);
		lbl.setColor(0.75f, 0.75f, 0.75f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skinLibgdx.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 0, 0, 1);
		tbl.row();
		lbl = new Label("", skinLibgdx);
		lbl.setColor(0.5f, 0.5f, 0.5f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skinLibgdx.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
		tbl.row();

		// + Save Button with event handler
		btnWinOptSave = new TextButton("Save", skinLibgdx);
		tbl.add(btnWinOptSave).padRight(30);
		btnWinOptSave.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				onSaveClicked();
			}
		});

		// + Cancel Button with event handler
		btnWinOptCancel = new TextButton("Cancel", skinLibgdx);
		tbl.add(btnWinOptCancel);
		btnWinOptCancel.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				onCancelClicked();
			}
		});

		return tbl;
	}

	/**
	 * Loads the previously set settings to the options menu.
	 */
	private void loadSettings()
	{
		GamePreferences prefs = GamePreferences.instance;
		prefs.load();
		chkSound.setChecked(prefs.sound);
		sldSound.setValue(prefs.volSound);
		chkMusic.setChecked(prefs.music);
		sldMusic.setValue(prefs.volMusic);
	}

	/**
	 * Loads the options window, hides other buttons.
	 */
	private void onOptionsClicked()
	{
		loadSettings();
		showMenuButtons(false);
		showOptionsWindow(true, true);
		btnMenuPlay.setVisible(false);
		btnMenuOptions.setVisible(false);
		winOptions.setVisible(true);
	}

	/**
	 * Saves changes made in settings, and returns to the main menu.
	 */
	private void onSaveClicked()
	{
		saveSettings();
		onCancelClicked();
		AudioManager.instance.onSettingsUpdated();
	}

	/**
	 * Closes the options window, returns to the main menu.
	 */
	private void onCancelClicked()
	{
		showMenuButtons(true);
		showOptionsWindow(false, true);
		btnMenuPlay.setVisible(true);
		btnMenuOptions.setVisible(true);
		winOptions.setVisible(false);
		AudioManager.instance.onSettingsUpdated();
	}

	/**
	 * Save changes made to settings in the options menu.
	 */
	private void saveSettings()
	{
		GamePreferences prefs = GamePreferences.instance;
		prefs.sound = chkSound.isChecked();
		prefs.volSound = sldSound.getValue();
		prefs.music = chkMusic.isChecked();
		prefs.volMusic = sldMusic.getValue();
		prefs.showFpsCounter = chkShowFpsCounter.isChecked();
		prefs.save();
	}

	/**
	 * Animates the buttons on the menu
	 */
	private void showMenuButtons(boolean visible)
	{
		float moveDuration = 1.0f;
		Interpolation moveEasing = Interpolation.swing;
		float delayOptionsButton = 0.25f;

		float moveX = 300 * (visible ? -1 : 1);
		float moveY = 0 * (visible ? -1 : 1);
		final Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
		btnMenuPlay.addAction(moveBy(moveX, moveY, moveDuration, moveEasing));
		btnMenuOptions.addAction(sequence(delay(delayOptionsButton), moveBy(moveX, moveY, moveDuration, moveEasing)));
		SequenceAction seq = sequence();
		if (visible)
			seq.addAction(delay(delayOptionsButton + moveDuration));
		seq.addAction(run(new Runnable()
		{
			public void run()
			{
				btnMenuPlay.setTouchable(touchEnabled);
				btnMenuPlay.setTouchable(touchEnabled);
				btnMenuOptions.setTouchable(touchEnabled);
			}
		}));
		stage.addAction(seq);
	}

	/**
	 * Animates buttons in Options windows
	 */
	private void showOptionsWindow(boolean visible, boolean animated)
	{
		float alphaTo = visible ? 0.8f : 0.0f;
		float duration = animated ? 1.0f : 0.0f;
		Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
		winOptions.addAction(sequence(touchable(touchEnabled), alpha(alphaTo, duration)));
	}
}
