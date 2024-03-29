/** Generated by itemis CREATE code generator. */
package smarthub.java;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TV_Unit implements ITimed, IEventDriven {
	public static class Device {
		private TV_Unit parent;
		
		public Device(TV_Unit parent) {
			this.parent = parent;
		}
		private boolean on;
		
		
		public void raiseOn() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					on = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean off;
		
		
		public void raiseOff() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					off = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean isOn;
		
		public synchronized boolean getIsOn() {
			synchronized(parent) {
				return isOn;
			}
		}
		
		public void setIsOn(boolean value) {
			synchronized(parent) {
				this.isOn = value;
			}
		}
		
	}
	
	public static class Input {
		private TV_Unit parent;
		
		public Input(TV_Unit parent) {
			this.parent = parent;
		}
		private boolean toggle;
		
		
		public void raiseToggle() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					toggle = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean satellite;
		
		
		public void raiseSatellite() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					satellite = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean cable;
		
		
		public void raiseCable() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					cable = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean hdmi;
		
		
		public void raiseHdmi() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					hdmi = true;
				});
				parent.runCycle();
			}
		}
		
		private String source;
		
		public synchronized String getSource() {
			synchronized(parent) {
				return source;
			}
		}
		
		public void setSource(String value) {
			synchronized(parent) {
				this.source = value;
			}
		}
		
	}
	
	protected Device device;
	
	protected Input input;
	
	public enum State {
		_TV_UNIT___TV_ON_,
		_TV_UNIT___TV_ON__TV_WORKING_SATELLITETV,
		_TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL,
		_TV_UNIT___TV_ON__TV_WORKING_CABLE,
		_TV_UNIT___TV_ON__TV_WORKING_HDMI,
		_TV_UNIT___TV_ONSLEEP_,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[1];
	
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		synchronized(TV_Unit.this) {
			return isExecuting;
		}
	}
	
	protected void setIsExecuting(boolean value) {
		synchronized(TV_Unit.this) {
			this.isExecuting = value;
		}
	}
	public TV_Unit() {
		device = new Device(this);
		input = new Input(this);
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		setChannel(0l);
		
		device.setIsOn(false);
		
		input.setSource("");
		
		isExecuting = false;
	}
	
	public synchronized void enter() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		enterSequence__TV_Unit__default();
		isExecuting = false;
	}
	
	public synchronized void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence__TV_Unit_();
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	private void clearInEvents() {
		change = false;
		device.on = false;
		device.off = false;
		input.toggle = false;
		input.satellite = false;
		input.cable = false;
		input.hdmi = false;
		timeEvents[0] = false;
	}
	
	private void microStep() {
		switch (stateVector[0]) {
		case _TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL:
			_TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel_react(-1l);
			break;
		case _TV_UNIT___TV_ON__TV_WORKING_CABLE:
			_TV_Unit___TV_On__TV_Working_cable_react(-1l);
			break;
		case _TV_UNIT___TV_ON__TV_WORKING_HDMI:
			_TV_Unit___TV_On__TV_Working_hdmi_react(-1l);
			break;
		case _TV_UNIT___TV_ONSLEEP_:
			_TV_Unit___TV_onSleep__react(-1l);
			break;
		default:
			break;
		}
	}
	
	private void runCycle() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		nextEvent();
		do { 
			microStep();
			
			clearInEvents();
		} while (nextEvent());
		
		isExecuting = false;
	}
	
	protected boolean nextEvent() {
		if(!inEventQueue.isEmpty()) {
			inEventQueue.poll().run();
			return true;
		}
		return false;
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case _TV_UNIT___TV_ON_:
			return stateVector[0].ordinal() >= State.
					_TV_UNIT___TV_ON_.ordinal()&& stateVector[0].ordinal() <= State._TV_UNIT___TV_ON__TV_WORKING_HDMI.ordinal();
		case _TV_UNIT___TV_ON__TV_WORKING_SATELLITETV:
			return stateVector[0].ordinal() >= State.
					_TV_UNIT___TV_ON__TV_WORKING_SATELLITETV.ordinal()&& stateVector[0].ordinal() <= State._TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL.ordinal();
		case _TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL:
			return stateVector[0] == State._TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL;
		case _TV_UNIT___TV_ON__TV_WORKING_CABLE:
			return stateVector[0] == State._TV_UNIT___TV_ON__TV_WORKING_CABLE;
		case _TV_UNIT___TV_ON__TV_WORKING_HDMI:
			return stateVector[0] == State._TV_UNIT___TV_ON__TV_WORKING_HDMI;
		case _TV_UNIT___TV_ONSLEEP_:
			return stateVector[0] == State._TV_UNIT___TV_ONSLEEP_;
		default:
			return false;
		}
	}
	
	public synchronized void setTimerService(ITimerService timerService) {
		this.timerService = timerService;
	}
	
	public ITimerService getTimerService() {
		return timerService;
	}
	
	public synchronized void raiseTimeEvent(int eventID) {
		inEventQueue.add(() -> {
			timeEvents[eventID] = true;
		});
		runCycle();
	}
	
	public Device device() {
		return device;
	}
	
	public Input input() {
		return input;
	}
	
	
	private boolean change;
	
	
	public void raiseChange() {
		synchronized(TV_Unit.this) {
			inEventQueue.add(() -> {
				change = true;
			});
			runCycle();
		}
	}
	
	private long channel;
	
	public synchronized long getChannel() {
		synchronized(TV_Unit.this) {
			return channel;
		}
	}
	
	public void setChannel(long value) {
		synchronized(TV_Unit.this) {
			this.channel = value;
		}
	}
	
	/* Entry action for state '<TV_On>'. */
	private void entryAction__TV_Unit___TV_On_() {
		timerService.setTimer(this, 0, 500l, true);
		
		device.setIsOn(true);
	}
	
	/* Entry action for state 'satelliteTV'. */
	private void entryAction__TV_Unit___TV_On__TV_Working_satelliteTV() {
		input.setSource("Satellite TV");
	}
	
	/* Entry action for state 'cable'. */
	private void entryAction__TV_Unit___TV_On__TV_Working_cable() {
		input.setSource("Cable TV");
	}
	
	/* Entry action for state 'hdmi'. */
	private void entryAction__TV_Unit___TV_On__TV_Working_hdmi() {
		input.setSource("HDMI");
	}
	
	/* Entry action for state '<TV_onSleep>'. */
	private void entryAction__TV_Unit___TV_onSleep_() {
		device.setIsOn(false);
	}
	
	/* Exit action for state '<TV_On>'. */
	private void exitAction__TV_Unit___TV_On_() {
		timerService.unsetTimer(this, 0);
	}
	
	/* 'default' enter sequence for state <TV_On> */
	private void enterSequence__TV_Unit___TV_On__default() {
		entryAction__TV_Unit___TV_On_();
		enterSequence__TV_Unit___TV_On__TV_Working_default();
	}
	
	/* 'default' enter sequence for state satelliteTV */
	private void enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_default() {
		entryAction__TV_Unit___TV_On__TV_Working_satelliteTV();
		enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_default();
	}
	
	/* 'default' enter sequence for state changeChannel */
	private void enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel_default() {
		stateVector[0] = State._TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL;
	}
	
	/* 'default' enter sequence for state cable */
	private void enterSequence__TV_Unit___TV_On__TV_Working_cable_default() {
		entryAction__TV_Unit___TV_On__TV_Working_cable();
		stateVector[0] = State._TV_UNIT___TV_ON__TV_WORKING_CABLE;
	}
	
	/* 'default' enter sequence for state hdmi */
	private void enterSequence__TV_Unit___TV_On__TV_Working_hdmi_default() {
		entryAction__TV_Unit___TV_On__TV_Working_hdmi();
		stateVector[0] = State._TV_UNIT___TV_ON__TV_WORKING_HDMI;
	}
	
	/* 'default' enter sequence for state <TV_onSleep> */
	private void enterSequence__TV_Unit___TV_onSleep__default() {
		entryAction__TV_Unit___TV_onSleep_();
		stateVector[0] = State._TV_UNIT___TV_ONSLEEP_;
	}
	
	/* 'default' enter sequence for region <TV_Unit> */
	private void enterSequence__TV_Unit__default() {
		react__TV_Unit___entry_Default();
	}
	
	/* 'default' enter sequence for region TV_Working */
	private void enterSequence__TV_Unit___TV_On__TV_Working_default() {
		react__TV_Unit___TV_On__TV_Working__entry_Default();
	}
	
	/* 'default' enter sequence for region changeChannels */
	private void enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_default() {
		react__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels__entry_Default();
	}
	
	/* Default exit sequence for state <TV_On> */
	private void exitSequence__TV_Unit___TV_On_() {
		exitSequence__TV_Unit___TV_On__TV_Working();
		exitAction__TV_Unit___TV_On_();
	}
	
	/* Default exit sequence for state satelliteTV */
	private void exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV() {
		exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels();
	}
	
	/* Default exit sequence for state changeChannel */
	private void exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel() {
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state cable */
	private void exitSequence__TV_Unit___TV_On__TV_Working_cable() {
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state hdmi */
	private void exitSequence__TV_Unit___TV_On__TV_Working_hdmi() {
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state <TV_onSleep> */
	private void exitSequence__TV_Unit___TV_onSleep_() {
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for region <TV_Unit> */
	private void exitSequence__TV_Unit_() {
		switch (stateVector[0]) {
		case _TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL:
			exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel();
			exitAction__TV_Unit___TV_On_();
			break;
		case _TV_UNIT___TV_ON__TV_WORKING_CABLE:
			exitSequence__TV_Unit___TV_On__TV_Working_cable();
			exitAction__TV_Unit___TV_On_();
			break;
		case _TV_UNIT___TV_ON__TV_WORKING_HDMI:
			exitSequence__TV_Unit___TV_On__TV_Working_hdmi();
			exitAction__TV_Unit___TV_On_();
			break;
		case _TV_UNIT___TV_ONSLEEP_:
			exitSequence__TV_Unit___TV_onSleep_();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region TV_Working */
	private void exitSequence__TV_Unit___TV_On__TV_Working() {
		switch (stateVector[0]) {
		case _TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL:
			exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel();
			break;
		case _TV_UNIT___TV_ON__TV_WORKING_CABLE:
			exitSequence__TV_Unit___TV_On__TV_Working_cable();
			break;
		case _TV_UNIT___TV_ON__TV_WORKING_HDMI:
			exitSequence__TV_Unit___TV_On__TV_Working_hdmi();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region changeChannels */
	private void exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels() {
		switch (stateVector[0]) {
		case _TV_UNIT___TV_ON__TV_WORKING_SATELLITETV_CHANGECHANNELS_CHANGECHANNEL:
			exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react__TV_Unit___entry_Default() {
		enterSequence__TV_Unit___TV_onSleep__default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__TV_Unit___TV_On__TV_Working__entry_Default() {
		enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels__entry_Default() {
		enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel_default();
	}
	
	private long react(long transitioned_before) {
		return transitioned_before;
	}
	
	private long _TV_Unit___TV_On__react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (((timeEvents[0]) && (!device.getIsOn()))) {
				exitSequence__TV_Unit___TV_On_();
				timeEvents[0] = false;
				enterSequence__TV_Unit___TV_onSleep__default();
				react(0l);
				
				transitioned_after = 0l;
			} else {
				if (device.off) {
					exitSequence__TV_Unit___TV_On_();
					enterSequence__TV_Unit___TV_onSleep__default();
					react(0l);
					
					transitioned_after = 0l;
				}
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _TV_Unit___TV_On__TV_Working_satelliteTV_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (input.cable) {
				exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV();
				enterSequence__TV_Unit___TV_On__TV_Working_cable_default();
				_TV_Unit___TV_On__react(0l);
				
				transitioned_after = 0l;
			} else {
				if (input.hdmi) {
					exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV();
					enterSequence__TV_Unit___TV_On__TV_Working_hdmi_default();
					_TV_Unit___TV_On__react(0l);
					
					transitioned_after = 0l;
				}
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _TV_Unit___TV_On__react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (change) {
				exitSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel();
				channel++;
				
				enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_changeChannels_changeChannel_default();
				_TV_Unit___TV_On__TV_Working_satelliteTV_react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _TV_Unit___TV_On__TV_Working_satelliteTV_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _TV_Unit___TV_On__TV_Working_cable_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (input.hdmi) {
				exitSequence__TV_Unit___TV_On__TV_Working_cable();
				enterSequence__TV_Unit___TV_On__TV_Working_hdmi_default();
				_TV_Unit___TV_On__react(0l);
				
				transitioned_after = 0l;
			} else {
				if ((input.satellite || input.toggle)) {
					exitSequence__TV_Unit___TV_On__TV_Working_cable();
					enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_default();
					_TV_Unit___TV_On__react(0l);
					
					transitioned_after = 0l;
				}
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _TV_Unit___TV_On__react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _TV_Unit___TV_On__TV_Working_hdmi_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if ((input.cable || input.toggle)) {
				exitSequence__TV_Unit___TV_On__TV_Working_hdmi();
				enterSequence__TV_Unit___TV_On__TV_Working_cable_default();
				_TV_Unit___TV_On__react(0l);
				
				transitioned_after = 0l;
			} else {
				if ((input.satellite || input.toggle)) {
					exitSequence__TV_Unit___TV_On__TV_Working_hdmi();
					enterSequence__TV_Unit___TV_On__TV_Working_satelliteTV_default();
					_TV_Unit___TV_On__react(0l);
					
					transitioned_after = 0l;
				}
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _TV_Unit___TV_On__react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _TV_Unit___TV_onSleep__react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (device.on) {
				exitSequence__TV_Unit___TV_onSleep_();
				enterSequence__TV_Unit___TV_On__default();
				react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = react(transitioned_before);
		}
		return transitioned_after;
	}
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public synchronized void triggerWithoutEvent() {
		runCycle();
	}
}
