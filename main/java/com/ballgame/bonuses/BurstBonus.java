package com.ballgame.bonuses;

import com.ballgame.game_objects.PlayerObject;
import com.ballgame.game_objects.PlayersCollisionHandler;
import java.util.concurrent.TimeUnit;

public class BurstBonus implements Bonus {

    private final int secondsDuration;
    private final int value;
    private PlayerObject playerObject;
    private Thread thread = null;
    
    public BurstBonus(int secondsDuration, int value) {
        this.secondsDuration = secondsDuration;
        this.value = value;
        
    }
    
    @Override
    public void action() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    playerObject.setSpeed(playerObject.getSpeed()+value);
                    TimeUnit.SECONDS.sleep(BurstBonus.this.secondsDuration);
                } catch(InterruptedException e) {
                } finally {
                    if(playerObject != null) {
                        playerObject.setSpeed(playerObject.getSpeed()-value);
                        playerObject.setBonus(null);
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
