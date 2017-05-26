package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;

public class MyGdxGame extends ApplicationAdapter {
	private static ScreenManager screenManager;
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
