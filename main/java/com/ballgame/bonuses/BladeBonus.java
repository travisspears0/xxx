package com.ballgame.bonuses;

import com.ballgame.game_objects.PlayerObject;
import com.ballgame.game_objects.PlayersCollisionHandler;

public class BladeBonus implements Bonus {

    private final int value;
    private PlayerObject playerObject;
    private Thread thread = null;

    public BladeBonus(int value) {
        this.value = value;
    }
    
    @Override
    public void action() {
        
        this.playerObject.setBonus(null);
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
        return "blade";
    }
}
