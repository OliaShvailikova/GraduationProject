package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;


public class TaskItem extends Actor {

    private static int N = 0;
    private int currentN;

    private TextureRegion texMenuItem;
    private TextureRegion tex;

    private BitmapFont font;

    public TaskItem( float _width, float _height ) {
        texMenuItem = new TextureRegion((Texture) AssetsManager.getInstance().assets.get("task_item.png"));
        tex = new TextureRegion(texMenuItem, 0, 0, 128, 128);

        this.setWidth(_width);
        this.setHeight(_height);

        font = new BitmapFont();

        // Глобальная нумерация кнопок выбора уровня
        N += 1;
        currentN = N;
        setTouchable(Touchable.enabled);
        // setBounds(getX(),getY(),128,128);
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false);
                // MyGame.getScreenManager().createScreen(ScreenManager.SCREEN_PLAY);
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                setVisible(true);
            }
        });

    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tex, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());

        font.setColor(1, 1, 1, 1);

        font.getData().setScale(2);
        font.draw(batch, String.valueOf(currentN), this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2 );
    }



    @Override
    public Actor hit(float x, float y, boolean touchable) {
        setTouchable(Touchable.disabled);
        return x > 0 && x < this.getWidth() && y > 0 && y < this.getHeight()?this:null;
    }
}

