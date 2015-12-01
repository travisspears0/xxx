package com.ballgame.objects;

import com.ballgame.bonuses.Bonus;
import com.ballgame.game_objects.GameObject;
import com.ballgame.game_objects.HurtingObject;
import com.ballgame.game_objects.PlayerObject;

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
                this.collectBonus((PlayerObject)o1, (Bonus)o2);
            }
        } else if(o2 instanceof PlayerObject) {
            if(o1 instanceof HurtingObject) {
                this.hurtPlayer((PlayerObject)o2, (HurtingObject)o1);
            } else {
                this.collectBonus((PlayerObject)o2, (Bonus)o1);
            }
        }
    }
    
    private void performPlayersTouch(PlayerObject p1, PlayerObject p2) {
        System.out.println("aaaaaaaa");
    }
    
    private void hurtPlayer(PlayerObject p, HurtingObject h) {
        
    }
    
    private void collectBonus(PlayerObject p, Bonus b) {
        
    }
    
    public static GameObjectsCollisionHandler getInstance() {
        if(GameObjectsCollisionHandler.instance == null) {
            GameObjectsCollisionHandler.instance = new GameObjectsCollisionHandler();
        }
        return GameObjectsCollisionHandler.instance;
    }
    
}
