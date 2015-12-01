package com.ballgame.game_objects;

public abstract class PlayersCollisionHandler {
    
    private final PlayerObject playerObject;

    public PlayersCollisionHandler(PlayerObject playerObject) {
        this.playerObject = playerObject;
    }
    
    public abstract void action();
    
}
