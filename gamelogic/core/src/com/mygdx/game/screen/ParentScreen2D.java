package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.manager.AssetsManager;


public class ParentScreen2D implements Screen {
    protected SpriteBatch spriteBatch;
    protected Stage stage;
    protected OrthographicCamera camera;
    protected Button back;


    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        stage = new Stage();
        camera = (OrthographicCamera)stage.getCamera();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        back = new Button();
        back.setSize(Gdx.graphics.getHeight()/7.3f,Gdx.graphics.getHeight()/7.3f);
        back.setPosition(Gdx.graphics.getHeight()/24.3f,Gdx.graphics.getHeight()/1.2f);
        //stage.addActor(back);

    }

    @Override
    public void render(float delta) {
        camera.update();
        spriteBatch.begin();
        stage.draw();
        stage.act(delta);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

