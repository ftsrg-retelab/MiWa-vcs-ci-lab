package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		alarmTrigger(speedLimit);

		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

	// NOTE: The exercise did not specify what we should do in case if the things go back to normal
	//       I implemented it so it will turn off the alarm if everything is fine again,
	//       Because this felt more logical.
	private void alarmTrigger(int speedLimit){
		// Alarm state - relative margin
		int refSpeed = controller.getReferenceSpeed();
		if(speedLimit < (refSpeed - refSpeed * 0.5) || speedLimit > (refSpeed + refSpeed * 0.5)){
			user.setAlarmState(true);
		}
		// Alarm state - absolute margin
		else if(speedLimit < 0 || speedLimit > 500){
			user.setAlarmState(true);
		}
		else {
			user.setAlarmState(false);
		}
	}

}
