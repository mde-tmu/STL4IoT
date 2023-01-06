package smarthub.ui;

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
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
//import javax.xml.transform.Templates;

import com.yakindu.core.ITimerService;

//Smart System Java Code Imports
import smarthub.java.SmartFireSystem;
import smarthub.java.SmartHubSystem;
import smarthub.java.SmartHubSystem.State;
import smarthub.java.SmartLightSystem;
import smarthub.java.SmartMicrowaveSystem;
import smarthub.java.SmartTVSystem;

//Atomic Components
import smarthub.java.Actuator_Component;
import smarthub.java.Controller_Component;
import smarthub.java.Sensor_Component;
import smarthub.java.DeviceTemp_Component;
import smarthub.java.Power_Component;
import smarthub.java.WiFi_Component;

//Autonomous Components
import smarthub.java.*;

import smarthub.java.Hub_Power_Manager;


public class Statecharts_Initializer extends DashboardFrame_Editor{

	//private static final long serialVersionUID = -8909693541678814631L;
	public static String AudioFilePath;
	public static Clip clip;
	public static AudioInputStream inputStream;
	public Random r = new Random();
	public Thread anti_freeze = new Thread();

    private static final DecimalFormat df = new DecimalFormat("0.00");
    public String runtime_PING;

    //PING MONITOR
    private static SystemPingMonitor SystemRuntime = new SystemPingMonitor();
    
	protected int 	totalSystemsON = 0,
					SF_Power_kWh = 20,
					STV_Power_kWh = 50,
					SL_Power_kWh = 15,
					SMW_Power_kWh = 10,
					Power_Manager_Threshold = 90,
					TV_input_index = 1;
	
	protected boolean 	fireAlarm_status, fireAlarm_sensors_triggered, alarmAudio_Played = false,
						TV_status, TV_usage,
						Light_status, Light_usage,
						Microwave_status, Microwave_usage;

	//Statecharts
	public SmartHubSystem SmartHubSystem;
	protected SmartFireSystem SmartFireSystem;
	protected SmartTVSystem SmartTVSystem;
	protected SmartLightSystem SmartLightSystem;
	protected SmartMicrowaveSystem SmartMicrowaveSystem;
	
	//Atomic Components used in the Smart Systems
	protected Actuator_Component Actuator;
	protected Controller_Component Controller;
	protected Sensor_Component Sensor;
	protected DeviceTemp_Component Temperature;
	protected Power_Component Power;
	protected WiFi_Component WiFi;
	
	//HUB Manager
	protected Hub_Power_Manager PowerManager;
	
	protected FireAlarm_Unit FireAlarm;
	protected TV_Unit TV;
	protected LEDLight_Unit Lights;
	protected Microwave_Unit Microwave;
	

	private static final long serialVersionUID = 1L;
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
		PowerManager = new Hub_Power_Manager();
		
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
		Actuator = new Actuator_Component();
		Controller = new Controller_Component();
		Sensor = new Sensor_Component();
		Power = new Power_Component();
		Temperature = new DeviceTemp_Component();
		WiFi = new WiFi_Component();


		//SMART HUB TIMER SERVICE
		PowerManager.setTimerService(new ScaledTimeTimerService(5.0));
		SmartHubSystem.setTimerService(new ScaledTimeTimerService(5.0));
		
		//SMART SYSTEM TIMER SERVICE
		SmartFireSystem.setTimerService(new ScaledTimeTimerService(5.0));
		SmartTVSystem.setTimerService(new ScaledTimeTimerService(5.0));
		SmartLightSystem.setTimerService(new ScaledTimeTimerService(5.0));
		SmartMicrowaveSystem.setTimerService(new ScaledTimeTimerService(5.0));
		
		//AUTONOMOUS UNIT TIMER SERVICE
		FireAlarm.setTimerService(new ScaledTimeTimerService(5.0));
		TV.setTimerService(new ScaledTimeTimerService(5.0));
		Lights.setTimerService(new ScaledTimeTimerService(5.0));
		Microwave.setTimerService(new ScaledTimeTimerService(5.0));
		
		//ATOMIC COMPONENTS TIMER SERVICE
		Actuator.setTimerService(new ScaledTimeTimerService(5.0));
		Controller.setTimerService(new ScaledTimeTimerService(5.0));
		Sensor.setTimerService(new ScaledTimeTimerService(5.0));
		Power.setTimerService(new ScaledTimeTimerService(5.0));
		Temperature.setTimerService(new ScaledTimeTimerService(5.0));
		WiFi.setTimerService(new ScaledTimeTimerService(5.0));
		
		/**
		 * THIS SECTION SETS EACH STATECHART VARIABLES TO ITS VALUE
		 * 
		 * WARNING: Without doing this, each variable declaration will default to NULL
		 * **/
		
		SmartHubSystem.setPowerManager(PowerManager);
		
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
		SmartHubSystem.getSF().setActuator(Actuator);
		SmartHubSystem.getSTV().setActuator(Actuator);
		SmartHubSystem.getSL().setActuator(Actuator);
		SmartHubSystem.getSMW().setActuator(Actuator);

		//Setting up CONTROLLER Component for each system
		SmartHubSystem.getSF().setController(Controller);
		SmartHubSystem.getSTV().setController(Controller);
		SmartHubSystem.getSL().setController(Controller);
		SmartHubSystem.getSMW().setController(Controller);

		//Setting up SENSOR Component for each system
		SmartHubSystem.getSF().setSensor(Sensor);
		SmartHubSystem.getSTV().setSensor(Sensor);
		SmartHubSystem.getSL().setSensor(Sensor);
		SmartHubSystem.getSMW().setSensor(Sensor);

		//Setting up POWER Component for each system
		SmartHubSystem.getSF().setPower(Power);
		SmartHubSystem.getSTV().setPower(Power);
		SmartHubSystem.getSL().setPower(Power);
		SmartHubSystem.getSMW().setPower(Power);

		//Setting up TEMPERATURE Component for each system
		SmartHubSystem.getSF().setTemp(Temperature);
		SmartHubSystem.getSTV().setTemp(Temperature);
		SmartHubSystem.getSL().setTemp(Temperature);
		SmartHubSystem.getSMW().setTemp(Temperature);

		//Setting up WIFI GATEWAY Component for each system
		SmartHubSystem.getSF().setWiFi(WiFi);
		SmartHubSystem.getSTV().setWiFi(WiFi);
		SmartHubSystem.getSL().setWiFi(WiFi);
		SmartHubSystem.getSMW().setWiFi(WiFi);
		
		//
		setState(JFrame.EXIT_ON_CLOSE);
		//new DashboardFrame_Editor().createContents();
    };
    
    //Simulate the statechart
	protected void run() {

		SmartHubSystem.enter();
		//System.out.println("Smart Hub System statechart is currently running (?): "+SmartHubSystem.isActive());
		//System.out.println("Power Manager System statechart is currently running (?): "+PowerManager.isActive());
		//System.out.println("Smart Fire System statechart is currently running (?): "+SmartFireSystem.isActive());
		//System.out.println("Smart TV System statechart is currently running (?): "+SmartTVSystem.isActive());
		//System.out.println("Smart Lights System statechart is currently running (?): "+SmartLightSystem.isActive());
		//System.out.println("Smart Microwave System statechart is currently running (?): "+SmartMicrowaveSystem.isActive());
		

		SmartHubSystem.getSF().getFireAlarm().sensors().setCarbon_increment(r.nextLong(10));
		SmartHubSystem.getSF().getFireAlarm().sensors().setSmoke_increment(r.nextLong(10));
		

		SmartHubSystem.getSTV().getSensor().setDetection_value(10000);
		SmartHubSystem.getSL().getSensor().setDetection_value(10000);
		

		SmartHubSystem.hUB().raiseTurnONSystems();
		
		//CONTINUOUS DATA READING FROM THE STATECHARTS
		class refresh extends TimerTask {
		    public void run() {
		    	Thread anti_freeze = new Thread(new Runnable() {

					@Override
					public void run() {
				    	runtimePing();
				    	readStatechartData(SmartHubSystem); //Refresh Values
					}
				});
				anti_freeze.start();
		    }
		}

		// And From your main() method or any other method
		Timer timer = new Timer();
		timer.schedule(new refresh(), 0, 310);

		//BUTTON HANDLERS
		ButtonClicksHandler(SmartHubSystem);

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
		readNotificationBarDATA(SmartHubSystem);
		readSmartFireSystemDATA(SmartHubSystem);
		readSmartTVSystemDATA(SmartHubSystem);
		readSmartLightSystemDATA(SmartHubSystem);
		readSmartMicrowaveSystemDATA(SmartHubSystem);
		
		//Getting Smart System Activity
		allsystem_status = SmartHubSystem.getAllSystemsOn();
		SF_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM1REGION_SMARTFIRE_STATUS_SMARTFIRESTATUS_ON);
		STV_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM1_STV_ON);
		SL_status= SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM2_SL_ON);
		SMW_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_SYSTEM_MANAGER_SYSTEM3_SMW_ON);
		
		//SENSOR TIMEOUTS
		SmartHubSystem.getSF().getSensor().setDetection_value(r.nextInt(100000,100000000));
		SmartHubSystem.getSF().getFireAlarm().sensors().setCarbon_increment(r.nextInt(10,100));
		SmartHubSystem.getSF().getFireAlarm().sensors().setSmoke_increment(r.nextInt(10,100));
		//WIFI TIMEOUTS
		SmartHubSystem.getSF().getWiFi().setTimeout_value(r.nextInt(0,20));
		SmartHubSystem.getSTV().getWiFi().setTimeout_value(r.nextInt(0,20));
		SmartHubSystem.getSL().getWiFi().setTimeout_value(r.nextInt(0,20));
		SmartHubSystem.getSMW().getWiFi().setTimeout_value(r.nextInt(0,20));
		
		boolean hubState = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON),
				emergencyState = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SMARTHUBREGION_SMARTHUBSYSTEMSTATUS_HUBSTATUSREGION_EMERGENCY_STATE);
		
		//EMERGENCY STATE HANDLER
		if(emergencyState) {
			buttons[4].setText("EMERGENCY STATE IS ACTIVE");
	        smartFire_switch.setText("Fire Alarm: ON");
		}
		
		//POWER MANAGER
		SmartHubSystem.getPowerManager().setThreshold(Power_Manager_Threshold);
		
		double total_HUB_power = (double)(SmartHubSystem.power().getTotal())/(double)(SmartHubSystem.getPowerManager().getThreshold())*100;
		buttons[0].setText("Threshold Consumption Level (MAX: "+Power_Manager_Threshold+" kWh): "+df.format(total_HUB_power) + " %");
		
		
		
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
			}else if(!SF_connection) {
				connection1 = "Not Connected";
			}
			//STV
			boolean STV_connection = SmartHubSystem.getSTV().getWiFi_connection();
			String connection2 = "";
			if(STV_connection) {
				connection2 = "Connected";
			}else if(!STV_connection) {
				connection2 = "Not Connected";
			}
			//SMARTLIGHT
			boolean SL_connection = SmartHubSystem.getSL().getWiFi_connection();
			String connection3 = "";
			if(SL_connection) {
				connection3 = "Connected";
			}else if(!SL_connection) {
				connection3 = "Not Connected";
			}
			//SMARTMICROWAVE
			boolean SMW_connection = SmartHubSystem.getSMW().getWiFi_connection();
			String connection4 = "";
			if(SMW_connection) {
				connection4 = "Connected";
			}else if(!SMW_connection) {
				connection4 = "Not Connected";
			}
	        notifsText1.setText("System 1 Server Connection Status: "+connection1);
	        notifsText2.setText("System 2 Server Connection Status: "+connection2);
	        notifsText3.setText("System 3 Server Connection Status: "+connection3);
	        notifsText4.setText("System 4 Server Connection Status: "+connection4);
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
			int current_Power = (int)SmartHubSystem.power().getTotal();
			
	        notifsText1.setText("Smart Fire System: " + df.format(((double)SF_Power_kWh/(double)current_Power)*100)+" %");
			if(TV_status)
				notifsText2.setText("Smart TV System: " + df.format(((double)STV_Power_kWh/(double)current_Power)*100)+" %");
			else
				notifsText2.setText("Smart TV System: " + df.format(((double)0/(double)current_Power)*100)+" %");

			if(Light_status)
				notifsText3.setText("Smart Lights System:  "+ df.format(((double)SL_Power_kWh/(double)current_Power)*100)+" %");
			else
				notifsText3.setText("Smart Lights System: " + df.format(((double)0/(double)current_Power)*100)+" %");

			if(Microwave_status)
				notifsText4.setText("Smart Microwave System: " + df.format(((double)SMW_Power_kWh/(double)current_Power)*100)+" %");
			else
				notifsText4.setText("Smart Microwave System: " + df.format(((double)0/(double)current_Power)*100)+" %");
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
        if(SmartHubSystem.getSF().getFireAlarm().getWarned()){
			smartFire_message.setBackground(Color.orange.brighter());
        }
        
		//POWER COMPONENT
		SmartHubSystem.smartFire().setKWh(SF_Power_kWh);
		SmartHubSystem.getSF().getPower().setHour(1);
		String SF_kWh = " " + SmartHubSystem.getSF().getPower_input();
        
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSF().getWiFi_connection();
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
        smartFirePanel.setBorder(
        		BorderFactory.createTitledBorder("SYSTEM 1: Smart Fire System ("+connection+") - "+SF_kWh+" kWh"));
        
        //CARBON AND SMOKE LEVELS
        String smoke_value = " "+ SmartHubSystem.getSF().getFireAlarm().sensors().getSmoke_value();
        smokeDetected.setText("Smoke Sensor Level: "+ smoke_value +" %");
        String carbon_value = " "+ SmartHubSystem.getSF().getFireAlarm().sensors().getCarbon_value();
        carbonDetected.setText("Carbon Sensor Level: "+ carbon_value +" %");
        
        //FIRE ALARM SOUNDING STATUS
		if(fireAlarm_status) {
			smartFire_message.setBackground(Color.RED);
	        buttons[4].setText("Smart Fire System has an emergency alert!");
	        smartFire_switch.setText("Fire Alarm: ON");
	        SmartHubSystem.hUB().raiseTurnOFFSystems();

		}
		else if(!fireAlarm_status){
			smartFire_message.setBackground(Color.GREEN.brighter());
			smartFirePanel.setBackground(Color.WHITE.brighter());
	        smartFire_switch.setText("Fire Alarm: OFF");
		}
		//FIRE ALARM SENSORS TRIGGERED
		if(fireAlarm_sensors_triggered) {
			smartFire_sensors.setText("Sensors Status: Trigger Signal Received!\nTimer: "+SmartHubSystem.getSF().getFireAlarm().timer().getCounter());
		}
		else {
			smartFire_sensors.setText("Sensors Status: Monitoring...");
		}
		
	}
	
	
	/**
	 SMART TV SYSTEM DATA MANAGEMENT PANEL
	 * **/
	
	public void readSmartTVSystemDATA(SmartHubSystem SmartHubSystem) {
		
		TV_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM2REGION_SMARTTV_STATUS_SMARTTVSTATUS_ON);
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
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
       smartTVPanel.setBorder(
       		BorderFactory.createTitledBorder("SYSTEM 2: Smart TV System ("+connection+") - "+STV_kWh+" kWh"));
       
	}
	
	/**
	 SMART LIGHTS SYSTEM DATA MANAGEMENT PANEL
	 * **/
	
	public void readSmartLightSystemDATA(SmartHubSystem SmartHubSystem) {
		
		Light_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM3REGION_SMARTLIGHT_STATUS_SMARTLIGHTSTATUS_ON);
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
		}else if(!Light_status){
			system_status = "Light System Status: OFF";
			smartLightPanel.setBackground(Color.gray.brighter());
			smartLight_use.setEnabled(false);
		}

       smartLight_switch.setText(system_status);
      
		//POWER COMPONENT
		SmartHubSystem.smartLight().setKWh(SL_Power_kWh);
		SmartHubSystem.getSL().getPower().setHour(1);
		String SL_kWh = " " + SmartHubSystem.smartLight().getKWh();
      
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSL().getWiFi_connection();
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
      smartLightPanel.setBorder(
      		BorderFactory.createTitledBorder("SYSTEM 3: Smart Lights System ("+connection+") - "+SL_kWh+" kWh"));
      
	}
	
	/**
	 SMART MICROWAVE SYSTEM DATA MANAGEMENT PANEL
	 * **/
	
	public void readSmartMicrowaveSystemDATA(SmartHubSystem SmartHubSystem) {
		
		Microwave_status = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM4REGION_SMARTMICROWAVE_STATUS_SMARTMICROWAVESTATUS_ON);
		Microwave_usage = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM4REGION_SMARTMICROWAVE_STATUS_SMARTMICROWAVESTATUS_ON_SENSORSTATUS_ACTIVE);
		String usage_status = "", system_status = "";
		
		//Lights Usage Status
		if(Microwave_status) {
			if(Microwave_usage)
				usage_status = "Microwave Usage Status: IN USE";
			else if(!Microwave_usage)
				usage_status = "Microwave Usage Status: NOT IN USE";
			
			system_status = "Microwave System Status: ON";

			smartMicrowave_use.setEnabled(true);
			smartMicrowave_use.setText(usage_status);
			smartMicrowavePanel.setBackground(Color.green);
		}else if(!Microwave_status){
			system_status = "Microwave System Status: OFF";
			smartMicrowavePanel.setBackground(Color.gray.brighter());
			smartMicrowave_use.setEnabled(false);
		}

      smartMicrowave_switch.setText(system_status);
     
		//POWER COMPONENT
		SmartHubSystem.smartMicrowave().setKWh(SMW_Power_kWh);
		SmartHubSystem.getSMW().getPower().setHour(1);
		String SMW_kWh = " " + SmartHubSystem.smartMicrowave().getKWh();
     
		
		//WIFI COMPONENT
		boolean WiFi_connection = SmartHubSystem.getSMW().getWiFi_connection();
		String connection = "";
		if(WiFi_connection) {
			connection = "Connected";
		}else if(!WiFi_connection) {
			connection = "Not Connected";
		}
		
		//PANEL TITLE
		smartMicrowavePanel.setBorder(
     		BorderFactory.createTitledBorder("SYSTEM 4: Smart Microwave System ("+connection+") - "+SMW_kWh+" kWh"));
     
	}
	
	/**
	 BUTTON CLICKS HANDLER
	 * **/
	
	public void ButtonClicksHandler(SmartHubSystem SmartHubSystem) {
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
        //Exit Hub
        buttons[3].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				SmartHubSystem.raiseToggle();
				//System.exit(0);
			}
        	
        });
        
        //HUB WiFi
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
							//SmartHubSystem.getSF().getSensor().setActivity(false);
					        smartFire_switch.setText("Fire Alarm: OFF");
						}
						else if(!fireAlarm_status){ //to turn on
							//smartFirePanel.setBackground(Color.RED.brighter());
							SmartHubSystem.getSF().getFireAlarm().mode().raiseDanger();
					        smartFire_switch.setText("Fire Alarm: ON");
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
						Microwave_usage = SmartHubSystem.isStateActive(State._SMARTHUBSYSTEM__HUBON_SYSTEM4REGION_SMARTMICROWAVE_STATUS_SMARTMICROWAVESTATUS_ON_SENSORSTATUS_ACTIVE);
						
						if(!Microwave_usage)
							SmartHubSystem.getSMW().getSensor().raiseDetect();
					}
				});
				anti_freeze.start();
			}
        });
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
