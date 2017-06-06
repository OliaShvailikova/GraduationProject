package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
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
    private ImageButton highScoreButton,playButton,  aboutusButton;
    private BitmapFont bFont;

    @Override
    public void show() {
        super.show();

        bFont =  new BitmapFont();
        bFont.setColor(Color.BLACK);

        backgroundImage = new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("menu_background.png")));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        final ImageButton.ImageButtonStyle highScoreButtonStyle = new ImageButton.ImageButtonStyle();
        highScoreButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("menu_button_highscore.png")));

        highScoreButton = new ImageButton(highScoreButtonStyle);
        highScoreButton.setSize(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/5);
        highScoreButton.setPosition(30 , Gdx.graphics.getHeight()/7);
        stage.addActor(highScoreButton);

        highScoreButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                highScoreButton.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                highScoreButton.setVisible(true);

                // disable scaling when button is released
                super.touchUp(event, x, y, pointer, button);
            }
        });


        final ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("menu_button_play_game.png")));


        playButton =  new ImageButton(playButtonStyle);
        playButton.setSize(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/5);
        playButton.setPosition(playButton.getWidth(), Gdx.graphics.getHeight()/3 );
        stage.addActor(playButton);

        playButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ImageButton.ImageButtonStyle playButtonPressedStyle = new ImageButton.ImageButtonStyle();
                playButtonPressedStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("menu_button_play_game_pressed.png")));
                playButton.setStyle(playButtonPressedStyle);


                // playButton.setVisible(false);

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playButton.setStyle(playButtonStyle);

                MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_LEVEL);
                super.touchUp(event, x, y, pointer, button);
            }
        });


        final ImageButton.ImageButtonStyle aboutUsButtonStyle = new ImageButton.ImageButtonStyle();
        aboutUsButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("menu_button_information.png")));

        aboutusButton = new ImageButton(aboutUsButtonStyle);
        aboutusButton.setSize(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/5);
        aboutusButton.setPosition(Gdx.graphics.getWidth()-aboutusButton.getWidth()-30 , Gdx.graphics.getHeight()/7);
        stage.addActor(aboutusButton);

        aboutusButton.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                aboutusButton.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                aboutusButton.setVisible(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });




    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

