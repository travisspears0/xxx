package com.ballgame.game_objects;

import com.ballgame.bonuses.Bonus;
import org.json.JSONObject;

public class BonusObject extends AbstractObject {
    
    private final Bonus bonus;

    public BonusObject(Bonus bonus, int x, int y) {
        this.bonus = bonus;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public JSONObject getChangedStatus() {
        JSONObject ob = new JSONObject();
        return ob;
    }
    
    @Override
    public JSONObject getFullStatus() {
        JSONObject ob = new JSONObject();
        
        return ob;
    }
    
    @Override
    public void update() {
        
    }
    
    
}
