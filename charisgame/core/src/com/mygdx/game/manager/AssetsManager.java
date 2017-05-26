package com.mygdx.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;


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
        assets.load("game_loading.jpg", Texture.class);
        assets.finishLoading();
    }

    public void load2DTextures(){
        assets.load("game_background_layer_1.png", Texture.class);
        assets.load("game_background_layer_2.png", Texture.class);
        assets.load("game_background_layer_3.png", Texture.class);
        assets.load("game_background_score.png", Texture.class);

        assets.load("menu_background.png", Texture.class);
        assets.load("menu_button_play_game.png", Texture.class);
        assets.load("menu_button_play_game_pressed.png", Texture.class);
        assets.load("menu_button_highscore.png", Texture.class);
        assets.load("menu_button_information.png", Texture.class);

        assets.load("game_block_left.png", Texture.class);
        assets.load("game_block_middle.png", Texture.class);
        assets.load("game_block_right.png", Texture.class);
        assets.load("game_bonusscore.png", Texture.class);
        assets.load("game_button_play_again.png", Texture.class);
        assets.load("game_button_save.png", Texture.class);
        assets.load("game_character_spritesheet.png", Texture.class);
        assets.load("game_highscoremark.png", Texture.class);

        assets.load("game_new_highscore.png", Texture.class);

        assets.load("game_numberfont.png", Texture.class);
        assets.load("game_obstacle_bonus.png", Texture.class);
        assets.load("game_obstacle_jump.png", Texture.class);
        assets.load("game_obstacle_jump_animated.png", Texture.class);
        assets.load("game_obstacle_slow.png", Texture.class);
        assets.load("game_waves.png", Texture.class);

        assets.load("level_background.png", Texture.class);
        assets.load("level_item.png", Texture.class);
        assets.load("task_item.png", Texture.class);



    }


}
