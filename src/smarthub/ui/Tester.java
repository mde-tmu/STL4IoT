package smarthub.ui;


import java.awt.event.*;

import smarthub.java.SmartHubSystem;

public class Tester extends DashboardFrame_Editor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void Handler(SmartHubSystem SmartHubSystem) {
		//SMOKE TESTER
				testButton1.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSF().getSmokeSensor().setCounter(0);
						System.out.println("CLICKED");
					}
		        });
		//CARBON TESTER
				testButton2.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSF().getCarbonSensor().setCounter(0);
						System.out.println("CLICKED");
					}
		        });
		//HEAT TESTER
				testButton3.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSF().getHeatSensor().setCounter(0);
						System.out.println("CLICKED");
					}
		        });
		//FIRE ALARM TESTER
				testButton4.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSF().getFireAlarm().alarm().raiseOn();
					}
		        });
		//TV SENSOR TESTER
				testButton5.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSTV().getTV().device().raiseOn();
					}
		        });
		//SMART LIGHT SENSING ACTIVITY TESTER
				testButton6.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						boolean bulbisOn = SmartHubSystem.getSL().getLights().bulb().getIsOn();
						
						if(bulbisOn) {
							SmartHubSystem.getSL().getSensor().setActivity(false);
						}
						else {
							SmartHubSystem.getSL().getSensor().setActivity(true);
						}
					}
		        });
		//MICROWAVE FOOD SENSOR TESTER
				testButton7.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSMW().getSensor().setActivity(false);
					}
		        });
				
				testButton8.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SmartHubSystem.getSF().getFireAlarm().alarm().raiseOn();
					}
		        });
	}
	
	
}