/**
 * 
 */
/**
 * @author clydejohn
 *
 */
package smarthub.ui;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SystemPingMonitor extends Statecharts_Initializer{
	private static final long serialVersionUID = -8909693541678814631L;
	public String time, ip, pingCmd;
	public Process process;
	public Runtime runtime;

    public void SystemRuntimePing(JPanel jpanel){
        ip = "localhost";
        time = "";
        //The command to execute
        pingCmd = "ping " + ip;

        //get the runtime to execute the command
        runtime = Runtime.getRuntime();
        try {
            process = runtime.exec(pingCmd);

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

        	notifsPanel.setBorder(BorderFactory.createTitledBorder("System Runtime: "+time));
            System.out.println("time --> " + time);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
