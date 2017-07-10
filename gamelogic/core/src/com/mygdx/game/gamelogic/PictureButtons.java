package com.mygdx.game.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.manager.AssetsManager;
import com.mygdx.game.readerfortask.TaskClass;
import com.mygdx.game.screen.TaskScreen;
import com.mygdx.game.svg.MakeSvg;

public class PictureButtons {
    public TextureAtlas textureAtlas;
    Stage stage;
    Image image;
    TaskClass taskClass;
    ImageButton right, left, up, down, rightUp, rightDown, leftUp, leftDown;
    Table table;
    boolean preDraw;
    int count;
    MakeSvg svg;

    public PictureButtons(Stage stage, Image image, TaskClass taskClass, boolean preDraw) {
        this.stage = stage;
        textureAtlas = new TextureAtlas("textures/buttons.txt");
        this.image = image;
        count = 7;
        this.taskClass = taskClass;
        this.preDraw = preDraw;
        right = new ImageButton(createButtonStyle("R"));
        left = new ImageButton(createButtonStyle("L"));
        up = new ImageButton(createButtonStyle("U"));
        down = new ImageButton(createButtonStyle("D"));
        rightDown = new ImageButton(createButtonStyle("(RD)"));
        leftDown = new ImageButton(createButtonStyle("(LD)"));
        rightUp = new ImageButton(createButtonStyle("(RU)"));
        leftUp = new ImageButton(createButtonStyle("(LU)"));
        buttClic(right, "R", preDraw);
        buttClic(left, "L", preDraw);
        buttClic(down, "D", preDraw);
        buttClic(up, "U", preDraw);
        buttClic(rightDown, "(RD)", preDraw);
        buttClic(rightUp, "(RU)", preDraw);
        buttClic(leftDown, "(LD)", preDraw);
        buttClic(leftUp, "(LU)", preDraw);
        svg = new MakeSvg();
    }


    public ImageButton.ImageButtonStyle createButtonStyle(String nameofButton) {
        final ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(textureAtlas.findRegion(nameofButton));
        return buttonStyle;
    }

    public void createTable() {
        Image pencil = new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("textures/pencil.png")));
        table = new Table();
        table.add(leftUp).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.add(up).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.add(rightUp).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.row();
        table.add(left).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.add(pencil).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.add(right).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.row();
        table.add(leftDown).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.add(down).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.add(rightDown).size(Gdx.graphics.getHeight()/14.6f, Gdx.graphics.getHeight()/14.6f);
        table.setWidth(stage.getWidth());
        table.setFillParent(true);
        table.setPosition(TaskScreen.posX, TaskScreen.posY);
        stage.addActor(table);
        checkSolution();
    }

    public void checkSolution() {
        if (!taskClass.getAnswer().contains("(")) {
            leftDown.setVisible(false);
            leftUp.setVisible(false);
            rightDown.setVisible(false);
            rightUp.setVisible(false);
            checkSimpleButtons();
        } else {
            checkSimpleButtons();
            checkDiagonalButtons();
        }
    }

    public void checkSimpleButtons() {
        float leftborder = image.getX() + image.getWidth();
        float topborder = image.getY() + image.getHeight();
        float tableleft = table.getX() + table.getWidth();
        float tabletop = table.getY() + table.getHeight();
        if (image.getX() / table.getX() > 7) {
            left.setVisible(false);
        } else {
            left.setVisible(true);
        }
        if (image.getY() / table.getY() < 0.5 && image.getY() / table.getY() > 0) {
            up.setVisible(false);
        } else {
            up.setVisible(true);
        }
        if (leftborder / tableleft < 0.68) {
            right.setVisible(false);
        } else {
            right.setVisible(true);
        }
        if (topborder / tabletop > 1.07 && topborder / tabletop < 2) {
            down.setVisible(false);
        } else {
            down.setVisible(true);
        }
    }

    public void reDraw(int count,boolean grid){
        stage.getActors().get(count).remove();
        stage.getActors().get(count-1).remove();
        stage.addActor(svg.drawSVG(
                image,
                Gdx.graphics.getWidth() / 1.8f,
                Gdx.graphics.getHeight() / 6.0f,
                TaskScreen.ownPath, taskClass.getBoardWidth(), taskClass.getBoardHeight(), TaskScreen.ID_COLOR_RED, grid));

    }

    public void buttClic(final ImageButton buttn, final String string, final boolean preDraw) {
        buttn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttn.setStyle(new Button().createButtonStyle(string + "-pressed"));
                TaskScreen.ownPath = TaskScreen.ownPath + string;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttn.setStyle(new Button().createButtonStyle(string));
                if (preDraw == false) {
                    reDraw(7,true);
                } else {
                    if (count == 7) {
                        stage.getActors().get(count).remove();
                        count++;
                        stage.addActor(svg.drawSVG(
                                image,
                                Gdx.graphics.getWidth() / 1.8f,
                                Gdx.graphics.getHeight() / 6.0f, TaskScreen.ownPath, taskClass.getBoardWidth(), taskClass.getBoardHeight(), TaskScreen.ID_COLOR_RED, false));
                    }
                    else {
                        reDraw(8,false);
                    }
                }
                if (string.equals("R")) {
                    TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
                }
                if (string.equals("L")) {
                    TaskScreen.posX = TaskScreen.posX - image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) + Gdx.graphics.getWidth() / 147;
                }
                if (string.equals("U")) {
                    TaskScreen.posY = TaskScreen.posY + image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) - Gdx.graphics.getWidth() / 173;
                }
                if (string.equals("D")) {
                    TaskScreen.posY = TaskScreen.posY - image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) + Gdx.graphics.getWidth() / 173;
                }
                if (string.equals("(RD)")) {
                    TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
                    TaskScreen.posY = TaskScreen.posY - image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) + Gdx.graphics.getWidth() / 147;
                }
                if (string.equals("(RU)")) {
                    TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
                    TaskScreen.posY = TaskScreen.posY + image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) - Gdx.graphics.getWidth() / 147;
                }
                if (string.equals("(LU)")) {
                    TaskScreen.posX = TaskScreen.posX - image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) + Gdx.graphics.getWidth() / 147;
                    TaskScreen.posY = TaskScreen.posY + image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) - Gdx.graphics.getWidth() / 147;
                }
                if (string.equals("(LD)")) {
                    TaskScreen.posX = TaskScreen.posX - image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) + Gdx.graphics.getWidth() / 147;
                    TaskScreen.posY = TaskScreen.posY - image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) + Gdx.graphics.getWidth() / 147;
                }
                table.setPosition(TaskScreen.posX, TaskScreen.posY);
                checkSolution();
                stage.addActor(table);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    public void checkDiagonalButtons() {
        float leftborder = image.getX() + image.getWidth();
        float topborder = image.getY() + image.getHeight();
        float tableleft = table.getX() + table.getWidth();
        float tabletop = table.getY() + table.getHeight();
        if (image.getX() / table.getX() > 7 || (topborder / tabletop > 1.07 && topborder / tabletop < 2)) {
            leftDown.setVisible(false);
        } else {
            leftDown.setVisible(true);
        }
        if (image.getX() / table.getX() > 7 || (image.getY() / table.getY() < 0.5 && image.getY() / table.getY() > 0)) {
            leftUp.setVisible(false);
        } else {
            leftUp.setVisible(true);
        }
        if ((image.getY() / table.getY() < 0.5 && image.getY() / table.getY() > 0) || leftborder / tableleft < 0.68) {
            rightUp.setVisible(false);
        } else {
            rightUp.setVisible(true);
        }
        if (leftborder / tableleft < 0.68 || (topborder / tabletop > 1.07 && topborder / tabletop < 2)) {
            rightDown.setVisible(false);
        } else {
            rightDown.setVisible(true);
        }
    }

    public void deleteButton() {
        TaskScreen.posX = Gdx.graphics.getWidth() / 12.8f;
        TaskScreen.posY = Gdx.graphics.getHeight() / 2.968f;
        TaskScreen.ownPath = "";
        table.setPosition(TaskScreen.posX, TaskScreen.posY);
        if (preDraw == false) {
            reDraw(7,true);
        } else {
            stage.getActors().get(count).remove();
            stage.getActors().get(count - 1).remove();
            count--;
        }
        checkSolution();
        stage.addActor(table);
    }

    public void returnButton() {
        Array<String> string = new Array<String>();
        char[] schema = TaskScreen.ownPath.toCharArray();
        for (int i = 0; i < schema.length; i++) {
            if (schema[i] != '(') {
                string.add(String.valueOf(schema[i]));
            } else if (schema[i] == '(') {
                string.add(String.valueOf(schema[i]) + String.valueOf(schema[i + 1]) + String.valueOf(schema[i + 2]) + String.valueOf(schema[i + 3]));
                i = i + 3;
            }
        }
        if (string.get(string.size-1).equals("R")) {
                TaskScreen.posX = TaskScreen.posX - image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) + Gdx.graphics.getWidth() / 147;
        }
        if (string.get(string.size-1).equals("L")) {
                TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
        }
        if (string.get(string.size-1).equals("U")) {
                TaskScreen.posY = TaskScreen.posY - image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) + Gdx.graphics.getWidth() / 173;
        }
        if (string.get(string.size-1).equals("D")) {
                TaskScreen.posY = TaskScreen.posY + image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) - Gdx.graphics.getWidth() / 173;
        }
        if (string.get(string.size-1).equals("(RD)")) {
                TaskScreen.posX = TaskScreen.posX - image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) + Gdx.graphics.getWidth() / 147;
                TaskScreen.posY = TaskScreen.posY + image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) - Gdx.graphics.getWidth() / 147;
        }
        if (string.get(string.size-1).equals("(RU)")) {
                TaskScreen.posX = TaskScreen.posX - image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) + Gdx.graphics.getWidth() / 147;
                TaskScreen.posY = TaskScreen.posY - image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) + Gdx.graphics.getWidth() / 147;
        }
        if (string.get(string.size-1).equals("(LU)")) {
                TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
                TaskScreen.posY = TaskScreen.posY - image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) + Gdx.graphics.getWidth() / 147;
        }
        if (string.get(string.size-1).equals("(LD)")) {
                TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
                TaskScreen.posY = TaskScreen.posY + image.getHeight() / Integer.parseInt(taskClass.getBoardHeight()) - Gdx.graphics.getWidth() / 147;
        }
        table.setPosition(TaskScreen.posX, TaskScreen.posY);
        TaskScreen.ownPath = "";
        for (int i = 0; i < string.size - 1; i++) {
            TaskScreen.ownPath = TaskScreen.ownPath + string.get(i);
        }
        if (preDraw == false) {
            reDraw(7,true);
        } else {
            reDraw(8,false);
        }
        checkSolution();
        stage.addActor(table);
    }


    public void jumpButton() {
        TaskScreen.posX = TaskScreen.posX + image.getWidth() / Integer.parseInt(taskClass.getBoardWidth()) - Gdx.graphics.getWidth() / 147;
        TaskScreen.ownPath = TaskScreen.ownPath + "J";
        table.setPosition(TaskScreen.posX, TaskScreen.posY);
        stage.getActors().get(7).remove();
        checkSolution();
        stage.addActor(table);
    }

    public void circleButton() {
        TaskScreen.ownPath = TaskScreen.ownPath + "O";
        reDraw(7,true);
        stage.addActor(table);
    }

}



