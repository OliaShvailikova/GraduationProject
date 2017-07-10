package com.mygdx.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class AssetsManager { //Sigelton class
    private static AssetsManager instance;

    public static  AssetsManager getInstance(){
        if(instance == null){
            instance = new AssetsManager();
        }
        return instance;
    }

    public AssetManager assets;

    private AssetsManager(){
        assets = new AssetManager();
    }

    public void loadSplashScreen(){
        assets.load("textures/game_loading.jpg", Texture.class);
        assets.finishLoading();
    }

    public void load2DTextures(){
        assets.load("textures/menu_background.png", Texture.class);
        assets.load("textures/menu_button_play_game.png", Texture.class);
        assets.load("textures/menu_button_play_game_pressed.png", Texture.class);
        assets.load("textures/menu_button_exit_pressed.png", Texture.class);
        assets.load("textures/menu_button_exit.png", Texture.class);
        assets.load("textures/level_item.png", Texture.class);
        assets.load("textures/level_background.png", Texture.class);
        assets.load("textures/back.png",Texture.class);
        assets.load("textures/back_button_pressed.png",Texture.class);
        assets.load("textures/check.png",Texture.class);
        assets.load("textures/check_button_pressed.png",Texture.class);
        assets.load("textures/pencil.png",Texture.class);
        assets.load("textures/folder.png",Texture.class);
        assets.load("textures/buttons.txt",TextureAtlas.class);
    }


}
