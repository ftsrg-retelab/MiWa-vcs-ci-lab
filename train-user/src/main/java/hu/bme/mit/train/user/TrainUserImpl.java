package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TrainUserImpl implements TrainUser {

    private TrainController controller;
    private int joystickPosition;

    public TrainUserImpl(TrainController controller) {
        this.controller = controller;

        // Set up periodic change of the joystick position
        Timer t = new Timer();
        Random r = new Random();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run(){
                int ran = r.nextInt(10);
                int joysickNewPos = ran - 9;

                overrideJoystickPosition(joysickNewPos);
            }
        };

        // After started, wait 3s then execute timerTask every two seconds
        t.scheduleAtFixedRate(timerTask, 3000, 2000);
    }

    @Override
    public boolean getAlarmFlag() {
        return false;
    }

    @Override
    public int getJoystickPosition() {
        return joystickPosition;
    }

    @Override
    public void overrideJoystickPosition(int joystickPosition) {
        this.joystickPosition = joystickPosition;
        controller.setJoystickPosition(joystickPosition);
        setAcceleration(joystickPosition / 100f);
    }

    private void setAcceleration(float acceleration){
        controller.setAcceleration(acceleration);
    }

}
