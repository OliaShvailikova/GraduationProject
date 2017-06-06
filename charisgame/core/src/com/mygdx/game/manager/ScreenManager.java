package com.mygdx.game.manager;

import com.badlogic.gdx.Screen;
import com.mygdx.game.screen.LevelScreen;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.SplashScreen;
import com.mygdx.game.screen.TaskScreen;

/**
 * Created by Home on 17.05.2017.
 */

public class ScreenManager {
    public static int SCREEN_SPLASH = -1;
    public static int SCREEN_MENU = 0;
    public static int SCREEN_PLAY = 1;
    public static int SCREEN_GAME_OVER = 2;
    public static int SCREEN_LEVEL = 3;
    public static int SCREEN_TASK=4;

    private Screen nowScreen;

    public ScreenManager(){
        if(nowScreen == null){
            nowScreen = new SplashScreen();

        }
        nowScreen.show();
    }

    public Screen getNowScreen(){
        return nowScreen;
    }

    public void createScreen(int num){
        switch (num){
            case -1:
                clearScreen();
                nowScreen = new SplashScreen();
                break;
            case 0:
                clearScreen();
                nowScreen = new MenuScreen();
                break;
            case 3:
                clearScreen();
                nowScreen = new LevelScreen();
                break;
            case 4:
                clearScreen();
                nowScreen = new TaskScreen();
                break;
        }

        nowScreen.show();

    }

    public void clearScreen(){
        if(nowScreen != null){
            nowScreen.dispose();
        }
    }
}

