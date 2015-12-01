package com.ballgame.bonuses;

import com.ballgame.game_objects.PlayerObject;
import com.ballgame.game_objects.PlayersCollisionHandler;

public class ShieldBonus implements Bonus {

    private PlayerObject playerObject=null;
    private final int value;

    public ShieldBonus(int value) {
        this.value = value;
    }
    
    @Override
    public void action() {
        if(this.playerObject != null) {
            this.playerObject.setShield(this.playerObject.getShield()+this.value);
        }
        this.playerObject.setBonus(null);
    }

    @Override
    public PlayerObject getPlayerObject() {
        return this.playerObject;
    }

    @Override
    public void setPlayerObject(PlayerObject playerObject) {
        this.playerObject = playerObject;
    }

    @Override
    public Thread getThread() {
        return null;
    }
    
    @Override
    public PlayersCollisionHandler getPlayersCollisionHandler() {
        return null;
    }
    
    @Override
    public String getTitle() {
        return "shield";
    }
}
