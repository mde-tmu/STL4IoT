package smarthub.ui;

import java.io.*;
import java.util.ArrayList; 

public class ReportLogSaver extends DashboardFrame_Editor {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void SaveReportLogToFile(ArrayList<String> LogReport) throws IOException {
			    FileWriter fileWriter = new FileWriter("ReportFile.txt");
			    PrintWriter printWriter = new PrintWriter(fileWriter);
			    
			    for(int i = 0; i<LogReport.size(); i++) {
				    printWriter.print(LogReport.get(i));
			    }
			    printWriter.close();
	}

}