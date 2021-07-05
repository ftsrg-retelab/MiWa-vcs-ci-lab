package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
//import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainController controller;
    TrainUser user;
    Tachograph tachograph;

    @Before
    public void before() {
        TrainSystem ts = new TrainSystem();
        controller = ts.getController();
        user = ts.getUser();
        tachograph = new Tachograph(user, controller);
    }

    @Test
    public void TachographTest() {
        controller.setSpeedLimit(10);

        user.overrideJoystickPosition(3);
        controller.followSpeed();
        tachograph.document();
        user.overrideJoystickPosition(4);
        controller.followSpeed();
        tachograph.document();

        List<Integer> expectedSpeed = new ArrayList<>();
        List<Integer> expectedJoystickPos = new ArrayList<>();

        expectedSpeed.add(3);
        expectedSpeed.add(7);

        expectedJoystickPos.add(3);
        expectedJoystickPos.add(4);

        Assert.assertArrayEquals(expectedSpeed.toArray(), tachograph.getSpeedValues().toArray());
        Assert.assertArrayEquals(expectedJoystickPos.toArray(), tachograph.getJoystickValues().toArray());

    }
}
