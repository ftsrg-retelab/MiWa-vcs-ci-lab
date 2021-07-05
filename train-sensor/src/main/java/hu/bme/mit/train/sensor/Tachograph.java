package hu.bme.mit.train.sensor;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Collection;
import java.util.Date;

public class Tachograph {
    private static  String JOYSTICK_COLUMN_NAME = "JoyStick";
    private static  String REFERENCE_SPEED_COLUMN_NAME = "RefSpeed";
    private Table<Date, String, Integer> table = HashBasedTable.create();
    private TrainUser user;
    private TrainController controller;

    public Tachograph(TrainUser user, TrainController controller){
        this.user = user;
        this.controller = controller;
    }

    public void document(){
        Date currentDate = new Date();
        table.put(currentDate, JOYSTICK_COLUMN_NAME, user.getJoystickPosition());
        table.put(currentDate, REFERENCE_SPEED_COLUMN_NAME, controller.getReferenceSpeed());
    }

    public Collection<Integer> getSpeedValues(){
        return table.column(REFERENCE_SPEED_COLUMN_NAME).values();
    }

    public Collection<Integer> getJoystickValues(){
        return table.column(JOYSTICK_COLUMN_NAME).values();
    }
}
