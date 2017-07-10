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

public class TaskChoiceScreen extends PagedScreen {
    private String levels;
    FileHandle[] fileHandle;

    public  TaskChoiceScreen(String level){
        this.levels=level;
    }

    @Override
    public void show() {
        super.show();
        fileHandle = Gdx.files.internal("levels/"+levels+"/").list();
        Label title = new Label(levels+". Number of tasks "+fileHandle.length+".", skin);
        title.setFontScale(2f);
        title.setPosition(200,Gdx.graphics.getHeight()/1.2f);
        stage.addActor(title);
        int c = 1;
        for (int l = 0; l < fileHandle.length; l++) {
            Table levels = new Table().pad(Gdx.graphics.getHeight()/14.6f);
            levels.defaults().pad(Gdx.graphics.getHeight()/36.5f, Gdx.graphics.getHeight()/18.25f, Gdx.graphics.getHeight()/36.5f, Gdx.graphics.getHeight()/18.25f);
            if (fileHandle[l].name().contains("ini")){
            levels.add(getLevelButton(c++)).expand().fill();
            scroll.addPage(levels);}
        }
        container.add(scroll).expand().fill();
        final Button.ButtonStyle backButtonstyle = new Button.ButtonStyle();
        backButtonstyle.up = new TextureRegionDrawable(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/back.png")));
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
                MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_LEVEL);
                super.touchUp(event, x, y, pointer, button);
            }

        });
        stage.addActor(back);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            MyGdxGame.getScreenManager().createScreen(ScreenManager.SCREEN_LEVEL);
        }
    }

    @Override
    public void dispose() {
       super.dispose();
        fileHandle=null;
        back=null;
    }

    private Button getLevelButton(final int level) {
        Button button = new Button(skin);
        Button.ButtonStyle style = button.getStyle();
        style.up = 	style.down = null;
        Label label = new Label(Integer.toString(level), skin);
        label.setFontScale(2f);
        label.setAlignment(Align.center);
        button.stack(new Image(skin.getDrawable("top")), label).fill().height(Gdx.graphics.getHeight()/7.3f);
        Table starTable = new Table();
        starTable.defaults().pad(5);
            for (int star = 0; star < 3; star++) {
                    starTable.add(new Image(skin.getDrawable("star-filled"))).width(Gdx.graphics.getHeight()/36.5f).height(Gdx.graphics.getHeight()/36.5f);
            }

        button.row();
        button.add(starTable).height(Gdx.graphics.getHeight()/24.3f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.getScreenManager().createTaskScreen(ScreenManager.SCREEN_TASK,levels,Integer.toString(level));
            }
        });
        label=null;
        return button;
    }

}


