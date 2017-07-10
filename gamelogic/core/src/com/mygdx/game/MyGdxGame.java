package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;


public class MyGdxGame extends Game {
		private static ScreenManager screenManager;

	public static AndroidInterfaces aoi;

	public MyGdxGame(AndroidInterfaces maoi)
	{
		aoi=maoi;
	}

	@Override
	public void resume() {
		super.resume();
		AssetsManager.getInstance().loadSplashScreen();
	}

	@Override
	public void pause() {
		super.pause();
		AssetsManager.getInstance().loadSplashScreen();
	}

	@Override
		public void create () {
			AssetsManager.getInstance().loadSplashScreen();
			screenManager = new ScreenManager();

		}

		@Override
		public void render () { //UPDATE METHOD CALLED PER FRAME
			if(screenManager.getNowScreen() != null){
				screenManager.getNowScreen().render(Gdx.graphics.getDeltaTime());
			}
		}


		public static ScreenManager getScreenManager(){
			return screenManager;
		}
		@Override
		public void dispose () {
			screenManager.getNowScreen().dispose();
		}
	}


	/**private Skin skin;
	private Stage stage;
	private Table container;

	public void create () {
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("uiskin.json"));
    	skin.add("top", skin.newDrawable("default-round", Color.RED), Drawable.class);
		skin.add("star-filled", skin.newDrawable("white", Color.YELLOW), Drawable.class);
		skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY), Drawable.class);
		Gdx.input.setInputProcessor(stage);

		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		PagedScrollPane scroll = new PagedScrollPane();
		scroll.setFlingTime(0.1f);
		scroll.setPageSpacing(25);
		int c = 1;
		for (int l = 0; l < 10; l++) {
			Table levels = new Table().pad(50);
			levels.defaults().pad(20, 40, 20, 40);
			for (int y = 0; y < 3; y++) {
				levels.row();
				for (int x = 0; x < 4; x++) {
					levels.add(getLevelButton(c++)).expand().fill();
				}
			}
			scroll.addPage(levels);
		}
		container.add(scroll).expand().fill();

	}

	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		//stage.setDebugAll(true);
	}

	public void resize (int width,int height) {
		stage.getViewport().update(width,height);
	}

	public void dispose () {
		stage.dispose();
		skin.dispose();
	}

	public boolean needsGL20 () {
		return false;
	}


	/**
	 * Creates a button to represent the level
	 *
	 * @param level
	 * @return The button to use for the level

	public Button getLevelButton(int level) {
		Button button = new Button(skin);
		ButtonStyle style = button.getStyle();
		style.up = 	style.down = null;

		// Create the label to show the level number
		Label label = new Label(Integer.toString(level), skin);
		label.setFontScale(2f);
		label.setAlignment(Align.center);

		// Stack the image and the label at the top of our button
		button.stack(new Image(skin.getDrawable("top")), label).expand().fill();

		// Randomize the number of stars earned for demonstration purposes
		int stars = MathUtils.random(-1, +3);
		Table starTable = new Table();
		starTable.defaults().pad(5);
		if (stars >= 0) {
			for (int star = 0; star < 3; star++) {
				if (stars > star) {
					starTable.add(new Image(skin.getDrawable("star-filled"))).width(20).height(20);
				} else {
					starTable.add(new Image(skin.getDrawable("star-unfilled"))).width(20).height(20);
				}
			}
		}

		button.row();
		button.add(starTable).height(30);

		button.setName("Level" + Integer.toString(level));
		button.addListener(levelClickListener);
		return button;
	}

	/**
	 * Handle the click - in real life, we'd go to the level

	public ClickListener levelClickListener = new ClickListener() {
		@Override
		public void clicked (InputEvent event, float x, float y) {
			Gdx.app.log("FFfF","Click");
		}
	};

} **/
