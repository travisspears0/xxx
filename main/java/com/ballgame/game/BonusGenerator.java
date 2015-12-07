package com.ballgame.game;

import com.ballgame.game_objects.BonusObject;
import com.ballgame.game_objects.GameObject;
import com.ballgame.game_objects.ObjectsFactory;
import com.ballgame.servers.Game;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BonusGenerator implements Runnable {

    private static final int MAX_BONUSES = 5;
    private int countBonuses = 0;
    private static final Random random = new Random();
    private final ObjectsFactory factory = new ObjectsFactory();
    
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                this.waitForBonusSpot();
                double x = random.nextInt(Game.getGameManager().BOARD_WIDTH);
                double y = random.nextInt(Game.getGameManager().BOARD_HEIGHT);
                if(this.areCoordsFree(x, y)) {
                    BonusObject newBonus = factory.getBonusObject(x, y, this);
                    Game.getGameManager().addGameObject(newBonus);
                }
                ++countBonuses;
                TimeUnit.SECONDS.sleep(random.nextInt(3)+2);
            }
        } catch(InterruptedException e) {
            Game.getGameManager().clearGameObjects();
        }
    }
    
    public synchronized void waitForBonusSpot() throws InterruptedException {
        while(countBonuses >= MAX_BONUSES) {
            this.wait();
        }
    }
    
    public synchronized void tryFreeBonusSpot() {
        this.notifyAll();
    }
    
    private boolean areCoordsFree(double x, double y) {
        for(GameObject ob : Game.getGameManager().getGameObjects()) {
            double l = (ob.getX()-x)*(ob.getX()-x)+(ob.getY()-y)*(ob.getY()-y);
            if(l<=ob.getSize()+BonusObject.BONUS_SIZE) {
                return false;
            }
        }
        return true;
    }
    
    public synchronized void decreaseBonusCounter() {
        --this.countBonuses;
    }
    
    public synchronized void clearBonusCounter() {
        this.countBonuses = 0;
    }
    
}
