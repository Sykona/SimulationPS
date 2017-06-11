import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import nQueues.VendingMachineModelScenario2;
import singleQueue.Customer;
import singleQueue.VendingMachineModelScenario1;
import switchingQueue.VendingMachineModelScenario3;

public class Experiment1 {

	
	static ArrayList<Customer> scenario1;
	static ArrayList<Customer> scenario2;
	static ArrayList<Customer> scenario3;
	
	public static void main(String[] args){
		
		// row keys...
		final String series1 = "Scenario 1";
		final String series2 = "Scenario 2";
		final String series3 = "Scenario 3";
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		int numberOfMachines = 2;

		doExperiments(numberOfMachines, 0.5, 10.0, 3);
		
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
			duration = scenario2.get(i).getDuration();
			scen2Avg += duration;
			if(duration > scen2Max)
				scen2Max = duration;
			// scenario3
			duration = scenario3.get(i).getDuration();
			scen3Avg += duration;
			if(duration > scen3Max)
				scen3Max = duration;
		}
		
		scen1Avg /= scenario1.size();
		scen2Avg /= scenario2.size();
		scen3Avg /= scenario3.size();
		
		dataset.addValue(scen1Avg, series1, "AVG Duration" + numberOfMachines);
		dataset.addValue(scen1Max, series1, "MAX Duration" + numberOfMachines);
		dataset.addValue(scen2Avg, series2, "AVG Duration" + numberOfMachines);
		dataset.addValue(scen2Max, series2, "MAX Duration" + numberOfMachines);
		dataset.addValue(scen3Avg, series3, "AVG Duration" + numberOfMachines);
		dataset.addValue(scen3Max, series3, "MAX Duration" + numberOfMachines);
		
		JFreeChart chart = ChartFactory.createBarChart(
				"Customer Durations", 		// name
				"Customers", 				// xAxis
				"Duration", 				// yAxis
				dataset,
				PlotOrientation.VERTICAL,	// orientation
				true,						// include legend
				true,						// tooltips?
				false						// URLs?
				);
		
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
        renderer.setSeriesItemLabelGenerator(1, new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
        renderer.setSeriesItemLabelGenerator(2, new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
        renderer.setSeriesItemLabelsVisible(0, true);
        renderer.setSeriesItemLabelsVisible(1, true);
        renderer.setSeriesItemLabelsVisible(2, true);

       
		File barChart = new File("Output" + numberOfMachines + ".png");
		try {
			ChartUtilities.saveChartAsPNG(barChart, chart, 2560, 1920);
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
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));
		 
		vendingMachineExperiment.stop(new TimeInstant(240));
		vendingMachineExperiment.start();
		vendingMachineExperiment.report();
		vendingMachineExperiment.finish();
		
		scenario1 = ((VendingMachineModelScenario1) vendingModel).getCustomerList();
		
		// scenario 2
		
		vendingMachineExperiment = new Experiment("VendingMachine-Experiment-nQueues");
		
		vendingModel = new VendingMachineModelScenario2(null, "VendingMachine-Model", true, true, numOfMachines, lowerBoundCustomerDuration, upperBoundCustomerDuration, arrivalTimeInterval);
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));
		
		vendingMachineExperiment.stop(new TimeInstant(240));
		vendingMachineExperiment.start();
		vendingMachineExperiment.report();
		vendingMachineExperiment.finish();
		
		scenario2 = ((VendingMachineModelScenario2) vendingModel).getCustomerList();
		
		// scenario 3
		
		vendingMachineExperiment = new Experiment("VendingMachine-Experiment-switchingQueue");
		vendingModel = new VendingMachineModelScenario3(null, "VendingMachine-Model", true, true, numOfMachines, lowerBoundCustomerDuration, upperBoundCustomerDuration, arrivalTimeInterval);
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));
		
		vendingMachineExperiment.stop(new TimeInstant(240));
		vendingMachineExperiment.start();
		vendingMachineExperiment.report();
		vendingMachineExperiment.finish();
		
		scenario3 = ((VendingMachineModelScenario3) vendingModel).getCustomerList();
	}
}
