package com.mygdx.game.gamelogic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Home on 22.06.2017.
 */

public class Button {

    public TextureAtlas textureAtlas;

    public Button(){
        textureAtlas = new TextureAtlas("textures/buttons.txt");
    }

    public ImageButton.ImageButtonStyle createButtonStyle(String nameofButton){
        final ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(textureAtlas.findRegion(nameofButton));
        return buttonStyle;
    }
}
