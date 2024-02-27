package smarthub.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import smarthub.java.SmartHubSystem;

import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.Layer;
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
       Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: "+PieChartReport);
   }
   //PIE CHART FOR THE POWER CONSUMPTION
   private static PieDataset<String> createDataset( ) {
	   PieDataset.setValue( "Smart Fire System" , 0 );  
	   PieDataset.setValue( "Smart TV System" , 0 );   
	   PieDataset.setValue( "Smart Microwave System" , 0 ); 
	   PieDataset.setValue( "Smart Lights System" , 0 );  
      return PieDataset;    
   }
   //CREATING PIE CHART FOR POWER DISTRIBUTION
   private static JFreeChart createPie( PieDataset<?> dataset ) {
	   piechart = ChartFactory.createPieChart(      
         "Power Consumption Levels",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         true);

      return piechart;
   }
   
   //CREATING POWER CHART
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

   public static ChartPanel createDialPlot() {

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
   
   public static ChartPanel smartFirePanel;
   
   public static ChartPanel createBarGraph() {


       CategoryDataset dataset = FireAlarmDataset(sensor1_val, sensor2_val, sensor3_val);
       JFreeChart chart = createChart(dataset);
       smartFirePanel = new ChartPanel(chart);
       smartFirePanel.setBorder(BorderFactory.createTitledBorder("System 1: Smart Fire System (Always ON)"));
   		//			smartFire_switch.setEnabled(false);
    		   
       smartFirePanel.setPreferredSize(new Dimension(500, 270));
       Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: Bar Graph Generated.\n");
	   
       return smartFirePanel;

   }
   
   public static double sensor1_val, sensor2_val, sensor3_val;
   public static DefaultCategoryDataset dataset;
   public static String sensor1, sensor2, sensor3;
   public static final String ROW_KEY = "Values";
   
   public static CategoryDataset FireAlarmDataset(double sensor1_val, double sensor2_val, double sensor3_val) {
	   	

       // row keys...
       sensor1 = "Carbon Sensor";
       sensor2 = "Smoke Sensor";
       sensor3 = "Heat Sensor";

       // create the dataset...
       dataset = new DefaultCategoryDataset();
       
       dataset.setValue(sensor1_val, sensor1, "Carbon");
       dataset.setValue(sensor2_val, sensor2, "Smoke");
       dataset.setValue(sensor3_val, sensor3, "Heat");
       
       return dataset;
       
   }
   //JFREECHART FOR BAR GRAPH
   public static GradientPaint gp0, gp1, gp2;
   public static Color safe = Color.green, warning = Color.yellow, danger = Color.red, white = Color.white;
   public static BarRenderer renderer;
   public static JFreeChart createChart(final CategoryDataset dataset) {
       
       // create the chart...
       final JFreeChart chart = ChartFactory.createBarChart(
           "Smart Fire System Data",         // chart title
           "Sensor Type",               // domain axis label
           "Levels (%)",                  // range axis label
           dataset,                  // data
           PlotOrientation.VERTICAL, // orientation
           true,                     // include legend
           true,                     // tooltips?
           false                     // URLs?
       );

       // SOME CUSTOMISATION OF THE CHART...

       // set the background color for the chart...
       chart.setBackgroundPaint(Color.white);

       // get a reference to the plot for further customisation...
       final CategoryPlot plot = chart.getCategoryPlot();
       plot.setBackgroundPaint(Color.lightGray);
       plot.setDomainGridlinePaint(Color.white);
       plot.setRangeGridlinePaint(Color.white);

       // set the range axis to display integers only...
       final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       rangeAxis.setRange(0.0, 100.0);
       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

       // disable bar outlines...
       renderer = (BarRenderer) plot.getRenderer();
       renderer.setDrawBarOutline(false);
       
       Color safe = Color.green,
    		 warning = Color.yellow,
    		 danger = Color.red;
       
       // set up gradient paints for series...
       gp0 = new GradientPaint(
           0.0f, 0.0f, safe, 
           0.0f, 0.0f, white
       );
       gp1 = new GradientPaint(
           0.0f, 0.0f, safe, 
           0.0f, 0.0f, white
       );
       gp2 = new GradientPaint(
           0.0f, 0.0f, safe, 
           0.0f, 0.0f, white
       );
       renderer.setSeriesPaint(0, gp0);
       renderer.setSeriesPaint(1, gp1);
       renderer.setSeriesPaint(2, gp2);

       final CategoryAxis domainAxis = plot.getDomainAxis();
       domainAxis.setCategoryLabelPositions(
           CategoryLabelPositions.createUpRotationLabelPositions(0)
       );
       
       // OPTIONAL CUSTOMISATION COMPLETED.
       Stroke stroke = new BasicStroke(1.5f);
       ValueMarker marker = new ValueMarker(90);  // position is the value on the axis
       marker.setPaint(Color.red);
       marker.setStroke(stroke);

       CategoryPlot threshold_line = chart.getCategoryPlot();
       threshold_line.addRangeMarker(marker,Layer.FOREGROUND);
       
       Stroke stroke2 = new BasicStroke(1.5f);
       ValueMarker marker2 = new ValueMarker(70);  // position is the value on the axis
       marker2.setPaint(Color.orange);
       marker2.setStroke(stroke2);

       CategoryPlot warning_line = chart.getCategoryPlot();
       warning_line.addRangeMarker(marker2,Layer.FOREGROUND);
       
       return chart;
       
   }
   public static void ChangeColour(double sensor1_val, double sensor2_val, double sensor3_val) {
	   if(sensor1_val < 70) {
			gp0 = new GradientPaint(
			           0.0f, 0.0f, safe, 
			           0.0f, 0.0f, white
			       );
		}
		else if(sensor1_val >= 70 && sensor1_val <90) {
			gp0 = new GradientPaint(
			           0.0f, 0.0f, warning, 
			           0.0f, 0.0f, white
			       );
		}
		else if(sensor1_val >= 90) {
			gp0 = new GradientPaint(
			           0.0f, 0.0f, danger, 
			           0.0f, 0.0f, white
			       );
		}
	   //FOR SMOKE SENSOR
	   if(sensor2_val < 70) {
			gp1 = new GradientPaint(
			           0.0f, 0.0f, safe, 
			           0.0f, 0.0f, white
			       );
		}
		else if(sensor2_val >= 70 && sensor2_val <90) {
			gp1 = new GradientPaint(
			           0.0f, 0.0f, warning, 
			           0.0f, 0.0f, white
			       );
		}
		else if(sensor2_val >= 90) {
			gp1 = new GradientPaint(
			           0.0f, 0.0f, danger, 
			           0.0f, 0.0f, white
			       );
		}
	   //FOR HEAT SENSOR
	   if(sensor3_val < 70) {
			gp2 = new GradientPaint(
			           0.0f, 0.0f, safe, 
			           0.0f, 0.0f, white
			       );
		}
		else if(sensor3_val >= 70 && sensor3_val <90) {
			gp2 = new GradientPaint(
			           0.0f, 0.0f, warning, 
			           0.0f, 0.0f, white
			       );
		}
		else if(sensor3_val >= 90) {
			gp2 = new GradientPaint(
			           0.0f, 0.0f, danger, 
			           0.0f, 0.0f, white
			       );
		}
	   
	   renderer.setSeriesPaint(0, gp0);
	   renderer.setSeriesPaint(1, gp1);
	   renderer.setSeriesPaint(2, gp2);
   }
   
}