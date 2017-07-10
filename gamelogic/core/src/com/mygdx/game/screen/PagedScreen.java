package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.PagedScrollPane;
import com.mygdx.game.manager.AssetsManager;;


public class PagedScreen extends ParentScreen2D {
    protected Image backgroundImage;
    protected Skin skin;
    protected Table container;
    protected PagedScrollPane scroll;


    @Override
    public void show() {
        super.show();
        stage = new Stage(new ScreenViewport());
        backgroundImage = new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/level_background.png")));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
        skin = new Skin(Gdx.files.internal("textures/uiskin.json"));
        skin.add("top", skin.newDrawable("default-round", Color.RED), Drawable.class);
        skin.add("star-filled", skin.newDrawable("white", Color.YELLOW), Drawable.class);
        skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY), Drawable.class);
        Gdx.input.setInputProcessor(stage);
        container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(Gdx.graphics.getHeight()/29.2f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public void resize (int width,int height) {
        stage.getViewport().update(width,height);
    }

}



