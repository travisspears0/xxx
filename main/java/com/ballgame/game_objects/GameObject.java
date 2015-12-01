package com.ballgame.game_objects;

import org.json.JSONObject;

public interface GameObject {
    /*
    public enum SHAPE {
        CIRCLE,
        SQUARE
    }*/
    /*
    public enum COLOR {
        _FF0000,//red
        _00FF00,//green
        _0000FF,//blue
        _990099,//purple
        _FF9900,//orange
        _996600,//brown
        _FF0099,//pink
        _FFFF33,//yellow
        _FFFFFF,//white
        _000000//black
    }
    */
    public double getX();
    public void setX(double x);
    public double getY();
    public void setY(double y);
    public int getSize();
    public void setSize(int size);
    public String getColor();
    public void setColor(String color);
    public void setChanged(boolean changed);
    public boolean isChanged();
    public JSONObject getChangedStatus();
    public JSONObject getFullStatus();
    public void update();
    
}
