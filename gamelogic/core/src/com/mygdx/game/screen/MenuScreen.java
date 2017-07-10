package com.mygdx.game.screen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;

import java.util.concurrent.TimeUnit;


public class MenuScreen extends ParentScreen2D {

    private Image backgroundImage;
    private ImageButton playButton, exitButton;
    private BitmapFont bFont;

    @Override
    public void show() {
        super.show();
        bFont =  new BitmapFont();
        bFont.setColor(Color.BLACK);
        backgroundImage = new Image(AssetsManager.getInstance().assets.get("textures/menu_background.png",Texture.class));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
        final ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/menu_button_play_game.png")));


        playButton =  new ImageButton(playButtonStyle);
        playButton.setSize(Gdx.graphics.getHeight()/3.65f,Gdx.graphics.getHeight()/3.65f);
        playButton.setPosition(Gdx.graphics.getHeight()/10.4f , Gdx.graphics.getHeight()/7);
        stage.addActor(playButton);

        playButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ImageButton.ImageButtonStyle playButtonPressedStyle = new ImageButton.ImageButtonStyle();
                playButtonPressedStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/menu_button_play_game_pressed.png")));
                playButton.setStyle(playButtonPressedStyle);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playButton.setStyle(playButtonStyle);
                dispose();
                MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_LEVEL);
                super.touchUp(event, x, y, pointer, button);
            }
        });


        final ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/menu_button_exit.png")));

        exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(Gdx.graphics.getHeight()/3.65f,Gdx.graphics.getHeight()/3.65f);
        exitButton.setPosition(Gdx.graphics.getWidth()-exitButton.getWidth()-30 , Gdx.graphics.getHeight()/7);
        stage.addActor(exitButton);

        exitButton.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ImageButton.ImageButtonStyle exitButtonPressedStyle = new ImageButton.ImageButtonStyle();
                exitButtonPressedStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/menu_button_exit_pressed.png")));
                exitButton.setStyle(exitButtonPressedStyle);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                exitButton.setStyle(exitButtonStyle);
                Gdx.app.exit();
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }

    }

    @Override
    public void hide() {
        super.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        playButton=null;
        exitButton=null;
        backgroundImage=null;
    }
}

