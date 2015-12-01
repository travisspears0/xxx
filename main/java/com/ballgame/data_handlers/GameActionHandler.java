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
                caller.getPlayerObject().getBonus().action();
                break;
            }
            default: {
                System.out.println("wrong action ["+ actionName +"]");
            }
        }
    }
    
}
