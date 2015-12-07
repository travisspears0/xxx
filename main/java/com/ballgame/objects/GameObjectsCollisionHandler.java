package com.ballgame.objects;

import com.ballgame.game_objects.BonusObject;
import com.ballgame.game_objects.GameObject;
import com.ballgame.game_objects.HurtingObject;
import com.ballgame.game_objects.PlayerObject;
import com.ballgame.servers.Game;

public class GameObjectsCollisionHandler {
    
    private static GameObjectsCollisionHandler instance;
    
    private GameObjectsCollisionHandler(){}
    
    public void performCollision(GameObject o1, GameObject o2) {
        if(o1 instanceof PlayerObject && o2 instanceof PlayerObject) {
            this.performPlayersTouch((PlayerObject)o1, (PlayerObject)o2);
            return;
        }
        if(o1 instanceof PlayerObject) {
            if(o2 instanceof HurtingObject) {
                this.hurtPlayer((PlayerObject)o1, (HurtingObject)o2);
            } else {
                this.collectBonus((PlayerObject)o1, (BonusObject)o2);
            }
        } else if(o2 instanceof PlayerObject) {
            if(o1 instanceof HurtingObject) {
                this.hurtPlayer((PlayerObject)o2, (HurtingObject)o1);
            } else {
                this.collectBonus((PlayerObject)o2, (BonusObject)o1);
            }
        }
    }
    
    private void performPlayersTouch(PlayerObject p1, PlayerObject p2) {
        PlayerObject left = (p1.getX()>p2.getX()) ? p2 : p1 ;
        PlayerObject right = (p1.getX()<=p2.getX()) ? p2 : p1 ;
        Vector vLeft = left.getVector();
        Vector vRight = right.getVector();
        
        Vector betweenForRight = new Vector(right.getX()-left.getX(), right.getY()-left.getY());
        Vector betweenForLeft = new Vector(left.getX()-right.getX(), left.getY()-right.getY());
        
        Vector combinedForLeft = Vector.combineVectors(vLeft, betweenForLeft);
        Vector combinedForRight = Vector.combineVectors(vRight, betweenForRight);
        
        Vector resultForLeft = Vector.combineVectors(combinedForLeft, vLeft);
        Vector resultForRight = Vector.combineVectors(combinedForRight, vRight);
        
        double speedToBustLeft = resultForLeft.getSpeed();
        double speedToBustRight = resultForRight.getSpeed();
        
        resultForLeft.setSpeed(vLeft.getSpeed());
        resultForRight.setSpeed(vRight.getSpeed());
        
        left.setVector(resultForLeft);
        right.setVector(resultForRight);
        
        left.setX(left.getX()+resultForLeft.getX());
        left.setY(left.getY()+resultForLeft.getY());
        right.setX(right.getX()+resultForRight.getX());
        right.setY(right.getY()+resultForRight.getY());
        
        left.getSpeedBuster().launch(speedToBustLeft);
        right.getSpeedBuster().launch(speedToBustRight);
    }
    
    private void hurtPlayer(PlayerObject p, HurtingObject h) {
        
    }
    
    private void collectBonus(PlayerObject p, BonusObject b) {
        p.setBonus(b.getBonus());
        b.prepareToRemove();
    }
    
    public static GameObjectsCollisionHandler getInstance() {
        if(GameObjectsCollisionHandler.instance == null) {
            GameObjectsCollisionHandler.instance = new GameObjectsCollisionHandler();
        }
        return GameObjectsCollisionHandler.instance;
    }
    
}
