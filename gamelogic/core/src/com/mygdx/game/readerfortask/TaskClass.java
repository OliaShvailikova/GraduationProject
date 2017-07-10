package com.mygdx.game.readerfortask;

import com.badlogic.gdx.utils.Array;

import java.util.Properties;

public class TaskClass {
    private boolean showTheory;
    private String taskPane;
    private String solutionPane;
    private String answer;
    private String boardWidth;
    private String boardHeight;
    private String solutionPictureAttributes;
    private boolean solutionPreDraw;
    private boolean implementation;
    private String taskSchema;
    public TaskClass(ReadIni ini) {
        Properties properties = ini.getProperties();
        this.showTheory = Boolean.parseBoolean(properties.getProperty("ShowTheory"));
        this.taskPane = properties.getProperty("TaskPane").trim();
        this.solutionPane = properties.getProperty("SolutionPane").trim();
        this.answer = properties.getProperty("Answer");
        this.boardWidth = properties.getProperty("BoardWidth").trim();
        this.boardHeight = properties.getProperty("BoardHeight").trim();
        this.solutionPictureAttributes = properties.getProperty("SolutionPictureAttributes").trim();
        this.solutionPreDraw = Boolean.parseBoolean(properties.getProperty("SolutionPreDraw"));
        this.implementation = Boolean.parseBoolean(properties.getProperty("Implementation"));
        this.taskSchema = properties.getProperty("TaskSchema");
    }

    public Array<String> getTaskSchema() {
        Array<String> strings = new Array<String>();
        char [] schema = taskSchema.toCharArray();
        for (int i=0;i<schema.length;i++){
            if (schema[i]!='('){
                strings.add(String.valueOf(schema[i]));
            }else
            if (schema[i]=='('){
                strings.add(String.valueOf(schema[i])+String.valueOf(schema[i+1])+String.valueOf(schema[i+2])+String.valueOf(schema[i+3]));
                i=i+3;
            }
        }
        return strings;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTaskPane() {

        return taskPane;
    }

    public boolean isSolutionPreDraw() {

        return solutionPreDraw;
    }

    public String getSolutionPictureAttributes() {

        return solutionPictureAttributes;
    }

    public String getSolutionPane() {

        return solutionPane;
    }

    public boolean isShowTheory() {

        return showTheory;
    }

    public boolean isImplementation() {

        return implementation;
    }

    public String getBoardWidth() {

        return boardWidth;
    }

    public String getBoardHeight() {

        return boardHeight;
    }
}
