package com.ballgame.objects;

public class Vector {
    
    private static final int MAX_SPEED = 15;
    
    private double x;
    private double y;
    private double speed;
    private double angle;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.updateAngle();
        this.updateSpeed();
    }
    
    public Vector(double angle, int speed) {
        this.angle = angle % (Math.PI*2);
        this.speed = speed;
        this.x = speed * Math.sin(this.angle);
        this.y = speed * Math.cos(this.angle);
    }

    public synchronized double getX() {
        return x;
    }
    
    public synchronized void setX(double x) {
        this.setX(x, true);
    }
    
    public synchronized void setX(double x, boolean update) {
        this.x = x;
        if(update) {
            this.updateSpeed();
            this.updateAngle();
        }
    }

    public synchronized double getY() {
        return y;
    }
    
    public synchronized void setY(double y, boolean update) {
        this.y = y;
        if(update) {
            this.updateSpeed();
            this.updateAngle();
        }
    }

    public synchronized void setY(double y) {
        this.setY(y,true);
    }
    
    public synchronized double getAngle() {
        return this.angle;
    }
    
    public synchronized void setAngle(double angle) {
        this.angle = angle;
        this.updateXY();
    }
    
    public synchronized double getSpeed() {
        return this.speed;
    }
    
    public synchronized void setSpeed(double speed) {
        this.speed = speed;
        this.speed = Math.min(speed, Vector.MAX_SPEED);
        this.speed = Math.max(this.speed, 1);
        this.x = speed * Math.sin(this.getAngle());
        this.y = speed * Math.cos(this.getAngle());
    }
    
    public synchronized void combineWith(Vector v) {
        this.x += v.getX();
        this.y += v.getY();
        this.updateAngle();
        this.updateSpeed();
    }
    
    private synchronized void updateSpeed() {
        this.speed = (int)(this.x/Math.sin(this.getAngle()));
    }
    
    private synchronized void updateAngle() {
        this.angle = Math.atan2(this.x, this.y);
    }
    
    private synchronized void updateXY() {
        this.x = this.speed * Math.sin(this.angle);
        this.y = this.speed * Math.cos(this.angle);
    }
    
    @Override
    public String toString() {
        return "vector ["+this.round(this.x)+","+this.round(this.y)+"], angle: " 
            + this.round(this.getAngle()) + ", speed: " + this.speed ;
    }
    
    private double round(double number) {
        return this.round(number, 1000);
    }
    
    private double round(double number, int places) {
        double d = number*places;
        int i = (int)d;
        d = i;
        d /= places;
        return d;
    }
    
    public static Vector combineVectors(Vector v1, Vector v2) {
        Vector v = new Vector(v1.getX(), v1.getY());
        v.combineWith(v2);
        return v;
    }
    
}
