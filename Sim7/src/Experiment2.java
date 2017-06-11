import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import nQueues.VendingMachineModelScenario2;
import singleQueue.Customer;
import singleQueue.VendingMachineModelScenario1;
import switchingQueue.VendingMachineModelScenario3;
/**
 * Multiple run experiment for the 3 scenarios with varying number of machine
 * @author Oliver Remy, Max Göttl, Dennis Strähhuber
 */
public class Experiment2 {
	
	static ArrayList<Customer> scenario1;
	static ArrayList<Customer> scenario2;
	static ArrayList<Customer> scenario3;
	
	static XYSeries series1AVG = new XYSeries("Scenario1AVG");
	static XYSeries series1MAX = new XYSeries("Scenario1MAX");
	
	static XYSeries series2AVG = new XYSeries("Scenario2AVG");
	static XYSeries series2MAX = new XYSeries("Scenario2MAX");
	
	static XYSeries series3AVG = new XYSeries("Scenario3AVG");
	static XYSeries series3MAX = new XYSeries("Scenario3MAX");
	
	static long arrivalSeed = 0;
	static long durationSeed = 0;
	static long queueSeed = 0;
	
	public static void main(String[] args){
		
		XYSeriesCollection dataset1 = new XYSeriesCollection();
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		
		double lowerBoundCustomerDuration = 0.5;
		double upperBoundCustomerDuration = 10.0;
		double arrivalTimeInterval = 2.8;
		
		for(int i=2; i<= 8; i+=1){
			doExperiments(i, lowerBoundCustomerDuration, upperBoundCustomerDuration, arrivalTimeInterval);
		}
		
		dataset1.addSeries(series1AVG);
		dataset2.addSeries(series1MAX);
		dataset1.addSeries(series2AVG);
		dataset2.addSeries(series2MAX);
		dataset1.addSeries(series3AVG);
		dataset2.addSeries(series3MAX);
		
		JFreeChart chart1 = ChartFactory.createXYLineChart(
				"AVG Customer Durations", 	// name
				"Machines", 				// xAxis
				"Duration in Minutes", 		// yAxis
				dataset1					// dataset
				);
		
		JFreeChart chart2 = ChartFactory.createXYLineChart(
				"MAX Customer Duration", 	// name
				"Machines",					// xAxis
				"Duration in Minutes",		// yAxis
				dataset2					// dataset
				);
		
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...


        // get a reference to the plot for further customisation...
        XYPlot plot = chart1.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits(Locale.GERMANY));
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits(Locale.GERMANY));
        
        plot.getRenderer().setSeriesStroke(2, new BasicStroke(3.0f));
        
        plot = chart2.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        // set the range axis to display integers only...
        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits(Locale.GERMANY));

        domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits(Locale.GERMANY));
        
        plot.getRenderer().setSeriesStroke(2, new BasicStroke(3.0f));
     
       
		File chart1File = new File("plots/Output_Chart1_AVG.png");
		File chart2File = new File("plots/Output_Chart2_MAX.png");
		try {
			ChartUtilities.saveChartAsPNG(chart1File, chart1, 640, 480);
			ChartUtilities.saveChartAsPNG(chart2File, chart2, 640, 480);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void doExperiments(int numOfMachines, double lowerBoundCustomerDuration, double upperBoundCustomerDuration, double arrivalTimeInterval) {

		// scenario1
		
		Experiment vendingMachineExperiment = new Experiment("VendingMachine-Experiment-singleQueue");
		Model vendingModel;
		vendingModel = new VendingMachineModelScenario1(null, "VendingMachine-Model", true, true, numOfMachines, lowerBoundCustomerDuration, upperBoundCustomerDuration, arrivalTimeInterval);
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(240));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(240));
		
		if(arrivalSeed == 0)
			arrivalSeed = ((VendingMachineModelScenario1) vendingModel).getArrivalSeed();
		else
			((VendingMachineModelScenario1) vendingModel).setArrivalSeed(arrivalSeed);
		if(durationSeed == 0)
			durationSeed = ((VendingMachineModelScenario1) vendingModel).getDurationSeed();
		else
			((VendingMachineModelScenario1) vendingModel).setDurationSeed(durationSeed);
		 
		vendingMachineExperiment.stop(new TimeInstant(240));
		vendingMachineExperiment.start();
		vendingMachineExperiment.report();
		vendingMachineExperiment.finish();
		
		scenario1 = ((VendingMachineModelScenario1) vendingModel).getCustomerList();
		
		// scenario 2
		
		vendingMachineExperiment = new Experiment("VendingMachine-Experiment-nQueues");
		
		vendingModel = new VendingMachineModelScenario2(null, "VendingMachine-Model", true, true, numOfMachines, lowerBoundCustomerDuration, upperBoundCustomerDuration, arrivalTimeInterval);
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(240));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(240));
		
		((VendingMachineModelScenario2) vendingModel).setArrivalSeed(arrivalSeed);
		((VendingMachineModelScenario2) vendingModel).setDurationSeed(durationSeed);
		if(queueSeed == 0)
			queueSeed = ((VendingMachineModelScenario2) vendingModel).getQueueSeed();
		else
			((VendingMachineModelScenario2) vendingModel).setQueueSeed(queueSeed);
		
		vendingMachineExperiment.stop(new TimeInstant(240));
		vendingMachineExperiment.start();
		vendingMachineExperiment.report();
		vendingMachineExperiment.finish();
		
		scenario2 = ((VendingMachineModelScenario2) vendingModel).getCustomerList();
		
		
		// scenario 3
		
		vendingMachineExperiment = new Experiment("VendingMachine-Experiment-switchingQueue");
		vendingModel = new VendingMachineModelScenario3(null, "VendingMachine-Model", true, true, numOfMachines, lowerBoundCustomerDuration, upperBoundCustomerDuration, arrivalTimeInterval);
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(240));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(240));
		
		((VendingMachineModelScenario3) vendingModel).setArrivalSeed(arrivalSeed);
		((VendingMachineModelScenario3) vendingModel).setDurationSeed(durationSeed);
		((VendingMachineModelScenario3) vendingModel).setQueueSeed(queueSeed);
		
		vendingMachineExperiment.stop(new TimeInstant(240));
		vendingMachineExperiment.start();
		vendingMachineExperiment.report();
		vendingMachineExperiment.finish();
		
		scenario3 = ((VendingMachineModelScenario3) vendingModel).getCustomerList();
		
		// Plot Data
		
		double scen1Avg = 0.0;
		double scen2Avg = 0.0;
		double scen3Avg = 0.0;
		
		double scen1Max = 0.0;
		double scen2Max = 0.0;
		double scen3Max = 0.0;
		
		for(int i=0; i<scenario1.size(); i++){
			double duration;
			// scenario1
			duration = scenario1.get(i).getDuration();
			scen1Avg += duration;
			if(duration > scen1Max)
				scen1Max = duration;
			// scenario2
			if(i < scenario2.size()) {
				duration = scenario2.get(i).getDuration();
				scen2Avg += duration;
				if(duration > scen2Max)
					scen2Max = duration;
			}
			// scenario3
			if(i < scenario3.size()) {
				duration = scenario3.get(i).getDuration();
				scen3Avg += duration;
				if(duration > scen3Max) 
					scen3Max = duration;
			}
		}
		
		scen1Avg /= scenario1.size();
		scen2Avg /= scenario2.size();
		scen3Avg /= scenario3.size();
		
		series1AVG.add(numOfMachines, scen1Avg);
		series1MAX.add(numOfMachines, scen1Max);
		
		series2AVG.add(numOfMachines, scen2Avg);
		series2MAX.add(numOfMachines, scen2Max);
		
		series3AVG.add(numOfMachines, scen3Avg);
		series3MAX.add(numOfMachines, scen3Max);
	}
}

