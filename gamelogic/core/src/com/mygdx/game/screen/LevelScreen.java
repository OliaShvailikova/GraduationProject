package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;


public class LevelScreen extends PagedScreen {
    FileHandle[] fileHandle;

    @Override
    public void show() {
        super.show();
        fileHandle = Gdx.files.internal("levels/").list();
        for (int l = 0; l < fileHandle.length; l++) {
            Table levels = new Table().pad(Gdx.graphics.getHeight() / 14.6f);
            levels.defaults().pad(Gdx.graphics.getHeight() / 36.5f, Gdx.graphics.getHeight() / 18.25f, Gdx.graphics.getHeight() / 36.5f, Gdx.graphics.getHeight() / 18.25f);
            Label name = new Label(fileHandle[l].name(), skin);
            name.setFontScale(2f);
            name.setAlignment(Align.center);
            levels.add(name).expand().fill();
            levels.row();
            levels.add(getLevelButton(l)).expand().fill();
            scroll.addPage(levels);
        }
        container.add(scroll).expand().fill();
        final Button.ButtonStyle backButtonstyle = new Button.ButtonStyle();
        backButtonstyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/back.png")));
        final Button.ButtonStyle pressedBackButtonStyle = new Button.ButtonStyle();
        pressedBackButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/back_button_pressed.png")));

        back.setStyle(backButtonstyle);
        back.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setStyle(pressedBackButtonStyle);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setStyle(backButtonstyle);
                MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_MENU);
                super.touchUp(event, x, y, pointer, button);
            }

        });
        stage.addActor(back);

    }

    @Override
    public void hide() {
        super.dispose();
        fileHandle=null;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_MENU);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        fileHandle=null;
    }

    private Button getLevelButton(final int level) {
        Button button = new Button(skin);
        Button.ButtonStyle style = button.getStyle();
        style.up = style.down = null;
        // Stack the image and the label at the top of our button
        button.stack(new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/level_item.png")))).size(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileHandle[] fileHandle = Gdx.files.internal("levels/").list();
                MyGdxGame.getScreenManager().createTaskScreen(ScreenManager.SCREEN_TASKCHOISE,fileHandle[level].name(),null);
            }
        });
        return button;
    }
}
