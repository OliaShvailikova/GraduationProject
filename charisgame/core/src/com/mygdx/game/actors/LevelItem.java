package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;


public class LevelItem extends Actor {

    private static int N = 0;
    private int currentN;

    private TextureRegion texMenuItem;
    private TextureRegion tex;

    private BitmapFont font;

    public LevelItem( float _width, float _height ) {
        texMenuItem = new TextureRegion((Texture) AssetsManager.getInstance().assets.get("level_item.png"));
        tex = new TextureRegion(texMenuItem, 0, 0, 200, 200);

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
                MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_TASK);
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
        return x > 0 && x < this.getWidth() && y > 0 && y < this.getHeight()?this:null;
    }





    /** @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
    this.setWidth(this.getWidth()+ 10);
    this.setHeight(this.getHeight()+ 10);
    return true;
    }

     @Override
     public void touchUp(float x, float y, int pointer, int button) {
     this.setWidth(this.getWidth()- 10);
     this.setHeight(this.getHeight()- 10);
     } **/
}
