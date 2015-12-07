package com.ballgame.game_objects;

import com.ballgame.bonuses.BladeBonus;
import com.ballgame.bonuses.Bonus;
import com.ballgame.bonuses.BurstBonus;
import com.ballgame.bonuses.ShieldBonus;
import com.ballgame.game.BonusGenerator;
import java.util.Random;

public class ObjectsFactory {
    
    private static final int COUNT_BONUSES = 3;
    private static final Random random = new Random();
    
    public HurtingObject getHurtingObject(int x, int y) {
        HurtingObject h = new HurtingObject();
        h.setX(x);
        h.setY(y);
        return h;
    }
    
    public BonusObject getBonusObject(double x, double y, BonusGenerator generator) {
        Bonus randomBonus = null;
        switch(random.nextInt(COUNT_BONUSES)) {
            case 0: {
                randomBonus = new BladeBonus(random.nextInt(10)+1);
                break;
            }
            case 1: {
                randomBonus = new BurstBonus(random.nextInt(3)+3, random.nextInt(4)+3);
                break;
            }
            case 2: {
                randomBonus = new ShieldBonus(random.nextInt(3)+3);
                break;
            }
            default: {
                throw new IndexOutOfBoundsException("there are not so many bonus types");
            }
        }
        return new BonusObject(randomBonus, x, y, generator);
    }
    
}
