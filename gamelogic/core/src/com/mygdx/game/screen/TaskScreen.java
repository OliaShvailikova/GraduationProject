package com.mygdx.game.screen;


import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.gamelogic.Button;
import com.mygdx.game.gamelogic.PictureButtons;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.manager.ScreenManager;
import com.mygdx.game.readerfortask.ReadIni;
import com.mygdx.game.readerfortask.TaskClass;
import com.mygdx.game.svg.MakeSvg;


public class TaskScreen extends ParentScreen2D {

    private Image taskPane, solutionPane,backgroundImage;
    public static final int ID_COLOR_BLUE = 0;
    public static final int ID_COLOR_RED = 1;
    public static final int ID_COLOR_GRAY = 2;
    private Array<String> strings;
    private String path;
    public static String ownPath="";
    private TaskClass taskClass;
    public static float posX,posY;
    private ImageButton circle,jump,delete,reset,check;
    //private com.badlogic.gdx.scenes.scene2d.ui.Button check;
    private String task;
    private String level;
    private Skin skin;

    public  TaskScreen(String level,String task){
        this.task=task;
        this.level=level;
        ownPath="";
    }

    @Override
    public void show() {
        super.show();
        backgroundImage = new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/level_background.png")));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
        skin = new Skin(Gdx.files.internal("textures/uiskin.json"));
        final com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle backButtonstyle = new com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle();
        backButtonstyle.up = new TextureRegionDrawable(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/back.png")));
        final com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle pressedBackButtonStyle = new com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle();
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
                MyGdxGame.getScreenManager().createTaskScreen(ScreenManager.SCREEN_TASKCHOISE,level,null);
                super.touchUp(event, x, y, pointer, button);
            }

        });
        stage.addActor(back);

        final ImageButton.ImageButtonStyle checkButtonstyle = new ImageButton.ImageButtonStyle();
        checkButtonstyle.up = new TextureRegionDrawable(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/check.png")));
        check = new ImageButton(checkButtonstyle);
        check.setPosition(Gdx.graphics.getWidth()/1.113f,Gdx.graphics.getHeight()/1.2f);
        check.setSize(Gdx.graphics.getHeight()/7.3f,Gdx.graphics.getHeight()/7.3f);

        check.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ImageButton.ImageButtonStyle pressedCheckButtonStyle = new ImageButton.ImageButtonStyle();
                pressedCheckButtonStyle.up = new TextureRegionDrawable(new TextureRegion((Texture)AssetsManager.getInstance().assets.get("textures/check_button_pressed.png")));
                check.setStyle(pressedCheckButtonStyle);
                MyGdxGame.aoi.toast(checkAnswer());
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                check.setStyle(checkButtonstyle);
                boolean check=checkAnswer();
                if (check){
                    MyGdxGame.getScreenManager().createTaskScreen(ScreenManager.SCREEN_TASKCHOISE,level,null);
                } else {
                    MyGdxGame.getScreenManager().createTaskScreen(ScreenManager.SCREEN_TASK,level,task);
                }

                super.touchUp(event, x, y, pointer, button);
            }

        });
        stage.addActor(check);
        taskClass = new TaskClass(new ReadIni(level,task));
        Array<String> strings = new Array<String>();
        char [] schema = taskClass.getAnswer().toCharArray();
        for (int i=0;i<schema.length;i++){
            if (schema[i]!='('){
                strings.add(String.valueOf(schema[i]));
            }else
            if (schema[i]=='('){
                strings.add(String.valueOf(schema[i])+String.valueOf(schema[i+1])+String.valueOf(schema[i+2])+String.valueOf(schema[i+3]));
                i=i+3;
            }
        }
        Label title = new Label("Number of steps "+strings.size+".", skin);
        title.setFontScale(2f);
        title.setPosition(400,Gdx.graphics.getHeight()/1.07f);
        stage.addActor(title);
        if (taskClass.getTaskPane().equals("Draw")) {
            MakeSvg task = new MakeSvg();
            stage.addActor(task.drawSVG(taskPane, Gdx.graphics.getWidth() / 22f, Gdx.graphics.getHeight() / 6.0f,taskClass.getAnswer(), taskClass.getBoardWidth(),
                    taskClass.getBoardHeight(), ID_COLOR_BLUE, true));
        }
        if (taskClass.getTaskPane().equals("Schema")) {
            Table table = new Table();
            table.setWidth(stage.getWidth());
            table.setFillParent(true);
            table.left().padLeft(50);
            strings = taskClass.getTaskSchema();
            for (int i = 0; i < strings.size; i++) {
                int height = 0;
                int width = 0;
                if (strings.size <= 12) {
                    height = 150;
                    width = 130;
                    if (i == 4 | i == 8 | i == 12) {
                        table.row();
                    }
                }
                if (strings.size <= 30 && strings.size > 12) {
                    height = 120;
                    width = 100;
                    if (i == 6 | i == 12 | i == 18 | i == 24) {
                        table.row();
                    }
                }
                Button button = new Button();
                table.add(new ImageButton(button.createButtonStyle(strings.get(i)))).size(width, height);
            }
            stage.addActor(table);
        }
        if (taskClass.getSolutionPane().equals("Draw")) {
            path = "";
            boolean preDraw=false;
            if (taskClass.isSolutionPreDraw()) {
                path = taskClass.getAnswer();
                preDraw=true;
            }
            MakeSvg solution = new MakeSvg();
            solutionPane =solution.drawSVG(solutionPane, Gdx.graphics.getWidth() / 1.8f, Gdx.graphics.getHeight() / 6.0f,path, taskClass.getBoardWidth(), taskClass.getBoardHeight(),
                    ID_COLOR_GRAY, true);
            posX=Gdx.graphics.getWidth()/12.8f;
            posY=Gdx.graphics.getHeight()/2.968f;
            PictureButtons pictureButtons=new PictureButtons(stage,solutionPane,taskClass,preDraw);
            addToTable(pictureButtons);
            stage.addActor(solutionPane);
            pictureButtons.createTable();
        }
        if (taskClass.getSolutionPane().equals("Schema")) {
            Image folder = new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/folder.png")));
            folder.setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 1.2f);
            folder.setPosition(Gdx.graphics.getWidth() / 2.2f, Gdx.graphics.getHeight() / 6f);
            stage.addActor(folder);
            Table table = new Table();
            table.setWidth(stage.getWidth());
            table.setFillParent(true);
            table.bottom().padLeft(Gdx.graphics.getWidth() / 5f);
            Table tables = new Table();
            tables.setWidth(stage.getWidth());
            tables.padLeft(Gdx.graphics.getWidth() / 4.5f).padBottom(Gdx.graphics.getHeight()/0.8f);
            String[] string = taskClass.getSolutionPictureAttributes().split(",");
            //addToTable(table,string);
            table.debugAll();
            Gdx.app.log("fffff", String.valueOf(table.getCells().size));
            stage.addActor(tables);
            stage.addActor(table);
        }
    }

   /** public void buttClic(final ImageButton buttn, final String string, final float pencilPosX, final float pencilPosY){
        buttn.addListener(new ClickListener(){
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttn.setStyle(new Button().createButtonStyle(string+"-pressed"));
                ownPath=ownPath+string;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttn.setStyle(new Button().createButtonStyle(string));
                stage.getActors().get(3).remove();
                stage.getActors().get(2).remove();
                if(string.equals("R")){
                    posX = posX+solutionPane.getWidth()/Integer.parseInt(taskClass.getBoardWidth())-Gdx.graphics.getWidth()/147;}
                if(string.equals("L")){
                    posX = posX-solutionPane.getWidth()/Integer.parseInt(taskClass.getBoardWidth())+Gdx.graphics.getWidth()/147;}
                if(string.equals("U")){
                    posY = posY+solutionPane.getHeight()/Integer.parseInt(taskClass.getBoardHeight())-Gdx.graphics.getWidth()/173;}
                if(string.equals("D")){
                    posY = posY-solutionPane.getHeight()/Integer.parseInt(taskClass.getBoardHeight())+Gdx.graphics.getWidth()/173;}
                if(string.equals("(RD)")){
                    posX = posX+solutionPane.getWidth()/Integer.parseInt(taskClass.getBoardWidth())-Gdx.graphics.getWidth()/147;
                    posY = posY-solutionPane.getHeight()/Integer.parseInt(taskClass.getBoardHeight())+Gdx.graphics.getWidth()/147;
                }
                if(string.equals("(RU)")){
                    posX = posX+solutionPane.getWidth()/Integer.parseInt(taskClass.getBoardWidth())-Gdx.graphics.getWidth()/147;
                    posY = posY+solutionPane.getHeight()/Integer.parseInt(taskClass.getBoardHeight())-Gdx.graphics.getWidth()/147;}
                if(string.equals("(LU)")){
                    posX = posX-solutionPane.getWidth()/Integer.parseInt(taskClass.getBoardWidth())+Gdx.graphics.getWidth()/147;
                    posY = posY+solutionPane.getHeight()/Integer.parseInt(taskClass.getBoardHeight())-Gdx.graphics.getWidth()/147;}
                if(string.equals("(LD)")){
                    posX = posX-solutionPane.getWidth()/Integer.parseInt(taskClass.getBoardWidth())+Gdx.graphics.getWidth()/147;
                    posY = posY-solutionPane.getHeight()/Integer.parseInt(taskClass.getBoardHeight())+Gdx.graphics.getWidth()/147;}
                pencil.setPosition(posX,posY);
                stage.addActor(new MakeSvg().drawSVG(
                        solutionPane,
                        Gdx.graphics.getWidth() / 1.8f,
                        Gdx.graphics.getHeight() / 6.0f,ownPath,taskClass.getBoardWidth(),taskClass.getBoardHeight(),ID_COLOR_RED,true));
                stage.addActor(pencil);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    } **/

    public void addToTable(final PictureButtons pictureButtons){
        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.setFillParent(true);
        table.bottom();
        String[] buttons = taskClass.getSolutionPictureAttributes().split(",");
        Button button=new Button();
        delete=new ImageButton(button.createButtonStyle("del"));
        delete.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    pictureButtons.deleteButton();
                super.touchUp(event, x, y, pointer, button);
            }

        });
        table.add(delete).size(Gdx.graphics.getHeight()/12.1f, Gdx.graphics.getHeight()/9.1f);
        reset=new ImageButton(button.createButtonStyle("return"));
        reset.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pictureButtons.returnButton();
                super.touchUp(event, x, y, pointer, button);
            }

        });
        table.add(reset).size(Gdx.graphics.getHeight()/12.1f, Gdx.graphics.getHeight()/9.1f);
        for (int i= 0; i < buttons.length; i++) {
            if (buttons[i].equals("J")) {
                jump=new ImageButton(button.createButtonStyle("J"));
                jump.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return super.touchDown(event, x, y, pointer, button);
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        pictureButtons.jumpButton();
                        super.touchUp(event, x, y, pointer, button);
                    }

                });
                table.add(jump).size(Gdx.graphics.getHeight()/12.1f, Gdx.graphics.getHeight()/9.1f);
            }
            if (buttons[i].equals("Circle")) {
                circle=new ImageButton(button.createButtonStyle("O"));
                circle.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return super.touchDown(event, x, y, pointer, button);
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        pictureButtons.circleButton();
                        super.touchUp(event, x, y, pointer, button);
                    }

                });
                table.add(circle).size(Gdx.graphics.getHeight()/12.1f, Gdx.graphics.getHeight()/9.1f);
            }
        }
        stage.addActor(table);
    }


    public boolean checkAnswer(){
        if (taskClass.getAnswer().equals(ownPath)){
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            MyGdxGame.getScreenManager().createTaskScreen(ScreenManager.SCREEN_TASKCHOISE,level,null);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        //taskClass=null;

    }
}
