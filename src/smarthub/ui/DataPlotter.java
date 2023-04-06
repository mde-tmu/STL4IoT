package smarthub.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;

import smarthub.java.SmartHubSystem;

import org.jfree.chart.ui.GradientPaintTransformType;
//import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RefineryUtilities;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
 
public class DataPlotter extends DashboardFrame_Editor {
  
	private static final long serialVersionUID = 1L;
	
	public static JFreeChart piechart, dialChart;
	
    //private final JFrame frame = new JFrame();
    public static DefaultPieDataset<String> PieDataset = new DefaultPieDataset<String>( );

	public static DefaultValueDataset DialDataset = new DefaultValueDataset(Statecharts_Initializer.total_HUB_power);
	
   public DataPlotter( String title ) {
	   runPlotter();
   }
   
   //GET DATA FROM STATECHART
   public static void PowerConsumptionDataFromStatechart(double total, double SF, double STV, double SMW, double SL) {
       if(SF_status)
   			DataPlotter.PieDataset.setValue( "Smart Fire System" , (double)SF/(double)total*100 );
       else
   			DataPlotter.PieDataset.setValue( "Smart Fire System" , -1);
       
       if(STV_status)
   			DataPlotter.PieDataset.setValue( "Smart TV System" , (double)STV/(double)total*100 );
       else
   			DataPlotter.PieDataset.setValue( "Smart TV System" , -1);

       if(SL_status)
			DataPlotter.PieDataset.setValue( "Smart Lights System" , (double)SL/(double)total*100 );
       else
			DataPlotter.PieDataset.setValue( "Smart Lights System" , -1 );

       if(SMW_status)
			DataPlotter.PieDataset.setValue( "Smart Microwave System" , (double)SMW/(double)total*100 );
       else
			DataPlotter.PieDataset.setValue( "Smart Microwave System" , -1 );
       
       ArrayList<String> PieChartReport = new ArrayList();
       
       PieChartReport.add("Power Distribution [SF, STV, SL, SMW] :"+"["+(double)SF/(double)total*100+", "
       +(double)STV/(double)total*100+", "
		+(double)SL/(double)total*100+", "
		+(double)SMW/(double)total*100+"]\n");
       //Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: "+PieChartReport);
   }
   //PIE CHART FOR THE POWER CONSUMPTION
   private static PieDataset<String> createDataset( ) {
	   PieDataset.setValue( "Smart Fire System" , 0 );  
	   PieDataset.setValue( "Smart TV System" , 0 );   
	   PieDataset.setValue( "Smart Microwave System" , 0 ); 
	   PieDataset.setValue( "Smart Lights System" , 0 );  
      return PieDataset;    
   }
   //CREATING PIE CHART
   private static JFreeChart createPie( PieDataset<?> dataset ) {
	   piechart = ChartFactory.createPieChart(      
         "Power Consumption Levels",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         true);

      return piechart;
   }
   
   public static JPanel createChartPanel( ) {
	   piechart = createPie(createDataset());

	   Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: Pie Chart Generated.\n");
	   
      return new ChartPanel(piechart);
   }

   public static void runPlotter() {
      DataPlotter powerDistributionLevels = new DataPlotter( "Power Distribution Level" );  
      powerDistributionLevels.setSize( 560 , 367 );
      RefineryUtilities.centerFrameOnScreen( powerDistributionLevels );    
      powerDistributionLevels.setVisible( true );
   }

   public static ChartPanel creatDialPlot() {

	   int minimumValue = 0;
	   int maximumValue = 100;
       int majorTickGap = 10;
       
       DialPlot plot = new DialPlot();
       plot.setDialFrame(new StandardDialFrame());
       plot.addLayer(new DialValueIndicator(0));
       plot.addLayer(new DialPointer.Pointer());

       DialTextAnnotation dialtextannotation = new DialTextAnnotation("TOTAL PERCENTAGE");
       dialtextannotation.setFont(new Font("Dialog", 1, 12));
       dialtextannotation.setPaint(Color.BLACK);
       dialtextannotation.setRadius(0.4d);
       plot.addLayer(dialtextannotation);
       
       StandardDialScale scale = new StandardDialScale(minimumValue, maximumValue, -120, -300, majorTickGap, majorTickGap - 1);
       scale.setTickRadius(0.88);
       scale.setTickLabelOffset(0.20);
       plot.addScale(0, scale);
       plot.setView(-0.3, 0, 1.6, 1);
       plot.setDataset(DialDataset);
       plot.setBackgroundPaint(Color.white);
       
       
	   Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: Dial Chart Generated.\n");
       return new ChartPanel(new JFreeChart(plot));
   }
}  