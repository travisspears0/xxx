package com.ballgame.game;

import com.ballgame.bonuses.Bonus;
import com.ballgame.game_objects.GameObject;
import com.ballgame.game_objects.HurtingObject;
import com.ballgame.game_objects.PlayerObject;
import com.ballgame.servers.Game;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.ballgame.objects.GameObjectsCollisionHandler;

public class GameLoop implements Runnable {
    
    private static GameLoop gameLoop;
    private final GameObjectsCollisionHandler gameObjectsCollisionHandler;
    
    private GameLoop() {
        this.gameObjectsCollisionHandler = GameObjectsCollisionHandler.getInstance();
    }
    
    @Override
    public void run() {
        Random r = new Random();
        try {
            while( !Thread.interrupted() ) {
                for( int i=0 ; i<Game.getGameManager().getGameObjects().size() ; ++i ) {
                    GameObject ob = Game.getGameManager().getGameObjects().get(i);
                    ob.update();
                    for( int j=i-1 ; j>=0 ; --j ) {
                        this.checkCollision(ob, Game.getGameManager().getGameObjects().get(j));
                    }
                }
                Game.getGameManager().notifyUsers();
                TimeUnit.MILLISECONDS.sleep(30);
            }
        } catch(InterruptedException e) {
            System.out.println("game ended");
        } /*finally {
            this.clear();
        }*/
    }
    
    private void checkCollision(GameObject o1, GameObject o2) {
        double distance = Math.sqrt( ((o1.getX()-o2.getX())*((o1.getX()-o2.getX()))) + 
                ((o1.getY()-o2.getY())*((o1.getY()-o2.getY()))));
        if(distance <= o1.getSize()+o2.getSize()) {
            this.gameObjectsCollisionHandler.performCollision(o1, o2);
        }
    }
    
    /*
    private void checkCollision(GameObject o1, GameObject o2) {
        double distance = Math.sqrt( ((o1.getX()-o2.getX())*((o1.getX()-o2.getX()))) + 
                ((o1.getY()-o2.getY())*((o1.getY()-o2.getY()))));
        if(distance <= o1.getSize()+o2.getSize()) {
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
            }else if(o2 instanceof PlayerObject) {
                if(o1 instanceof HurtingObject) {
                    this.hurtPlayer((PlayerObject)o2, (HurtingObject)o1);
                } else {
                    this.collectBonus((PlayerObject)o2, (Bonus)o1);
                }
            }
        }
    }
    
    private void performPlayersTouch(PlayerObject p1, PlayerObject p2) {
        System.out.println(p1 +" touches "+ p2);
    }
    
    private void hurtPlayer(PlayerObject player, HurtingObject hurtingObject) {
        //...
    }
    
    private void collectBonus(PlayerObject collector, Bonus bonus) {
        if(collector.getBonus() != null && collector.getBonus().getThread() != null) {
            collector.getBonus().getThread().interrupt();
        }
        collector.setBonus(bonus);
    }
    
    private void clear() {
        
    }
    */
    public static GameLoop getInstance() {
        if(GameLoop.gameLoop == null) {
            GameLoop.gameLoop = new GameLoop();
        }
        return GameLoop.gameLoop;
    }
    
}
