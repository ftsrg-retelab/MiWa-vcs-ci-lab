package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
    private static final int REF_SPEED = 50;

    @Mock
    TrainController trainControllerMock;
    TrainUser trainUserMock;
    TrainSensor trainSensorImpl;

    @Before
    public void before() {
        trainControllerMock = mock(TrainController.class);
        when(trainControllerMock.getReferenceSpeed()).thenReturn(REF_SPEED);
        trainUserMock = mock(TrainUser.class);
        trainSensorImpl = new TrainSensorImpl(trainControllerMock, trainUserMock);
    }

    @Test
    public void itIsFine(){ // Case 1
        trainSensorImpl.overrideSpeedLimit(65);
        verify(trainUserMock, times(1)).setAlarmState(false);
        verify(trainUserMock, times(0)).setAlarmState(true);
    }

    @Test
    public void tooLow_Relative(){ // Case 3
        when(trainControllerMock.getReferenceSpeed()).thenReturn(150);
        trainSensorImpl.overrideSpeedLimit(50);
        verify(trainUserMock, times(0)).setAlarmState(false);
        verify(trainUserMock, times(1)).setAlarmState(true);
    }

    @Test
    public void backAndForth_Relative(){ // Case 4
        trainSensorImpl.overrideSpeedLimit(100);
        verify(trainUserMock, times(0)).setAlarmState(false);
        verify(trainUserMock, times(1)).setAlarmState(true);
        trainSensorImpl.overrideSpeedLimit(45);
        verify(trainUserMock, times(1)).setAlarmState(false);
        verify(trainUserMock, times(1)).setAlarmState(true);
    }

    @Test
    public void tooLow_Absolute(){ // Case 5
        when(trainControllerMock.getReferenceSpeed()).thenReturn(10);
        trainSensorImpl.overrideSpeedLimit(-1);
        verify(trainUserMock, times(0)).setAlarmState(false);
        verify(trainUserMock, times(1)).setAlarmState(true);
    }

    @Test
    public void tooHigh_Absolute(){ // Case 6
        when(trainControllerMock.getReferenceSpeed()).thenReturn(500);
        trainSensorImpl.overrideSpeedLimit(555);
        verify(trainUserMock, times(0)).setAlarmState(false);
        verify(trainUserMock, times(1)).setAlarmState(true);
    }
}
