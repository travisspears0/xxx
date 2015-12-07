package com.ballgame.bonuses;

import com.ballgame.game_objects.PlayerObject;
import com.ballgame.game_objects.PlayersCollisionHandler;
import java.util.concurrent.TimeUnit;

public class BurstBonus implements Bonus {

    private final int secondsDuration;
    private final double newSpeed;
    private PlayerObject playerObject;
    private Thread thread = null;
    
    public BurstBonus(int secondsDuration, double newSpeed) {
        this.secondsDuration = secondsDuration;
        this.newSpeed = newSpeed;
    }
    
    @Override
    public void action() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                double previousSpeed = BurstBonus.this.playerObject.getVector().getSpeed();
                try {
                    playerObject.getVector().setSpeed(BurstBonus.this.newSpeed);
                    TimeUnit.SECONDS.sleep(BurstBonus.this.secondsDuration);
                } catch(InterruptedException e) {
                } finally {
                    if(playerObject != null) {
                        playerObject.getVector().setSpeed(previousSpeed);
                        if(BurstBonus.this.playerObject.getBonus() == BurstBonus.this) {
                            playerObject.setBonus(null);
                        }
                    }
                }
            }
        });
        this.thread.start();
    }

    @Override
    public PlayerObject getPlayerObject() {
        return this.playerObject;
    }

    @Override
    public void setPlayerObject(PlayerObject p) {
        this.playerObject = p;
    }

    @Override
    public Thread getThread() {
        return this.thread;
    }
    
    @Override
    public PlayersCollisionHandler getPlayersCollisionHandler() {
        return null;
    }

    @Override
    public String getTitle() {
        return "burst";
    }
    
}
