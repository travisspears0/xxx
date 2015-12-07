package com.ballgame.game_objects;

import com.ballgame.bonuses.Bonus;
import com.ballgame.game.BonusGenerator;
import com.ballgame.servers.Game;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class BonusObject extends AbstractObject {
    
    public static int BONUS_SIZE = 20;
    private final Bonus bonus;
    private static int CURRENT_ID = 0;
    private final String id = "bonus_" + (++CURRENT_ID);
    BonusGenerator generator;

    public BonusObject(Bonus bonus, double x, double y, BonusGenerator generator) {
        this.bonus = bonus;
        this.x = x;
        this.y = y;
        this.size = BONUS_SIZE;
        this.changed = true;
        this.generator = generator;
    }
    
    @Override
    public JSONObject getChangedStatus() {
        return this.getFullStatus();
    }
    
    @Override
    public JSONObject getFullStatus() {
        JSONObject ob = new JSONObject();
        if(this.isToBeRemoved()) {
            ob.put("id",this.id);
            ob.put("remove", "remove");
            Game.getGameManager().removeGameObject(this);
            this.generator.decreaseBonusCounter();
            this.generator.tryFreeBonusSpot();
            return ob;
        }
        ob.put("id",this.id);
        ob.put("x",this.x);
        ob.put("y",this.y);
        ob.put("size",this.size);
        ob.put("color","#990099");
        ob.put("info",this.bonus.getTitle());
        return ob;
    }
    
    @Override
    public void update() {
        
    }

    public Bonus getBonus() {
        return bonus;
    }
    
}
