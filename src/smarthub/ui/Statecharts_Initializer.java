package smarthub.ui;


import com.yakindu.core.*;
//import com.yakindu.core.rx.Observable;
import java.text.SimpleDateFormat;  
import java.util.Date; 
//import java.math.*;
import java.text.DecimalFormat;

//FOR AUDIO
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//FOR UI
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

//Atomic Components
//Smart System Java Code Imports
import smarthub.java.*;
import smarthub.java.SmartHubSystem.*;


public class Statecharts_Initializer extends DashboardFrame_Editor{

	private static final long serialVersionUID = -8909693541678814631L;
	public static String AudioFilePath;
	public static Clip clip;
	public static AudioInputStream inputStream;
	public static Random r = new Random();
	public Thread anti_freeze = new Thread();

    private static final DecimalFormat df = new DecimalFormat("0.00");

	protected ITimerService timerService;
    
	protected int 	totalSystemsON = 0,
					SF_Power_kWh = 20,
					STV_Power_kWh = 50,
					SL_Power_kWh = 15,
					SMW_Power_kWh = 10,
					Power_Manager_Threshold = 90,
					TV_input_index = 1;
	
	protected static long CarbonSensor_detection = 14, SmokeSensor_detection = 8, HeatSensor_detection = 10;
	
	protected boolean 	fireAlarm_status, fireAlarm_sensors_triggered, alarmAudio_Played = false,
						TV_status, TV_usage,
						Light_status, Light_usage,
						Microwave_status, Microwave_usage;

	//Statecharts
	public static SmartHubSystem SmartHubSystem;
	protected static SmartFireSystem SmartFireSystem;
	protected static SmartTVSystem SmartTVSystem;
	protected static SmartLightSystem SmartLightSystem;
	protected static SmartMicrowaveSystem SmartMicrowaveSystem;
	
	//Atomic Components used in the Smart Systems
	protected static Actuator_Component SF_Actuator, STV_Actuator, SL_Actuator, SMW_Actuator;
	protected static Controller_Component SF_Controller, STV_Controller, SL_Controller, SMW_Controller;
	protected static Sensor_Component CarbonSensor, HeatSensor, SmokeSensor,
								STV_Sensor, SL_Sensor, SMW_Sensor;
	protected static DeviceTemp_Component SF_Temperature, STV_Temperature, SL_Temperature, SMW_Temperature;
	protected static Power_Component SF_Power, STV_Power, SL_Power, SMW_Power;
	protected static Network_Component SF_WiFi, STV_WiFi, SL_WiFi, SMW_WiFi, HUB_WiFi;
	
	//HUB Manager
	protected Hub_PowerManager HUBPowerManager;
	
	protected FireAlarm_Unit FireAlarm;
	protected TV_Unit TV;
	protected LEDLight_Unit Lights;
	protected Microwave_Unit Microwave;
	
	protected ITimerService timer;
	

	public static Statecharts_Initializer application = new Statecharts_Initializer();

	public static void main(String[] args)throws InterruptedException {

		application.init();
		application.setupStatemachine();
		application.run();
	}
	
	//Setting up all of the statechart java functions
	protected void setupStatemachine() {
		
		//Declaring all Statechart Functions
		SmartHubSystem = new SmartHubSystem();
		HUBPowerManager = new Hub_PowerManager();
		
		SmartFireSystem = new SmartFireSystem();
		SmartTVSystem = new SmartTVSystem();
		SmartLightSystem = new SmartLightSystem();
		SmartMicrowaveSystem = new SmartMicrowaveSystem();
		
		FireAlarm = new FireAlarm_Unit();
		TV = new TV_Unit();
		Lights = new LEDLight_Unit();
		Microwave = new Microwave_Unit();
		
		//END OF MAIN SYSTEM and UNIT STATECHART FUNCTIONS
		
		//ATOMIC COMPONENTS
		SF_Actuator = new Actuator_Component();
		STV_Actuator = new Actuator_Component();
		SL_Actuator = new Actuator_Component();
		SMW_Actuator = new Actuator_Component();
		
		SF_Controller = new Controller_Component();
		STV_Controller = new Controller_Component();
		SL_Controller = new Controller_Component();
		SMW_Controller = new Controller_Component();

		SmokeSensor = new Sensor_Component();
		CarbonSensor = new Sensor_Component();
		HeatSensor = new Sensor_Component();
		STV_Sensor = new Sensor_Component();
		SL_Sensor = new Sensor_Component();
		SMW_Sensor = new Sensor_Component();
		
		SF_Power = new Power_Component();
		STV_Power = new Power_Component();
		SL_Power = new Power_Component();
		SMW_Power = new Power_Component();
		
		SF_Temperature = new DeviceTemp_Component();
		STV_Temperature = new DeviceTemp_Component();
		SL_Temperature = new DeviceTemp_Component();
		SMW_Temperature = new DeviceTemp_Component();
		
		SF_WiFi = new Network_Component();
		STV_WiFi = new Network_Component();
		SL_WiFi = new Network_Component();
		SMW_WiFi = new Network_Component();
		HUB_WiFi = new Network_Component();


		//SMART HUB TIMER SERVICE
		HUBPowerManager.setTimerService(new ScaledTimeTimerService(1.0));
		SmartHubSystem.setTimerService(new ScaledTimeTimerService(1.0));
		
		//SMART SYSTEM TIMER SERVICE
		SmartFireSystem.setTimerService(new ScaledTimeTimerService(1.0));
		SmartTVSystem.setTimerService(new ScaledTimeTimerService(1.0));
		SmartLightSystem.setTimerService(new ScaledTimeTimerService(1.0));
		SmartMicrowaveSystem.setTimerService(new ScaledTimeTimerService(1.0));
		
		//AUTONOMOUS UNIT TIMER SERVICE
		FireAlarm.setTimerService(new ScaledTimeTimerService(1.0));
		TV.setTimerService(new ScaledTimeTimerService(1.0));
		Lights.setTimerService(new ScaledTimeTimerService(1.0));
		Microwave.setTimerService(new ScaledTimeTimerService(1.0));
		
		//ATOMIC COMPONENTS TIMER SERVICE
		SF_Actuator.setTimerService(new ScaledTimeTimerService(1.0));
		STV_Actuator.setTimerService(new ScaledTimeTimerService(1.0));
		SL_Actuator.setTimerService(new ScaledTimeTimerService(1.0));
		SMW_Actuator.setTimerService(new ScaledTimeTimerService(1.0));
		
		SF_Controller.setTimerService(new ScaledTimeTimerService(1.0));
		STV_Controller.setTimerService(new ScaledTimeTimerService(1.0));
		SL_Controller.setTimerService(new ScaledTimeTimerService(1.0));
		SMW_Controller.setTimerService(new ScaledTimeTimerService(1.0));
		
		SmokeSensor.setTimerService(new ScaledTimeTimerService(1.0));
		CarbonSensor.setTimerService(new ScaledTimeTimerService(1.0));
		HeatSensor.setTimerService(new ScaledTimeTimerService(1.0));
		STV_Sensor.setTimerService(new ScaledTimeTimerService(1.0));
		SL_Sensor.setTimerService(new ScaledTimeTimerService(1.0));
		SMW_Sensor.setTimerService(new ScaledTimeTimerService(1.0));
		
		SF_Power.setTimerService(new ScaledTimeTimerService(1.0));
		STV_Power.setTimerService(new ScaledTimeTimerService(1.0));
		SL_Power.setTimerService(new ScaledTimeTimerService(1.0));
		SMW_Power.setTimerService(new ScaledTimeTimerService(1.0));
		
		SF_Temperature.setTimerService(new ScaledTimeTimerService(1.0));		
		STV_Temperature.setTimerService(new ScaledTimeTimerService(1.0));		
		SL_Temperature.setTimerService(new ScaledTimeTimerService(1.0));		
		SMW_Temperature.setTimerService(new ScaledTimeTimerService(1.0));
		
		HUB_WiFi.setTimerService(new ScaledTimeTimerService(1.0));
		SF_WiFi.setTimerService(new ScaledTimeTimerService(1.0));
		STV_WiFi.setTimerService(new ScaledTimeTimerService(1.0));
		SL_WiFi.setTimerService(new ScaledTimeTimerService(1.0));
		SMW_WiFi.setTimerService(new ScaledTimeTimerService(1.0));
		
		/**
		 * THIS SECTION SETS EACH STATECHART VARIABLES TO ITS VALUE
		 * 
		 * WARNING: Without doing this, each variable declaration will default to NULL
		 * **/
		
		SmartHubSystem.setPowerManager(HUBPowerManager);
		
		SmartHubSystem.setSF(SmartFireSystem);
		SmartHubSystem.setSTV(SmartTVSystem);
		SmartHubSystem.setSL(SmartLightSystem);
		SmartHubSystem.setSMW(SmartMicrowaveSystem);

		//Autonomous Unit Setup
		SmartHubSystem.getSF().setFireAlarm(FireAlarm);
		SmartHubSystem.getSTV().setTV(TV);
		SmartHubSystem.getSL().setLights(Lights);
		SmartHubSystem.getSMW().setMW(Microwave);
		
		//Setting up ACTUATOR Component for each system
		SmartHubSystem.getSF().setActuator(SF_Actuator);
		SmartHubSystem.getSTV().setActuator(STV_Actuator);
		SmartHubSystem.getSL().setActuator(SL_Actuator);
		SmartHubSystem.getSMW().setActuator(SMW_Actuator);

		//Setting up CONTROLLER Component for each system
		SmartHubSystem.getSF().setController(SF_Controller);
		SmartHubSystem.getSTV().setController(STV_Controller);
		SmartHubSystem.getSL().setController(SL_Controller);
		SmartHubSystem.getSMW().setController(SMW_Controller);

		//Setting up SENSOR Component for each system
		SmartHubSystem.getSF().setCarbonSensor(CarbonSensor);
		SmartHubSystem.getSF().setSmokeSensor(SmokeSensor);
		SmartHubSystem.getSF().setHeatSensor(HeatSensor);
		SmartHubSystem.getSTV().setSensor(STV_Sensor);
		SmartHubSystem.getSL().setSensor(SL_Sensor);
		SmartHubSystem.getSMW().setSensor(SMW_Sensor);

		//Setting up POWER Component for each system
		SmartHubSystem.getSF().setPower(SF_Power);
		SmartHubSystem.getSTV().setPower(STV_Power);
		SmartHubSystem.getSL().setPower(SL_Power);
		SmartHubSystem.getSMW().setPower(SMW_Power);

		//Setting up TEMPERATURE Component for each system
		SmartHubSystem.getSF().setTemp(SF_Temperature);
		SmartHubSystem.getSTV().setTemp(STV_Temperature);
		SmartHubSystem.getSL().setTemp(SL_Temperature);
		SmartHubSystem.getSMW().setTemp(SMW_Temperature);

		//Setting up WIFI GATEWAY Component for each system
		SmartHubSystem.setWiFi(HUB_WiFi);
		SmartHubSystem.getSF().setWiFi(SF_WiFi);
		SmartHubSystem.getSTV().setWiFi(STV_WiFi);
		SmartHubSystem.getSL().setWiFi(SL_WiFi);
		SmartHubSystem.getSMW().setWiFi(SMW_WiFi);
		
		//
		setState(JFrame.EXIT_ON_CLOSE);
		//new DashboardFrame_Editor().createContents();
    };
    
    //Simulate the statechart
	protected void run() {


		smartFirePanel.setVisible(false);
		smartTVPanel.setVisible(false);
		smartLightPanel.setVisible(false);
		smartMicrowavePanel.setVisible(false);
		
		SmartHubSystem.enter();
		//System.out.println("Smart Hub System statechart is currently running (?): "+SmartHubSystem.isActive());
		//System.out.println("Power Manager System statechart is currently running (?): "+PowerManager.isActive());
		//System.out.println("Smart Fire System statechart is currently running (?): "+SmartFireSystem.isActive());
		//System.out.println("Smart TV System statechart is currently running (?): "+SmartTVSystem.isActive());
		//System.out.println("Smart Lights System statechart is currently running (?): "+SmartLightSystem.isActive());
		//System.out.println("Smart Microwave System statechart is currently running (?): "+SmartMicrowaveSystem.isActive());
		

		SmartHubSystem.power().setThreshold(Power_Manager_Threshold);

		SmartHubSystem.getSTV().getSensor().setSimulate_detection_timer(10);
		SmartHubSystem.getSL().getSensor().setSimulate_detection_timer(10);
		SmartHubSystem.getSMW().getSensor().setSimulate_detection_timer(10);

		
		randomTimerValues(SmartHubSystem, r);
		
		
		//CONTINUOUS DATA READING FROM THE STATECHARTS
		class refresh extends TimerTask {
					@Override
			public void run() {
					    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
					    Date date = new Date();
					    String date_stamp = "("+formatter.format(date)+") : SmartHUBSystem V2.0 is running now...";
					    System.out.println(date_stamp);
					    exitPanelText.setText(date_stamp);
					    
					    if(SmartHubSystem.getWiFi().getConnection())
					    	exitPanelText3.setText("Smart Hub System Network Connection: Connected...");
					    else
					    	exitPanelText3.setText("Smart Hub System Network Connection: Disconnected...Please Reconnect...");

		         try {
		             Thread.sleep(1000);

		             IP_Catcher.runtimePing();
		             IP_Catcher.Local();
		             readStatechartData(SmartHubSystem); //Refresh Values
				 } catch (InterruptedException error) {
				     System.out.println(error);
				 }
			}
		}

		// And From your main() method or any other method
		Timer timer = new Timer(true);
		TimerTask timerTask = new refresh(); //reference created for TimerTask class
		
		timer.schedule(timerTask, 0, 999);
        
        //timer.cancel();
        
		//BUTTON HANDLERS
		ButtonClicksHandler(SmartHubSystem);
		Tester.Handler(SmartHubSystem);

	}
	
	public static void randomTimerValues(SmartHubSystem SmartHubSystem, Random r) {


		//SMARTFIRE SENSOR TIMEOUTS
		SmartHubSystem.getSF().sensorInput().setSmoke_detection_time(SmokeSensor_detection);
		SmartHubSystem.getSF().sensorInput().setCarbon_detection_time(CarbonSensor_detection);
		SmartHubSystem.getSF().sensorInput().setHeat_detection_time(HeatSensor_detection);
		
		SmartHubSystem.getSF().getFireAlarm().sensors().setSmoke_increment(r.nextInt(1,10));
		SmartHubSystem.getSF().getFireAlarm().sensors().setCarbon_increment(r.nextInt(1,10));
		SmartHubSystem.getSF().getFireAlarm().sensors().setHeat_increment(r.nextInt(1,10));
		
		SmartHubSystem.getSL().sensorInput().setDetection_time(r.nextInt(5,10));
		
		//WIFI TIMEOUTS
		SmartHubSystem.getWiFi().setTimeout_value(r.nextInt(20,30));
		SmartHubSystem.getSF().getWiFi().setTimeout_value(r.nextInt(5,20));
		SmartHubSystem.getSTV().getWiFi().setTimeout_value(r.nextInt(5,20));
		SmartHubSystem.getSL().getWiFi().setTimeout_value(r.nextInt(5,20));
		SmartHubSystem.getSMW().getWiFi().setTimeout_value(r.nextInt(5,20));
	}
	
	public static synchronized void playSound(final String AudioFilePath) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        clip = AudioSystem.getClip();
		        inputStream =  AudioSystem.getAudioInputStream(new File(AudioFilePath).getAbsoluteFile());
		        clip.open(inputStream);
		        clip.start();
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
	}
	public static synchronized void stopSound(final String AudioFilePath) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        clip = AudioSystem.getClip();
		        inputStream =  AudioSystem.getAudioInputStream(new File(AudioFilePath).getAbsoluteFile());
		        clip.open(inputStream);
		        clip.stop();
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
	}
	
	/**
	 * SMART HUB DATA
	 * 
	 * THIS FUNCTION ENSURES THAT DATA ON THE YAKINDU STATECHARTS ARE REFLECTED ON TO THE DASHBOARD
	 * 
	 * **/
	public void readStatechartData(SmartHubSystem SmartHubSystem) {
    	
		//RUNNING FUNCTIONS TO READ DATA FROM STATECHART AND OUTPUT DATA ONTO THE DASHBOARD
		if(SmartHubSystem.getWiFi().getConnection()) {
			readNotificationBarDATA(SmartHubSystem);
			readSmartFireSystemDATA(SmartHubSystem);
			readSmartTVSystemDATA(SmartHubSystem);
			readSmartLightSystemDATA(SmartHubSystem);
			readSmartMicrowaveSystemDATA(SmartHubSystem);
		}
		
		//Getting Smart System Activity
		allsystem_status = SmartHubSystem.getAllSystemsOn();
		SF_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM1REGION_SMARTFIRE_STATUS_SMARTFIRESTATUS_ON);
		STV_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM1_STV_ON);
		SL_status= SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM2_SL_ON);
		SMW_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM3_SMW_ON);
		
		boolean hubState = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON),
				emergencyState = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_EMERGENCY_STATE);
		
		//EMERGENCY STATE HANDLER
		if(emergencyState) {
			buttons[4].setText("EMERGENCY STATE IS ACTIVE");
	        smartFire_switch.setText("Fire Alarm: ON");
	        smartFire_systemStatus.setText("System Status: Emergency Mode! 911 has been notified.");
		}
		
		//POWER MANAGER
		
		double total_HUB_power = (double)(SmartHubSystem.power().getTotal())/(double)(SmartHubSystem.getPowerManager().getThreshold())*100;
		
		System.out.println("OVER THRESHOLD:"+SmartHubSystem.getPowerManager().getOver_threshold());
		
		if(total_powerInfo_btn==0) {
			buttons[0].setText("Consumption Level Percentage: "+df.format(total_HUB_power) + " % (MAX: "+SmartHubSystem.getPowerManager().getThreshold()+" kWh)");
		}
		else {
			buttons[0].setText("Current Total Power Consumption: "+df.format(SmartHubSystem.power().getTotal()) + " kWh (MAX: "+SmartHubSystem.getPowerManager().getThreshold()+" kWh)");
		}
		
		
		//MAIN SMART HUB STATE
		if(hubState) {
			buttons[1].setText("HUB Status: ON");
			buttons[0].setEnabled(true);
			buttons[2].setEnabled(true);
			buttons[4].setEnabled(true);
			smartFirePanel.setVisible(true);
			smartTVPanel.setVisible(true);
			smartLightPanel.setVisible(true);
			smartMicrowavePanel.setVisible(true);
		}
		else if(!hubState) {
			buttons[1].setText("HUB Status: OFF");
			buttons[0].setEnabled(false);
			buttons[0].setText("Data Unavailable");
			buttons[2].setEnabled(false);
			buttons[4].setEnabled(false);
			buttons[4].setText("");
			smartFirePanel.setVisible(false);
			smartTVPanel.setVisible(false);
			smartLightPanel.setVisible(false);
			smartMicrowavePanel.setVisible(false);
		}
		//CHECKS IF ALL SYSTEMS ARE ON
		totalSystemsON = (int)SmartHubSystem.getTotalSystemsON();
		
		if(totalSystemsON>=4) {
			buttons[2].setText("Turn OFF all systems");
		}
		else if(totalSystemsON < 4){
			buttons[2].setText("Turn ON all systems");
		}
		int HUBNetwork_timeout = (int)SmartHubSystem.getWiFi().getTimeout_counter(); //CONNECTION LEVEL
		if(SmartHubSystem.getWiFi().getConnection()) {
			buttons[3].setText("HUB Connected: (Timeout:"+HUBNetwork_timeout+")");
			SmartHubSystem.getSF().system().raiseConnect_network();
			SmartHubSystem.getSTV().system().raiseConnect_network();
			SmartHubSystem.getSL().system().raiseConnect_network();
			SmartHubSystem.getSMW().system().raiseConnect_network();

			buttons[4].setEnabled(true);
			notifsViewButton.setText(notifsView_Title[notifsView_value]);
			notifsViewButton.setEnabled(true);

			smartFire_switch.setEnabled(true);
			smartTV_switch.setEnabled(true);
			smartTV_use.setEnabled(true);
			smartTV_inputSource.setEnabled(true);
			
			smartLight_switch.setEnabled(true);
			smartLight_use.setEnabled(true);
			smartLight_brightness.setEnabled(true);
			
			smartMicrowave_switch.setEnabled(true);
			smartMicrowave_use.setEnabled(true);
			smartMicrowave_startTimer.setEnabled(true);
			smartMicrowave_addTimer.setEnabled(true);
			smartMicrowave_resetTimer.setEnabled(true);
			smartMicrowave_doorStatus.setEnabled(true);
			
		}
		else if(!SmartHubSystem.getWiFi().getConnection()){
			buttons[3].setText("Network: Not Connected");
			SmartHubSystem.getSF().system().raiseDisconnect_network();
			SmartHubSystem.getSTV().system().raiseDisconnect_network();
			SmartHubSystem.getSL().system().raiseDisconnect_network();
			SmartHubSystem.getSMW().system().raiseDisconnect_network();
			buttons[4].setText("Network ERROR! Please wait until system reconnects...");
			buttons[4].setEnabled(false);
			
			notifsViewButton.setText("Network ERROR! Please wait until system reconnects...");
			notifsViewButton.setEnabled(false);
			notifsText1.setText("");
			notifsText2.setText("");
			notifsText3.setText("");
			notifsText4.setText("");
			
			smartFirePanel.setBorder(
	        		BorderFactory.createTitledBorder("SYSTEM 1 is disconnected"));
					smartFire_switch.setEnabled(false);
			smartTVPanel.setBorder(
	        		BorderFactory.createTitledBorder("SYSTEM 2 is disconnected"));
					smartTV_switch.setEnabled(false);
					smartTV_use.setEnabled(false);
					smartTV_inputSource.setEnabled(false);
			smartLightPanel.setBorder(
	        		BorderFactory.createTitledBorder("SYSTEM 3 is disconnected"));
					smartLight_switch.setEnabled(false);
					smartLight_use.setEnabled(false);
					smartLight_brightness.setEnabled(false);
			smartMicrowavePanel.setBorder(
	        		BorderFactory.createTitledBorder("SYSTEM 4 is disconnected"));
					smartMicrowave_switch.setEnabled(false);
					smartMicrowave_use.setEnabled(false);
					smartMicrowave_startTimer.setEnabled(false);
					smartMicrowave_addTimer.setEnabled(false);
					smartMicrowave_resetTimer.setEnabled(false);
					smartMicrowave_doorStatus.setEnabled(false);
		}
	}
	
	/**
	 * FUNCTION FOR THE NOTIFICATION TABS
	 * **/
	
	public void readNotificationBarDATA(SmartHubSystem SmartHubSystem) {

		
		//SYSTEM USAGE
		if(notifsView_value==0){ 
			//SMART FIRE
			if(SF_status) {
		        notifsText1.setText("Smart Fire System: ON");
			}else if(!SF_status) {
		        notifsText1.setText("Smart Fire System: OFF");
			}
			//SMART TV
			if(STV_status) {
		        notifsText2.setText("Smart TV System: ON");
			}else if(!STV_status) {
		        notifsText2.setText("Smart TV System: OFF");
			}
			//SMART LIGHTS
			if(SL_status) {
		        notifsText3.setText("Smart Lights System: ON");
			}else if(!SL_status) {
		        notifsText3.setText("Smart Lights System: OFF");
			}
			//SMART MICROWAVE
			if(SMW_status) {
		        notifsText4.setText("Smart Microwave System: ON");
			}else if(!SMW_status) {
		        notifsText4.setText("Smart Microwave System: OFF");
			}
		}
		//WIFI STATUS
		else if(notifsView_value==1) {
			//SMARTFIRE
			boolean SF_connection = SmartHubSystem.getSF().getWiFi_connection();
			String connection1 = "";
			if(SF_connection) {
				connection1 = "Connected";
				smartFire_switch.setEnabled(true);
			}else if(!SF_connection) {
				connection1 = "Not Connected...(Reconnecting...)";
				smartFire_switch.setEnabled(false);
			}
			//STV
			boolean STV_connection = SmartHubSystem.getSTV().getWiFi_connection();
			String connection2 = "";
			if(STV_connection) {
				connection2 = "Connected";
				smartTV_switch.setEnabled(true);
				smartTV_use.setEnabled(true);
				smartTV_inputSource.setEnabled(true);
			}else if(!STV_connection) {
				connection2 = "Not Connected...(Reconnecting...)";
				smartTV_switch.setEnabled(false);
				smartTV_use.setEnabled(false);
				smartTV_inputSource.setEnabled(false);
			}
			//SMARTLIGHT
			boolean SL_connection = SmartHubSystem.getSL().getWiFi_connection();
			String connection3 = "";
			if(SL_connection) {
				connection3 = "Connected";
				smartLight_switch.setEnabled(true);
			}else if(!SL_connection) {
				connection3 = "Not Connected...(Reconnecting...)";
				smartLight_switch.setEnabled(false);
			}
			//SMARTMICROWAVE
			boolean SMW_connection = SmartHubSystem.getSMW().getWiFi_connection();
			String connection4 = "";
			if(SMW_connection) {
				connection4 = "Connected";
				smartMicrowave_switch.setEnabled(true);
			}else if(!SMW_connection) {
				connection4 = "Not Connected...(Reconnecting...)";
				smartMicrowave_switch.setEnabled(false);
			}
	        notifsText1.setText("System 1 Network Connection Status: "+connection1);
	        notifsText2.setText("System 2 Network Connection Status: "+connection2);
	        notifsText3.setText("System 3 Network Connection Status: "+connection3);
	        notifsText4.setText("System 4 Network Connection Status: "+connection4);
		}
		//USAGE STATUS
		else if(notifsView_value==2) {
			if(fireAlarm_status)
				notifsText1.setText("SYSTEM 1: IN USE");
			else
				notifsText1.setText("SYSTEM 1: NOT IN USE");
			
			if(TV_usage)
				notifsText2.setText("SYSTEM 2: IN USE ("+smartTV_inputSource.getText()+")");
			else
				notifsText2.setText("SYSTEM 2: NOT IN USE");
			
			if(Light_usage)
				notifsText3.setText("SYSTEM 3: IN USE");
			else
				notifsText3.setText("SYSTEM 3: NOT IN USE");
			
			if(Microwave_usage)
				notifsText4.setText("SYSTEM 4: IN USE");
			else
				notifsText4.setText("SYSTEM 4: NOT IN USE");
		}
		//POWER CONSUMPTION
		else if(notifsView_value==3) {
			double current_Power = (double)SmartHubSystem.power().getTotal();
			
			String 	SF_power = df.format(((double)SF_Power_kWh/(double)current_Power)*100)+" %",
					STV_power = df.format(((double)STV_Power_kWh/(double)current_Power)*100)+" %",
					SL_power = df.format(((double)SL_Power_kWh/(double)current_Power)*100)+" %",
					SMW_power = df.format(((double)SMW_Power_kWh/(double)current_Power)*100)+" %";
			
	        if(SF_status)
		        notifsText1.setText("Smart Fire System: " + SF_power);
			else
		        notifsText1.setText("Smart Fire System: 0 %");

			
	        if(TV_status)
				notifsText2.setText("Smart TV System: " + STV_power);
			else
				notifsText2.setText("Smart TV System: " + " 0 %");

			if(Light_status)
				notifsText3.setText("Smart Lights System:  "+ SL_power);
			else
				notifsText3.setText("Smart Lights System: " +"0 %");

			if(Microwave_status)
				notifsText4.setText("Smart Microwave System: " + SMW_power);
			else
				notifsText4.setText("Smart Microwave System: " + "0 %");
		}
	}
	
	/**
	 SMART FIRE SYSTEM DATA MANAGEMENT PANEL
	 * **/
	
	public void readSmartFireSystemDATA(SmartHubSystem SmartHubSystem) {
		//FOR AUDIO
		try {
	        clip = AudioSystem.getClip();
	        inputStream =  AudioSystem.getAudioInputStream(new File("Audio/firealarm2.wav").getAbsoluteFile());
	        clip.open(inputStream);
	      } catch (Exception e) {
	        System.err.println(e.getMessage());
	      }
		

		SmartHubSystem.getSF().getFireAlarm().sensors().setCarbon_threshold(100);
		SmartHubSystem.getSF().getFireAlarm().sensors().setSmoke_threshold(100);
		
		fireAlarm_status = SmartHubSystem.getSF().getFireAlarm().alarm().getSound();
		fireAlarm_sensors_triggered = SmartHubSystem.getSF().getFireAlarm().sensors().getTriggerSignal_received();
		//SAFE or WARNING or DANGER MODE
		String mode_status = SmartHubSystem.getSF().getFireAlarm().message().getStatus();
        smartFire_message.setText("Current Status: "+mode_status);
        

        
		//POWER COMPONENT
		SmartHubSystem.smartFire().setKWh(SF_Power_kWh);
		SmartHubSystem.getSF().getPower().setHour(1);
		String SF_kWh = " " + SmartHubSystem.getSF().getPower_input();
        
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSF().getWiFi_connection();
		int network_timeout = (int)SmartHubSystem.getSF().getWiFi().getTimeout_counter(); //CONNECTION LEVEL
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
        smartFirePanel.setBorder(
        		BorderFactory.createTitledBorder("SYSTEM 1: Smart Fire System ("+connection+" - "+network_timeout+") - "+SF_kWh+" kWh"));
      
        
        //FIRE ALARM SOUNDING STATUS
		if(fireAlarm_status) {
			smartFire_message.setBackground(Color.RED);
	        buttons[4].setText("Smart Fire System has an emergency alert!");
	        smartFire_switch.setText("Fire Alarm: ON");
	        SmartHubSystem.hUB().raiseTurnOFFSystems();
	        SmartHubSystem.getSF().getCarbonSensor().raiseDetect();
	        SmartHubSystem.getSF().getSmokeSensor().raiseDetect();
	        SmartHubSystem.getSF().getHeatSensor().raiseDetect();
		}
		else if(!fireAlarm_status){
	        if(SmartHubSystem.getSF().getFireAlarm().getWarned()){
				smartFire_message.setBackground(Color.orange.darker());
	        }
	        else
	        	smartFire_message.setBackground(Color.GREEN.brighter());
	        
			smartFirePanel.setBackground(Color.WHITE.brighter());
	        smartFire_switch.setText("Fire Alarm: OFF");
		}
			
		//FIRE ALARM SENSORS TRIGGERED



		if(fireAlarm_sensors_triggered) {
			boolean smoke_presence = SmartHubSystem.getSF().getFireAlarm().sensors().getSmoke_presence(),
					carbon_presence = SmartHubSystem.getSF().getFireAlarm().sensors().getCarbon_presence(),
					heat_presence = SmartHubSystem.getSF().getFireAlarm().sensors().getHeat_presence(),
					
					smoke_activity = SmartHubSystem.getSF().smokeSensorOutput().getActivity(),
					carbon_activity = SmartHubSystem.getSF().carbonSensorOutput().getActivity(),
					heat_activity = SmartHubSystem.getSF().heatSensorOutput().getActivity();
			
			String system_detection_status = SmartHubSystem.getSF().sensorOutput().getDetected();
			
			System.out.println("PRESENCE(Smoke, Carbon, Heat) :("+smoke_presence+carbon_presence+heat_presence+")");
			System.out.println("ACTIVITY(Smoke, Carbon, Heat) :("+smoke_activity+carbon_activity+heat_activity+")");
			
			if(smoke_presence) {
				smartFire_systemStatus.setText("System Status: "+system_detection_status+" (Timer: "+SmartHubSystem.getSF().getFireAlarm().timer().getCounter()+")");
			}
			else if(carbon_presence) {
				smartFire_systemStatus.setText("System Status: "+system_detection_status+" (Timer: "+SmartHubSystem.getSF().getFireAlarm().timer().getCounter()+")");
			}
			else if(heat_presence) {
				smartFire_systemStatus.setText("System Status: "+system_detection_status+" (Timer: "+SmartHubSystem.getSF().getFireAlarm().timer().getCounter()+")");
			}
			
	        //CARBON AND SMOKE LEVELS
			String carbon_value, smoke_value, heat_value;
			
			//SMOKE LEVEL
	        if(SmartHubSystem.getSF().getFireAlarm().sensors().getSmoke_value()>=100) {
	        	SmartHubSystem.getSF().getFireAlarm().mode().raiseDanger();
		        smoke_value = "MAX 100";
	        }
	        else {
		        smoke_value = " "+ SmartHubSystem.getSF().getFireAlarm().sensors().getSmoke_value();
	        }
	        
	        //CARBON LEVEL
	        if(SmartHubSystem.getSF().getFireAlarm().sensors().getCarbon_value()>=100) {
	        	SmartHubSystem.getSF().getFireAlarm().mode().raiseDanger();
	        	carbon_value = "MAX 100";
	        }
	        else {
	        	carbon_value = " "+ SmartHubSystem.getSF().getFireAlarm().sensors().getCarbon_value();
	        }

	        //HEAT LEVEL
	        if(SmartHubSystem.getSF().getFireAlarm().sensors().getHeat_value()>=100) {
	        	SmartHubSystem.getSF().getFireAlarm().mode().raiseDanger();
	        	heat_value = "MAX 100";
	        }
	        else {
	        	heat_value = " "+ SmartHubSystem.getSF().getFireAlarm().sensors().getHeat_value();
	        }
	        
	        smartFire_smokeSensor.setText("Smoke Sensor Level (Obscuration / ft): "+ smoke_value +"% over threshold");
	        smartFire_carbonSensor.setText("Carbon Sensor Level (Parts per million): "+carbon_value+"% over threshold");
	        smartFire_heatSensor.setText("Heat Sensor Level (Celsius): "+ heat_value +"% over threshold");
		}
		else {
			smartFire_systemStatus.setText("System Status: Monitoring...");
	        
	        //CARBON, SMOKE , HEAT LEVELS
	        smartFire_smokeSensor.setText("Smoke Sensor Level (Obscuration / ft): Monitoring...");
	        smartFire_carbonSensor.setText("Carbon Sensor Level (Parts per million): Monitoring...");
	        smartFire_heatSensor.setText("Heat Sensor Level (Celsius): Monitoring...");
		}
	}
	
	
	/**
	 SMART TV SYSTEM DATA MANAGEMENT PANEL
	 * **/
	
	public void readSmartTVSystemDATA(SmartHubSystem SmartHubSystem) {
		
		TV_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM1_STV_ON);
		TV_usage = SmartHubSystem.getSTV().getTV().device().getIsOn();
		String TV_input = SmartHubSystem.getSTV().getTV().input().getSource();
		String usage_status = "", system_status = "";
		
		//TV Usage Status
		if(TV_status) {
			if(TV_usage) {
				usage_status = "TV Usage Status: IN USE";
				smartTV_inputSource.setEnabled(true);
				smartTV_inputSource.setText(TV_input);
			}
			else if(!TV_usage) {
				usage_status = "TV Usage Status: NOT IN USE";
				smartTV_inputSource.setEnabled(false);
				smartTV_inputSource.setText("Data Currently Not Available");
			}
			
			system_status = "TV System Status: ON";

			smartTV_use.setEnabled(true);
			smartTV_use.setText(usage_status);
			smartTVPanel.setBackground(Color.green);
		}else if(!TV_status){
			system_status = "TV System Status: OFF";
			smartTVPanel.setBackground(Color.gray.brighter());
			smartTV_use.setEnabled(false);
			smartTV_inputSource.setEnabled(false);
			smartTV_inputSource.setText("Data Currently Not Available");
		}

        smartTV_switch.setText(system_status);
       
		//POWER COMPONENT
		SmartHubSystem.smartTV().setKWh(STV_Power_kWh);
		SmartHubSystem.getSTV().getPower().setHour(1);
		String STV_kWh = " " + SmartHubSystem.smartTV().getKWh();
       
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSTV().getWiFi_connection();
		int network_timeout = (int)SmartHubSystem.getSTV().getWiFi().getTimeout_counter(); //CONNECTION TIMEOUT
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
			smartTV_use.setEnabled(true);
			smartTV_inputSource.setEnabled(true);
		}else if(!WiFi_connection) {
			connection = "Not Connected";
			smartTV_use.setEnabled(false);
			smartTV_inputSource.setEnabled(false);
		}
		
		//PANEL TITLE
       smartTVPanel.setBorder(
       		BorderFactory.createTitledBorder("SYSTEM 2: Smart TV System ("+connection+" - "+network_timeout+") - "+STV_kWh+" kWh"));
       
	}
	
	/**
	 SMART LIGHTS SYSTEM DATA MANAGEMENT PANEL
	 * **/
	
	public void readSmartLightSystemDATA(SmartHubSystem SmartHubSystem) {
		
		Light_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM2_SL_ON);
		Light_usage = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM3REGION_SMARTLIGHT_STATUS_SMARTLIGHTSTATUS_ON_SENSORSTATUS_ACTIVE);
		String usage_status = "", system_status = "";
		//Lights Usage Status
		if(Light_status) {
			if(Light_usage)
				usage_status = "Light Usage Status: IN USE";
			else if(!Light_usage)
				usage_status = "Light Usage Status: NOT IN USE";
			
			system_status = "Light System Status: ON";

			smartLight_use.setEnabled(true);
			smartLight_use.setText(usage_status);
			smartLightPanel.setBackground(Color.green);
			
			var brightness_level = SmartHubSystem.getSL().getLights().brightness().getLevel();
			smartLight_brightness.setText("Brightness Level: "+brightness_level);
			smartLight_brightness.setEnabled(true);
			
			if(brightness_level==1) {
				smartLightPanel.setBackground(Color.orange.darker());
			}else if(brightness_level==2) {
				smartLightPanel.setBackground(Color.orange);
			}else if(brightness_level==3) {
				smartLightPanel.setBackground(Color.yellow.darker());
			}else if(brightness_level==4) {
				smartLightPanel.setBackground(Color.yellow.brighter());
			}
			
		}else if(!Light_status){
			system_status = "Light System Status: OFF";
			smartLightPanel.setBackground(Color.gray.brighter());
			smartLight_use.setEnabled(false);
			smartLight_use.setText("Data Currently Not Available");
			smartLight_brightness.setEnabled(false);
		}

       smartLight_switch.setText(system_status);
      
		//POWER COMPONENT
		SmartHubSystem.smartLight().setKWh(SL_Power_kWh);
		SmartHubSystem.getSL().getPower().setHour(1);
		String SL_kWh = " " + SmartHubSystem.smartLight().getKWh();
      
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSL().getWiFi_connection();
		int network_timeout = (int)SmartHubSystem.getSL().getWiFi().getTimeout_counter(); //CONNECTION TIMEOUT
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
      smartLightPanel.setBorder(
      		BorderFactory.createTitledBorder("SYSTEM 3: Smart Lights System ("+connection+" - "+network_timeout+") - "+SL_kWh+" kWh"));
      
	}
	
	/**
	 SMART MICROWAVE SYSTEM DATA MANAGEMENT PANEL
	 * **/

	public void readSmartMicrowaveSystemDATA(SmartHubSystem SmartHubSystem) {
		

		Microwave_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM4REGION_SMARTMICROWAVE_STATUS_SMARTMICROWAVESTATUS_ON);

		Microwave_usage = SmartHubSystem.getSMW().getMW().getIn_use();
		
		boolean food_inside = SmartHubSystem.getSMW().getMW().getFood_inside_sensed();
		boolean doorClosed = SmartHubSystem.getSMW().getMW().door().getClosed();
		
		String usage_status = "", system_status = "";
		
		//Lights Usage Status
		if(Microwave_status) {
			smartMicrowave_addTimer.setVisible(true);
			smartMicrowave_startTimer.setVisible(true);
			smartMicrowave_resetTimer.setVisible(true);
			smartMicrowave_doorStatus.setVisible(true);
			
			smartMicrowave_addTimer.setText("Current timer: +"+SmartHubSystem.getSMW().getMW().device().getTimer()+" seconds...");
			smartMicrowave_startTimer.setText("Start/Stop Timer");
			smartMicrowave_resetTimer.setText("Reset Timer");
			
			
			
			//Disble timer if value is zero
			if(SmartHubSystem.getSMW().getMW().device().getTimer() <= 0) {
				smartMicrowave_startTimer.setEnabled(false);
				smartMicrowave_resetTimer.setEnabled(false);
			}else {
				smartMicrowave_startTimer.setEnabled(true);
				smartMicrowave_resetTimer.setEnabled(true);
			}
			
			if(doorClosed) {
				smartMicrowave_doorStatus.setText("Microwave Door: CLOSED");
			}else if(!doorClosed) {
				smartMicrowave_startTimer.setEnabled(false);
				smartMicrowave_doorStatus.setText("Microwave Door: OPEN");
			}
			
			if(food_inside) {
				usage_status = "Microwave has food inside";
				if(SmartHubSystem.getSMW().getMW().getIn_use()) {
					usage_status = SmartHubSystem.getSMW().getMW().getMessage();
				}
				
			}
			else if(!food_inside)
				usage_status =  "Microwave has NO food inside!";
			
			system_status = "Microwave System Status: ON";

			smartMicrowave_use.setEnabled(true);
			smartMicrowave_use.setText(usage_status);
			smartMicrowavePanel.setBackground(Color.green);
		}else if(!Microwave_status){
			system_status = "Microwave System Status: OFF";
			smartMicrowavePanel.setBackground(Color.gray.brighter());
			smartMicrowave_use.setEnabled(false);
			smartMicrowave_use.setText("Data Currently Not Available");
			
			smartMicrowave_addTimer.setVisible(false);
			smartMicrowave_startTimer.setVisible(false);
			smartMicrowave_resetTimer.setVisible(false);
			smartMicrowave_doorStatus.setVisible(false);
		}

      smartMicrowave_switch.setText(system_status);
     
		//POWER COMPONENT
		SmartHubSystem.smartMicrowave().setKWh(SMW_Power_kWh);
		SmartHubSystem.getSMW().getPower().setHour(1);
		String SMW_kWh = " " + SmartHubSystem.smartMicrowave().getKWh();
     
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSMW().getWiFi_connection();
		int network_timeout = (int)SmartHubSystem.getSMW().getWiFi().getTimeout_counter(); //CONNECTION TIMEOUT
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
		smartMicrowavePanel.setBorder(
     		BorderFactory.createTitledBorder("SYSTEM 4: Smart Microwave System ("+connection+" - "+network_timeout+") - "+SMW_kWh+" kWh"));
     
	}
	
	/**
	 BUTTON CLICKS HANDLER
	 * **/

	public int total_powerInfo_btn = 0;
	
	public void ButtonClicksHandler(SmartHubSystem SmartHubSystem) {
		//TOTAL POWER CONSUMPTION SWITCH
        buttons[0].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {
					@Override
					public void run() {
						if(total_powerInfo_btn==0) {
							total_powerInfo_btn = 1;
						}
						else {
							total_powerInfo_btn = 0;
						}
					}
				});
				anti_freeze.start();
			}
        });
		//SMART HUB MAIN SWITCH
        buttons[1].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.raiseToggle();
					}
				});
				anti_freeze.start();
			}
        });
        
		//TURNING ON AND OFF ALL SYSTEMS
        buttons[2].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {// TODO Auto-generated method stub
						totalSystemsON = (int)SmartHubSystem.getTotalSystemsON();
						playSound("Audio/click.wav");
						if(totalSystemsON>=4) {
							SmartHubSystem.hUB().raiseTurnOFFSystems();
						}
						else if(totalSystemsON < 4){
							SmartHubSystem.hUB().raiseTurnONSystems();
						}
					}
				});
				anti_freeze.start();
			}
			
        });
        //HUB WiFi Connection
        buttons[3].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(SmartHubSystem.getWiFi().getConnection()) {
					SmartHubSystem.getWiFi().raiseOff();
					//SmartHubSystem.getWiFi().setConnection(false);
				}
				else if(!SmartHubSystem.getWiFi().getConnection()){
					SmartHubSystem.getWiFi().raiseOn();
					//SmartHubSystem.getWiFi().setConnection(true);
				}
				//System.exit(0);
			}
        	
        });
        
        //HUB Notification
        buttons[4].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playSound("Audio/click.wav");
				buttons[4].setText("WiFi Connection: Connected...");
			}
        	
        });
        
        notifsViewButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				anti_freeze = new Thread(new Runnable() {
					@Override
					public void run() {
						playSound("Audio/click.wav");
						notifsView_value++;
						if(notifsView_value < notifsView_Title.length) {
							notifsViewButton.setText(notifsView_Title[notifsView_value]);
						}
						else {
							notifsView_value = 0;
							notifsViewButton.setText(notifsView_Title[notifsView_value]);
						}
					}
				});
				anti_freeze.start();
			}
        });

        /**
         * TESTER
         */
      //HUB WiFi Connection
        testButton1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(SmartHubSystem.getWiFi().getConnection()) {
					SmartHubSystem.getWiFi().raiseOff();
					//SmartHubSystem.getWiFi().setConnection(false);
				}
				else if(!SmartHubSystem.getWiFi().getConnection()){
					SmartHubSystem.getWiFi().raiseOn();
					//SmartHubSystem.getWiFi().setConnection(true);
				}
				//System.exit(0);
			}
        	
        });
        
        /**
         * 
         * 
         * SMART SYSTEM BUTTONS BELOW
         * 
         * 
         * **/
        
        //SMART FIRE SWITCH
        smartFire_switch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						playSound("Audio/click.wav");
						if(fireAlarm_status) {
							SmartHubSystem.getSF().getFireAlarm().mode().raiseSafe();

							SmartHubSystem.getSF().getSmokeSensor().setSimulate_detection_timer(r.nextInt(15,20));
							SmartHubSystem.getSF().getCarbonSensor().setSimulate_detection_timer(r.nextInt(15,20));
							SmartHubSystem.getSF().getHeatSensor().setSimulate_detection_timer(r.nextInt(15,20));
							
							SmartHubSystem.getSF().getCarbonSensor().setActivity(false);
							SmartHubSystem.getSF().getHeatSensor().setActivity(false);
							SmartHubSystem.getSF().getSmokeSensor().setActivity(false);

							SmartHubSystem.getSF().sensorOutput().setActivity(false);
							
							SmartHubSystem.getSF().sensorInput().raiseReset();

							//SmartHubSystem.getSF().getSmok

							
							randomTimerValues(SmartHubSystem, r);
					        //smartFire_switch.setText("Fire Alarm: OFF");
						}
						else if(!fireAlarm_status){ //to turn on
							//smartFirePanel.setBackground(Color.RED.brighter());
							SmartHubSystem.getSF().getFireAlarm().mode().raiseDanger();
							SmartHubSystem.getSF().getCarbonSensor().setCounter(0);
							SmartHubSystem.getSF().getSmokeSensor().setCounter(0);
							SmartHubSystem.getSF().getHeatSensor().setCounter(0);
					        //smartFire_switch.setText("Fire Alarm: ON");
						}
				}
				});
				anti_freeze.start();
			}
        });
        
		//TV SWITCH
        smartTV_switch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {
					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.smartTV().raiseToggle();
					}
				});
				anti_freeze.start();
				
			}
        });
        //TV USE 
        smartTV_use.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						if(TV_usage) {
							SmartHubSystem.getSTV().getTV().device().raiseOff();
						}else if(!TV_usage)
							SmartHubSystem.getSTV().getTV().device().raiseOn();
					}
				});
				anti_freeze.start();
			}
        });
        //TV INPUT SOURCE
        smartTV_inputSource.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {
					@Override
					public void run() {
						playSound("Audio/click.wav");
						if(TV_input_index == 1) {
							TV_input_index++;
							SmartHubSystem.getSTV().getTV().input().raiseHdmi();
						}
						else if(TV_input_index == 2) {
							TV_input_index++;
							SmartHubSystem.getSTV().getTV().input().raiseCable();
						}
						else if(TV_input_index == 3) {
							TV_input_index = 1;
							SmartHubSystem.getSTV().getTV().input().raiseSatellite();
						}
					}
				});
				anti_freeze.start();
			}
        });
        
		//LIGHT SWITCH
        smartLight_switch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.smartLight().raiseToggle();
					}
				});
				anti_freeze.start();
			}
        });
        //LED LIGHT UNIT SWITCH
        smartLight_use.addActionListener(e -> {
			playSound("Audio/click.wav");
			boolean bulbisOn = SmartHubSystem.getSL().getLights().bulb().getIsOn();
			
			if(bulbisOn) {
				SmartHubSystem.getSL().getSensor().setActivity(false);
			}
			else {
				SmartHubSystem.getSL().getSensor().setActivity(true);
			}
		});
      //LED LIGHT UNIT SWITCH
        smartLight_use.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						boolean bulbisOn = SmartHubSystem.getSL().getLights().bulb().getIsOn();
						
						if(bulbisOn) {
							SmartHubSystem.getSL().getSensor().setActivity(false);
						}
						else {
							SmartHubSystem.getSL().getSensor().setActivity(true);
						}
					}
				});
				anti_freeze.start();
			}
        });
        
       //LED LIGHT BRIGHTNESS SWITCH
        smartLight_brightness.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						int level = (int)SmartHubSystem.getSL().getLights().brightness().getLevel();
						if(level>3) {
							SmartHubSystem.getSL().getLights().brightness().setLevel(1);
						}
						else
							SmartHubSystem.getSL().getLights().brightness().raiseUp();
					}
				});
				anti_freeze.start();
			}
        });
        /**************   MICROWAVE BUTTONS   ***************/
        
        //Microwave System Switch
        smartMicrowave_switch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.smartMicrowave().raiseToggle();
					}
				});
				anti_freeze.start();
			}

        });
        //Microwave Unit Switch
        smartMicrowave_use.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						var food_inside = SmartHubSystem.getSMW().getMW().getFood_inside_sensed();
						
						if(food_inside) {
							SmartHubSystem.getSMW().getSensor().setActivity(false);
						}
						else {
							SmartHubSystem.getSMW().getSensor().setActivity(true);
						}
					}
				});
				anti_freeze.start();
			}
        });
      //Microwave Add Timer Switch
        smartMicrowave_addTimer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.getSMW().getMW().device().raiseAddTimer();
					}
				});
				anti_freeze.start();
			}
        });
      //Microwave Reset Timer Switch
        smartMicrowave_resetTimer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.getSMW().getMW().device().raiseResetTimer();
					}
				});
				anti_freeze.start();
			}
        });
        //Microwave Start Timer Switch
        smartMicrowave_startTimer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						SmartHubSystem.getSMW().getMW().device().raiseStart();
					}
				});
				anti_freeze.start();
			}
        }); //Microwave Door Switch
        smartMicrowave_doorStatus.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
						playSound("Audio/click.wav");
						boolean doorClosed = SmartHubSystem.getSMW().getMW().door().getClosed();
						
						if(doorClosed) {
							SmartHubSystem.getSMW().getMW().door().raiseOpen();
						}else if(!doorClosed) {
							SmartHubSystem.getSMW().getMW().door().raiseClose();
						}
					}
				});
				anti_freeze.start();
			}
        });
        
        exitButton.addActionListener(e -> System.exit(0));
        /**
         * FOR BUTTON BEHAVIOURS
         * -->THIS IS TO PREVENT ANY JFRAME CRASHES
         * -->CRASH MAY OCCUR DUE TO MULTIPLE STATECHARTS RUNNING SIMULTANEOUSLY
         * 
         * anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
					}
				});
				anti_freeze.start();
         * 
         * **/
	}


	/**
	 * END OF FILE
	 * 
	 * Author: Clyde Rempillo
	 * Toronto Metropolitan University
	 * Written in January 2023
	 * **/
}
