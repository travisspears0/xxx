package com.ballgame.game;

import com.ballgame.game_objects.PlayerObject;
import java.util.concurrent.TimeUnit;

public class SpeedBuster {

    private final PlayerObject playerObject;
    private Thread thread = null;
    
    public SpeedBuster(PlayerObject playerObject) {
        this.playerObject = playerObject;
    }
    
    public Thread getThread() {
        return this.thread;
    }
    
    public void launch(double newSpeed) {
        this.launch(newSpeed, 2000);
    }
    
    public void launch(double _newSpeed, long _milliseconds) {
        if(this.thread != null) {
            this.thread.interrupt();
        }
        final double newSpeed = _newSpeed;
        final long milliseconds = _milliseconds;
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                double oldSpeed = SpeedBuster.this.playerObject.getVector().getSpeed();
                SpeedBuster.this.playerObject.getVector().setSpeed(newSpeed);
                double currentSpeed = SpeedBuster.this.playerObject.getVector().getSpeed();
                long divider = 10;
                double step = (currentSpeed-oldSpeed)/divider;
                long interval = milliseconds/divider;
                try {
                    for(int i=0 ; i<divider ; ++i) {
                        SpeedBuster.this.playerObject.getVector().setSpeed(
                                SpeedBuster.this.playerObject.getVector().getSpeed()-step);
                        TimeUnit.MILLISECONDS.sleep(interval);
                    }
                } catch(InterruptedException e) {
                    
                } finally {
                    SpeedBuster.this.playerObject.getVector().setSpeed(oldSpeed);
                }
            }
        });
        this.thread.start();
        this.thread = null;
    }
    
}
