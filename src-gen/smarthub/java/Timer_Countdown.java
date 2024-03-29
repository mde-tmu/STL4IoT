/** Generated by itemis CREATE code generator. */
package smarthub.java;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Timer_Countdown implements ITimed, IEventDriven {
	public enum State {
		MAIN_REGION_TIMER_OFF,
		MAIN_REGION_COUNTING_DOWN,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[1];
	
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		synchronized(Timer_Countdown.this) {
			return isExecuting;
		}
	}
	
	protected void setIsExecuting(boolean value) {
		synchronized(Timer_Countdown.this) {
			this.isExecuting = value;
		}
	}
	public Timer_Countdown() {
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		setTime(5l);
		
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
		
		enterSequence_main_region_default();
		isExecuting = false;
	}
	
	public synchronized void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence_main_region();
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
		start = false;
		end = false;
		timeEvents[0] = false;
	}
	
	private void microStep() {
		switch (stateVector[0]) {
		case MAIN_REGION_TIMER_OFF:
			main_region_Timer_off_react(-1l);
			break;
		case MAIN_REGION_COUNTING_DOWN:
			main_region_Counting_down_react(-1l);
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
		case MAIN_REGION_TIMER_OFF:
			return stateVector[0] == State.MAIN_REGION_TIMER_OFF;
		case MAIN_REGION_COUNTING_DOWN:
			return stateVector[0] == State.MAIN_REGION_COUNTING_DOWN;
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
	
	
	private boolean start;
	
	
	public void raiseStart() {
		synchronized(Timer_Countdown.this) {
			inEventQueue.add(() -> {
				start = true;
			});
			runCycle();
		}
	}
	
	private boolean end;
	
	
	public void raiseEnd() {
		synchronized(Timer_Countdown.this) {
			inEventQueue.add(() -> {
				end = true;
			});
			runCycle();
		}
	}
	
	private long time;
	
	public synchronized long getTime() {
		synchronized(Timer_Countdown.this) {
			return time;
		}
	}
	
	public void setTime(long value) {
		synchronized(Timer_Countdown.this) {
			this.time = value;
		}
	}
	
	/* Entry action for state 'Counting_down'. */
	private void entryAction_main_region_Counting_down() {
		timerService.setTimer(this, 0, (1l * 1000l), false);
		
		time--;
	}
	
	/* Exit action for state 'Counting_down'. */
	private void exitAction_main_region_Counting_down() {
		timerService.unsetTimer(this, 0);
		
		if (getTime()<=0l) {
			raiseEnd();
		}
	}
	
	/* 'default' enter sequence for state Timer_off */
	private void enterSequence_main_region_Timer_off_default() {
		stateVector[0] = State.MAIN_REGION_TIMER_OFF;
	}
	
	/* 'default' enter sequence for state Counting_down */
	private void enterSequence_main_region_Counting_down_default() {
		entryAction_main_region_Counting_down();
		stateVector[0] = State.MAIN_REGION_COUNTING_DOWN;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* Default exit sequence for state Timer_off */
	private void exitSequence_main_region_Timer_off() {
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state Counting_down */
	private void exitSequence_main_region_Counting_down() {
		stateVector[0] = State.$NULLSTATE$;
		
		exitAction_main_region_Counting_down();
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case MAIN_REGION_TIMER_OFF:
			exitSequence_main_region_Timer_off();
			break;
		case MAIN_REGION_COUNTING_DOWN:
			exitSequence_main_region_Counting_down();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Timer_off_default();
	}
	
	private long react(long transitioned_before) {
		return transitioned_before;
	}
	
	private long main_region_Timer_off_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (((start) && (getTime()>0l))) {
				exitSequence_main_region_Timer_off();
				enterSequence_main_region_Counting_down_default();
				react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long main_region_Counting_down_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (getTime()==0l) {
				exitSequence_main_region_Counting_down();
				enterSequence_main_region_Timer_off_default();
				react(0l);
				
				transitioned_after = 0l;
			} else {
				if (timeEvents[0]) {
					exitSequence_main_region_Counting_down();
					timeEvents[0] = false;
					enterSequence_main_region_Counting_down_default();
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
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public synchronized void triggerWithoutEvent() {
		runCycle();
	}
}
