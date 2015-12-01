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
        }
    }
    
    private void checkCollision(GameObject o1, GameObject o2) {
        double distance = Math.sqrt( ((o1.getX()-o2.getX())*((o1.getX()-o2.getX()))) + 
                ((o1.getY()-o2.getY())*((o1.getY()-o2.getY()))));
        if(distance <= o1.getSize()+o2.getSize()) {
            this.gameObjectsCollisionHandler.performCollision(o1, o2);
        }
    }
    
    public static GameLoop getInstance() {
        if(GameLoop.gameLoop == null) {
            GameLoop.gameLoop = new GameLoop();
        }
        return GameLoop.gameLoop;
    }
    
}
