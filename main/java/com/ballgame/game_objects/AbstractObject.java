package com.ballgame.game_objects;

import org.json.JSONObject;

public abstract class AbstractObject implements GameObject {
    
    protected static final int MAX_SIZE = 30;
    protected static final int MIN_SIZE = 10;
    protected double x=400;
    protected double y=400;
    /**
     * a value between 10 and 30(may be changed) which determines the radius of the ball
     * representing object
     */
    protected int size = 20 ;
    protected String color;
    protected boolean  changed = false;
    protected boolean toBeRemoved = false;

    @Override
    public synchronized double getX() {
        return x;
    }

    @Override
    public synchronized void setX(double x) {
        this.x = x;
    }

    @Override
    public synchronized double getY() {
        return y;
    }

    @Override
    public synchronized void setY(double y) {
        this.y = y;
    }

    @Override
    public synchronized int getSize() {
        return size;
    }

    @Override
    public synchronized void setSize(int size) {
        this.size = size;
        this.size = Math.min(this.size, AbstractObject.MAX_SIZE);
        this.size = Math.max(this.size, AbstractObject.MIN_SIZE);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public synchronized boolean isChanged() {
        return changed;
    }

    @Override
    public synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    public synchronized void prepareToRemove() {
        this.toBeRemoved = true;
        this.setChanged(true);
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }
    
    @Override
    public abstract JSONObject getChangedStatus();
    @Override
    public abstract JSONObject getFullStatus();
    @Override
    public abstract void update();
    
}
