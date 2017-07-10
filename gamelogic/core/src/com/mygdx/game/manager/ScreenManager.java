package com.mygdx.game.manager;

import com.badlogic.gdx.Screen;
import com.mygdx.game.AndroidInterfaces;
import com.mygdx.game.screen.LevelScreen;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.SplashScreen;
import com.mygdx.game.screen.TaskChoiceScreen;
import com.mygdx.game.screen.TaskScreen;


public class ScreenManager {
    public static int SCREEN_SPLASH = -1;
    public static int SCREEN_MENU = 0;
    public static int SCREEN_LEVEL = 1;
    public static int SCREEN_TASKCHOISE=2;
    public static int SCREEN_TASK=3;

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

    public void createTaskScreen(int num,String level,String task){
        switch (num){
            case 2:
                clearScreen();
                nowScreen = new TaskChoiceScreen(level);
                break;
            case 3:
                clearScreen();
                nowScreen = new TaskScreen(level,task);
                break;
        }
        nowScreen.show();
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
            case 1:
                clearScreen();
                nowScreen = new LevelScreen();
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

