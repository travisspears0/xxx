package com.ballgame.game_objects;

import com.ballgame.game.GameManager;
import com.ballgame.servers.Game;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;
import com.ballgame.bonuses.Bonus;
import com.ballgame.game.SpeedBuster;
import com.ballgame.objects.Vector;

public class PlayerObject extends AbstractObject {

    private static final Random random = new Random();
    //private static final int MAX_SPEED = 10;
    /**
     * degrees of direction, value between 0 and Math.PI, where:
     *      0                   up
     *      Math.PI*.25         right
     *      Math.PI*.5          down
     *      Math.PI*.75         left
     */
    //private double angle;
    /**
     * value between 1 and 10
     */
    //private int speed = 5;
    private Vector vector;
    private boolean ghost = true;
    private final int userId;
    private final Map<String, String> cacheMap = new HashMap<>();
    
    private int shield = 0;
    private int hp = 10;
    private Bonus bonus = null;
    private final SpeedBuster speedBuster = new SpeedBuster(this);
    //private PlayersCollisionHandler playersCollisionHandler = null;
    
    public PlayerObject(int userId) {
        /*this.angle=random.nextDouble()*10;
        this.angle = (int)this.angle;
        this.angle = this.angle/10*Math.PI*2;*/
        this.vector = new Vector(random.nextDouble()*4, 5);
        this.userId = userId;
        this.color = Game.getGameManager().getFristFreeColor();
    }
/*
    public synchronized double getAngle() {
        return angle;
    }

    public synchronized void setAngle(double angle) {
        this.angle = angle;
        this.angle %= Math.PI*2;
    }
    */

    public synchronized Vector getVector() {
        return vector;
    }

    public synchronized void setVector(Vector vector) {
        this.vector = vector;
    }
    
    @Override
    public void update() {
        this.setX(this.getX()+this.getVector().getX());//this.getSpeed()*Math.sin(this.getVector().getAngle()));
        this.setY(this.getY()+this.getVector().getY());//this.getSpeed()*Math.cos(this.getVector().getAngle()));
        this.checkRicochet();
        this.changed = true;
    }
    
    private void checkRicochet() {
        if( this.getX()+this.getSize() >= GameManager.BOARD_WIDTH ) {
            this.getVector().setAngle(2*Math.PI-this.getVector().getAngle());
            this.setX(GameManager.BOARD_WIDTH-this.getSize());
        } else if( this.getX()-this.getSize() <=0 ) {
            this.getVector().setAngle(2*Math.PI-this.getVector().getAngle());
            this.setX(this.getSize());
        }
        if( this.getY()+this.getSize() >= GameManager.BOARD_HEIGHT ) {
            this.getVector().setAngle(Math.PI-this.getVector().getAngle());
            this.setY(GameManager.BOARD_HEIGHT-this.getSize());
        } else if( this.getY()-this.getSize() <= 0 ) {
            this.getVector().setAngle(Math.PI-this.getVector().getAngle());
            this.setY(this.getSize());
        }
    }
    /*
    public synchronized double getSpeed() {
        return this.getVector().getSpeed();
    }

    public synchronized void setSpeed(double speed) {
        this.getVector().setSpeed(speed);
    }
    */
    public synchronized boolean isGhost() {
        return ghost;
    }

    public synchronized void setGhost(boolean ghost) {
        this.ghost = ghost;
    }
    
    @Override
    public JSONObject getChangedStatus() {
        JSONObject ob = new JSONObject();
        ob.put("id", this.userId);
        if( !this.color.equals(this.cacheMap.get("color")) ) {
            this.cacheMap.put("color", this.getColor());
            ob.put("color", this.getColor());
        }
        if( !Double.toString(this.getX()).equals(this.cacheMap.get("x")) ) {
            this.cacheMap.put("x", this.getX()+"");
            ob.put("x", this.getX());
        }
        if( !Double.toString(this.getY()).equals(this.cacheMap.get("y")) ) {
            this.cacheMap.put("y", this.getY()+"");
            ob.put("y", this.getY());
        }
        if( !Double.toString(this.getVector().getAngle()).equals(this.cacheMap.get("angle")) ) {
            this.cacheMap.put("angle", this.getVector().getAngle()+"");
            ob.put("angle", this.getVector().getAngle());
        }
        if( !Integer.toString(this.getSize()).equals(this.cacheMap.get("size")) ) {
            this.cacheMap.put("size", this.getSize()+"");
            ob.put("size", this.getSize());
        }
        /*
        if( !Double.toString(this.getVector().getX()).equals(this.cacheMap.get("vectorX")) ) {
            this.cacheMap.put("vectorX", this.getVector().getX()+"");
            ob.put("vectorX", this.getVector().getX());
        }
        if( !Double.toString(this.getVector().getY()).equals(this.cacheMap.get("vectorY")) ) {
            this.cacheMap.put("vectorY", this.getVector().getY()+"");
            ob.put("vectorY", this.getVector().getY());
        }
        */
        
        if( !Integer.toString(this.getShield()).equals(this.cacheMap.get("shield")) ) {
            this.cacheMap.put("shield", this.getShield()+"");
            ob.put("shield", this.getShield());
        }
        if( !Integer.toString(this.getHp()).equals(this.cacheMap.get("hp")) ) {
            this.cacheMap.put("hp", this.getHp()+"");
            ob.put("hp", this.getHp());
        }
        if( this.getBonus()!=null && !this.getBonus().getTitle().equals(this.cacheMap.get("bonus")) ) {
            this.cacheMap.put("bonus", this.getBonus().getTitle());
            ob.put("bonus", this.getBonus().getTitle());
        }
        return ob;
    }
    
    @Override
    public JSONObject getFullStatus() {
        JSONObject ob = new JSONObject();
        ob.put("id", this.userId);
        ob.put("color", this.getColor());
        ob.put("x", this.getX());
        ob.put("y", this.getY());
        ob.put("angle", this.getVector().getAngle());
        ob.put("size", this.getSize());
        
        ob.put("shield", this.getShield());
        ob.put("hp", this.getHp());
        ob.put("bonus", this.getBonus());
        /*ob.put("vectorX", this.getVector().getX());
        ob.put("vectorY", this.getVector().getY());*/
        return ob;
    }
    
    public synchronized void increaseAngle() {
        this.getVector().setAngle(this.getVector().getAngle()-.1);
    }
    
    public synchronized void decreaseAngle() {
        this.getVector().setAngle(this.getVector().getAngle()+.1);
    }
    
    public synchronized void increaseSize() {
        this.setSize(this.getSize()+1);
    }
    
    public synchronized void increaseSpeed() {
        this.getVector().setSpeed(this.getVector().getSpeed()+1);
    }
    
    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public synchronized int getShield() {
        return shield;
    }

    public synchronized void setShield(int shield) {
        this.shield = shield;
    }

    public synchronized int getHp() {
        return hp;
    }

    public synchronized void setHp(int hp) {
        this.hp = hp;
    }
/*
    public synchronized PlayersCollisionHandler getPlayersCollisionHandler() {
        return playersCollisionHandler;
    }

    public synchronized void setPlayersCollisionHandler(PlayersCollisionHandler p) {
        this.playersCollisionHandler = p;
    }
*/

    public synchronized SpeedBuster getSpeedBuster() {
        return speedBuster;
    }
}
