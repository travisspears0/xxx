package com.ballgame.bonuses;

import com.ballgame.game_objects.PlayerObject;
import com.ballgame.game_objects.PlayersCollisionHandler;

public interface Bonus {
    
    public void action();
    public PlayerObject getPlayerObject();
    public void setPlayerObject(PlayerObject p);
    public Thread getThread();
    public PlayersCollisionHandler getPlayersCollisionHandler();
    public String getTitle();
    
}
