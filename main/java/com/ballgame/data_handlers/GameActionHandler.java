package com.ballgame.data_handlers;

import com.ballgame.objects.User;

public class GameActionHandler {
    
    public void handle(String actionName, User caller) {
        switch(actionName) {
            case "left": {
                caller.getPlayerObject().decreaseAngle();
                break;
            }
            case "right": {
                caller.getPlayerObject().increaseAngle();
                break;
            }
            case "action": {
                if(caller.getPlayerObject().getBonus() != null) {
                    caller.getPlayerObject().getBonus().action();
                } else {
                    caller.getPlayerObject().increaseSpeed();
                }
                break;
            }
            default: {
                System.out.println("wrong action ["+ actionName +"]");
            }
        }
    }
    
}
