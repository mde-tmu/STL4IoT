package smarthub.ui;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class DashboardFrame_Editor extends JFrame{

	/**
	 *
	 */
	
	public int notifsView_value = 0;
	public String[] notifsView_Title = {"(1/4): System Status", "(2/4): WiFi Status", "(3/4): Usage Status", "(4/4): System Power Contribution"};
    public static boolean allsystem_status = false,
					SF_status = false,
					STV_status = false,
					SL_status = false,
					SMW_status = false;

    public static JFrame frame;
    public static JPanel mainPanel, notifsPanel;
	public static JPanel testerPanel;
	public static JPanel smartFirePanel;
	public static JPanel smartTVPanel;
	public static JPanel smartLightPanel;
	public static JPanel smartMicrowavePanel;
    
    public static JButton smartFire_switch,
    				smartTV_switch, smartTV_use, smartTV_inputSource,
    				smartLight_switch, smartLight_use, smartLight_brightness,
    				smartMicrowave_switch, smartMicrowave_use, smartMicrowave_addTimer, smartMicrowave_resetTimer, smartMicrowave_startTimer, smartMicrowave_doorStatus,
    				notifsViewButton,
    				testButton1, testButton2, testButton3, testButton4, testButton5, testButton6, testButton7, testButton8,
    				exitButton, saveReportButton;
    
	public static JTextPane smartFire_message, smartFire_systemStatus, smartFire_carbonSensor, smartFire_smokeSensor, smartFire_heatSensor,
						smartTV_message, smartTV_heat,
						smartLight_message, smartLight_heat,
						smartMicrowave_message, smartMicrowave_heat,
						notifsText1, notifsText2, notifsText3, notifsText4,
						exitPanelText, exitPanelText2,exitPanelText3;
    
    public String sf_switch, sf_message, sf_sensors,
    				stv_switch, stv_message, stv_heat,
    				smw_switch, smw_message, smw_heat;
    
    public long stv_temp, sl_temp, smw_temp;
	
	private static final long serialVersionUID = 1L;
	
	
    final int hGap = 5;
    private final int vGap = 5;
    
    public JButton switchBtn;
    public JButton openBtn;
    
    public String MainDashBtn[] = {"0%","Turn ON Smart Hub","Turn ON all Systems", "WiFi: Connected", ""};

    
    
    private String[] borderConstraints = {
        BorderLayout.PAGE_START,
        BorderLayout.LINE_START,
        BorderLayout.CENTER,
        BorderLayout.LINE_END,
        BorderLayout.PAGE_END
    };

    public static JButton[] buttons;

    private GridBagConstraints gbc;
 

	public Object contentPane;

	//Initialize the JFrame Listener
	protected void init() {
		
		this.addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});		
		this.createContents();
	}
	
    public DashboardFrame_Editor() {
        buttons = new JButton[16];
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;   
        gbc.insets = new Insets(hGap, vGap, hGap, vGap);
    }

    public void createContents() {

        frame = new JFrame("Smart Hub System v2.0");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel(
                        new GridLayout(0, 3, hGap, hGap));
        contentPane.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
        mainPanel = new JPanel(new BorderLayout(hGap, vGap));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Smart Hub System v2.0"));
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        
        //MainDashBtn[0] = bootSmartHub.message;
        
        for (int i = 0; i < 5; i++) {
            buttons[i] = new JButton(MainDashBtn[i]);
            mainPanel.add(buttons[i],borderConstraints[i]);
        }
        
        /*1*/contentPane.add(mainPanel);

        /*********************/
        /*2*/contentPane.add(createNotificationPanel());
        contentPane.add(DataPlotter.createChartPanel()); //Chart for Consumption Level
        /*3*///contentPane.add(createTesterPanel1());
        /*4*/contentPane.add(createFireAlarmPanel());
        /*5*/contentPane.add(createTVPanel());
        contentPane.add(DataPlotter.creatDialPlot()); //Chart for Consumption Level
        /*6*///contentPane.add(createTesterPanel2());
        /*7*/contentPane.add(createLightPanel());
        /*8*/contentPane.add(createMicrowavePanel());
        /*9*/contentPane.add(createExitPanel());

        
        
        
        //DataPlotter.runPlotter(contentPane);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    public Component createNotificationPanel() {
        
    	notifsPanel = new JPanel(new GridLayout(5, 1, hGap, vGap));

    	notifsPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));

    	notifsPanel.setBorder(BorderFactory.createTitledBorder("System Status Tab: "));
    	notifsPanel.setOpaque(true);
    	notifsPanel.setBackground(Color.white.brighter());
    	
    	notifsViewButton = new JButton();
        notifsText1 = new JTextPane();
        notifsText2 = new JTextPane();
        notifsText3 = new JTextPane();
        notifsText4 = new JTextPane();
        
        notifsViewButton.setText(notifsView_Title[notifsView_value]);
        
        notifsPanel.add(notifsViewButton);
        notifsPanel.add(notifsText1);
        notifsPanel.add(notifsText2);
        notifsPanel.add(notifsText3);
        notifsPanel.add(notifsText4);
        
        return notifsPanel;
    }
    
    /*
     * ******************************
     */
    //Smart Fire Panel
    public Component createFireAlarmPanel() {
        // Smart Fire
        smartFirePanel = new JPanel(new GridLayout(0, 1, hGap, vGap));
        
        smartFirePanel.setOpaque(true);
        smartFirePanel.setBackground(Color.GRAY.brighter());
        
        smartFire_switch = new JButton();
        smartFire_message = new JTextPane();
        smartFire_systemStatus = new JTextPane();
        smartFire_smokeSensor = new JTextPane();
        smartFire_carbonSensor = new JTextPane();
        smartFire_heatSensor = new JTextPane();
        

        smartFirePanel.add(smartFire_switch);
        smartFirePanel.add(smartFire_message);
        smartFirePanel.add(smartFire_systemStatus);
        smartFirePanel.add(smartFire_smokeSensor);
        smartFirePanel.add(smartFire_carbonSensor);
        smartFirePanel.add(smartFire_heatSensor);
        
        return smartFirePanel;
    }
    
    //Smart TV Panel
    public Component createTVPanel() {
        /***********/
        
        smartTVPanel = new JPanel(new GridLayout(3, 1, hGap, vGap));
        smartTVPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
        
        smartTVPanel.setOpaque(true);
        smartTVPanel.setBackground(Color.gray.brighter());
        //BUTTONS
        smartTV_inputSource = new JButton();
        smartTV_switch = new JButton();
        smartTV_use = new JButton();
        smartTV_use.setText("TV Usage Status: NOT IN USE");
        //ADD TO PANEL
        smartTVPanel.add(smartTV_switch);
        smartTVPanel.add(smartTV_use);
        smartTVPanel.add(smartTV_inputSource);
		
		return smartTVPanel;
    }
    
    //Smart Light Panel
    public Component createLightPanel() {

    	smartLightPanel = new JPanel(new GridLayout(0, 1, hGap, vGap));
        smartLightPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
        smartLightPanel.setOpaque(true);
        smartLightPanel.setBackground(Color.gray.brighter());
        //BUTTONS
        smartLight_switch = new JButton();
        smartLight_use = new JButton();
        smartLight_brightness = new JButton();
        smartLight_use.setText("Light Usage Status: NOT IN USE");
        smartLight_brightness.setText("Brightness Level: 0");
        //ADD TO PANEL
        smartLightPanel.add(smartLight_switch);
        smartLightPanel.add(smartLight_use);
        smartLightPanel.add(smartLight_brightness);
		
		return smartLightPanel;
    }
    
    //Smart Microwave Panel
    public Component createMicrowavePanel() {
        /***********/
    	smartMicrowavePanel = new JPanel(new GridLayout(3, 2, hGap, vGap));
    	smartMicrowavePanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
    	smartMicrowavePanel.setOpaque(true);
    	smartMicrowavePanel.setBackground(Color.gray.brighter());
        //BUTTONS
        smartMicrowave_switch = new JButton();
        smartMicrowave_use = new JButton();
        smartMicrowave_addTimer = new JButton();
        smartMicrowave_resetTimer = new JButton();
        smartMicrowave_startTimer = new JButton();
        smartMicrowave_doorStatus = new JButton();
        
        smartMicrowave_use.setText("Microwave Usage Status: NOT IN USE");
        //ADD TO PANEL
        smartMicrowavePanel.add(smartMicrowave_switch);
        smartMicrowavePanel.add(smartMicrowave_use);
        smartMicrowavePanel.add(smartMicrowave_addTimer);
        smartMicrowavePanel.add(smartMicrowave_resetTimer);
        smartMicrowavePanel.add(smartMicrowave_startTimer);
        smartMicrowavePanel.add(smartMicrowave_doorStatus);
		
		return smartMicrowavePanel;
    }
    
    //Testing Panel
    public Component createTesterPanel1() {
        /***********/
    	testerPanel = new JPanel(new GridLayout(4, 0, hGap, vGap));
    	testerPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));

    	testerPanel.setBorder(BorderFactory.createTitledBorder("Smart Fire Test Panel"));
    	testerPanel.setOpaque(true);
    	testerPanel.setBackground(Color.gray.brighter());
        //BUTTONS
        testButton1 = new JButton();
        testButton2 = new JButton();
        testButton3 = new JButton();
        testButton4 = new JButton();
        testButton1.setText("Test Smoke Sensor");
        testButton2.setText("Test Carbon Sensor");
        testButton3.setText("Test Heat Sensor");
        testButton4.setText("Sound Fire Alarm");
        //ADD TO PANEL
        testerPanel.add(testButton1);
        testerPanel.add(testButton2);
        testerPanel.add(testButton3);
        testerPanel.add(testButton4);
        
		
		return testerPanel;
    }
    public Component createTesterPanel2() {
       
    	testerPanel = new JPanel(new GridLayout(4, 0, hGap, vGap));
    	testerPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));

    	testerPanel.setBorder(BorderFactory.createTitledBorder("Usage Test Panel"));
    	testerPanel.setOpaque(true);
    	testerPanel.setBackground(Color.gray.brighter());
        
        //BUTTONS
        testButton5 = new JButton();
        testButton6 = new JButton();
        testButton7 = new JButton();
        testButton8 = new JButton();
        testButton5.setText("Test TV Activity Sensor");
        testButton6.setText("Test Lights Motion Sensor");
        testButton7.setText("Test Microwave Food Sensor");
        testButton8.setText("");
        //ADD TO PANEL
        testerPanel.add(testButton5);
        testerPanel.add(testButton6);
        testerPanel.add(testButton7);
        testerPanel.add(testButton8);
		
		return testerPanel;
    }
    public Component createExitPanel() {
        /***********/
    	testerPanel = new JPanel(new GridLayout(5, 1, hGap, vGap));
    	testerPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));

    	testerPanel.setBorder(BorderFactory.createTitledBorder("Logging Report"));
    	testerPanel.setOpaque(true);
    	testerPanel.setBackground(Color.white.brighter());
        //BUTTONS
    	exitButton = new JButton();
    	saveReportButton = new JButton();
    	
    	exitPanelText = new JTextPane();
    	exitPanelText2 = new JTextPane();
    	exitPanelText3 = new JTextPane();
    	
        exitButton.setText("Exit Smart Hub");
        saveReportButton.setText("Save Report Log");
        exitPanelText.setText("REPORT WILL GO HERE");
        //ADD TO PANEL
        testerPanel.add(exitPanelText);
        testerPanel.add(exitPanelText2);
        testerPanel.add(exitPanelText3);
        testerPanel.add(exitButton);
        testerPanel.add(saveReportButton);
		
		return testerPanel;
    }
    
    
}