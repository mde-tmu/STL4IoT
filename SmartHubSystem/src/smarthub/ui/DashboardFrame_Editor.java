package smarthub.ui;

import java.awt.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.awt.event.*;
import javax.swing.*;

public class DashboardFrame_Editor extends JFrame{

	/**
	 *
	 */
	
	public int notifsView_value = 0;
	public String[] notifsView_Title = {"(1/4): System Status", "(2/4): WiFi Status", "(3/4): Usage Status", "(4/4): Power Consumption"};
    public boolean allsystem_status = false,
					SF_status = false,
					STV_status = false,
					SL_status = false,
					SMW_status = false;

    public JFrame frame;
    public JPanel mainPanel, notifsPanel, smartFirePanel, smartTVPanel, smartLightPanel, smartMicrowavePanel;
    
    public JButton smartFire_switch,
    				smartTV_switch, smartLight_switch, smartMicrowave_switch,
    				smartTV_use, smartLight_use, smartMicrowave_use,
    				smartTV_inputSource,
    				notifsViewButton;
    
	public JTextPane smartFire_message, smartFire_sensors, smokeDetected, carbonDetected,
						smartTV_message, smartTV_heat,
						smartLight_message, smartLight_heat,
						smartMicrowave_message, smartMicrowave_heat,
						notifsText1, notifsText2, notifsText3, notifsText4;
    
    public String sf_switch, sf_message, sf_sensors,
    				stv_switch, stv_message, stv_heat,
    				smw_switch, smw_message, smw_heat;
    
    public long stv_temp, sl_temp, smw_temp;
	
	private static final long serialVersionUID = 1L;
	
	
    private final int hGap = 5;
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
                        new GridLayout(0, 2, hGap, hGap));
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
        
        contentPane.add(mainPanel);
        

        
        /*********************/
        contentPane.add(createNotificationPanel());
		contentPane.add(createFireAlarmPanel());
        contentPane.add(createTVPanel());
        contentPane.add(createLightPanel());
        contentPane.add(createMicrowavePanel());
        


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
        smokeDetected = new JTextPane();
        carbonDetected = new JTextPane();
        smartFire_message = new JTextPane();
        smartFire_sensors = new JTextPane();
        

        smartFirePanel.add(smartFire_switch);
        smartFirePanel.add(smartFire_message);
        smartFirePanel.add(smartFire_sensors);
        smartFirePanel.add(smokeDetected);
        smartFirePanel.add(carbonDetected);
        
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

    	smartLightPanel = new JPanel(new GridLayout(2, 1, hGap, vGap));
        smartLightPanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
        smartLightPanel.setOpaque(true);
        smartLightPanel.setBackground(Color.gray.brighter());
        //BUTTONS
        smartLight_switch = new JButton();
        smartLight_use = new JButton();
        smartLight_use.setText("Light Usage Status: NOT IN USE");
        //ADD TO PANEL
        smartLightPanel.add(smartLight_switch);
        smartLightPanel.add(smartLight_use);
		
		return smartLightPanel;
    }
    
    //Smart Microwave Panel
    public Component createMicrowavePanel() {
        /***********/
    	smartMicrowavePanel = new JPanel(new GridLayout(2, 1, hGap, vGap));
    	smartMicrowavePanel.setBorder(BorderFactory.createEmptyBorder(hGap, vGap, hGap, vGap));
    	smartMicrowavePanel.setOpaque(true);
    	smartMicrowavePanel.setBackground(Color.gray.brighter());
        //BUTTONS
        smartMicrowave_switch = new JButton();
        smartMicrowave_use = new JButton();
        smartMicrowave_use.setText("Microwave Usage Status: NOT IN USE");
        //ADD TO PANEL
        smartMicrowavePanel.add(smartMicrowave_switch);
        smartMicrowavePanel.add(smartMicrowave_use);
		
		return smartMicrowavePanel;
    }
    
    public void runtimePing() {
    	Thread anti_freeze = new Thread(new Runnable() {

			@Override
			public void run() {
				String ip = "localhost";
		        String time = "";
		        double PING_VALUE = 0;

		        //The command to execute
		        String pingCmd = "ping " + ip;

		        //get the runtime to execute the command
		        Runtime runtime = Runtime.getRuntime();
		        try {
		            Process process = runtime.exec(pingCmd);

		            //Gets the inputstream to read the output of the command
		            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

		            //reads the outputs
		            String inputLine = in.readLine();
		            while ((inputLine != null)) {
		                if (inputLine.length() > 0 && inputLine.contains("time")) {
		                     time = inputLine.substring(inputLine.indexOf("time"));
		                     break;                        
		                }
		                inputLine = in.readLine();
		            }    

		            //System.out.println(time);
		        } catch (Exception ex) {
		            System.out.println(ex);
		        }
		        String ping[] = time.split("=");
		        String ping2[] = ping[1].split(" ");
		        PING_VALUE = Double.parseDouble(ping2[0]);
		        buttons[4].setText("Smart Hub System Runtime Speed: "+PING_VALUE);
				
			}
		});
		anti_freeze.start();
        
    }

}