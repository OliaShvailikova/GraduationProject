package com.mygdx.game.readerfortask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class ReadIni {
    Properties properties;
    private String taskName;
    private String level;

    public ReadIni(String level,String taskName) {
        this.taskName=taskName;
        this.level=level;
        FileHandle fileHandle = Gdx.files.internal("levels/"+level+"/task"+taskName+".ini");
        properties = new Properties();
        try {
            properties.load(new StringReader(fileHandle.readString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Properties getProperties() {
        return properties;
    }

}
