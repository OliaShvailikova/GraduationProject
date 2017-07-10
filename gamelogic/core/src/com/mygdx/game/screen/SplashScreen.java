package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;


public class SplashScreen extends ParentScreen2D {

    Image backgroundImage;

    @Override
    public void show() {
        super.show();
        backgroundImage = new Image(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/game_loading.jpg")));
        backgroundImage.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
        AssetsManager.getInstance().load2DTextures();


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //0 means no asset loaded
        //1 means all assets are loaded
        AssetsManager.getInstance().assets.update();
        if(AssetsManager.getInstance().assets.getProgress() >= 1f){
            //MenuScreen
            MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_MENU);
        }


    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

