package smarthub.ui;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class IP_Catcher extends DashboardFrame_Editor {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void Local() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            exitPanelText2.setText("HUB Host: "+ip);
            
            runtimePing();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
	public static void runtimePing(){

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
        Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: Application Ping Level: "+PING_VALUE+"...\n");
		
		
	}
}