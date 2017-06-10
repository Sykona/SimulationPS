package singleQueue;
import desmoj.core.simulator.*;
import desmoj.core.statistic.Histogram;
import desmoj.extensions.grafic.util.Plotter;

import java.util.ArrayList;

import desmoj.core.dist.*;

public class VendingMachineModelScenario1 extends Model{
	
	// random generator for customer arrivals
	private ContDistExponential customerArrivalTime;
	public double getCustomerArrivalTime() {
		return customerArrivalTime.sample();
	}
	
	// random generator for customers duration
	private ContDistUniform customerDuration;
	public double getCustomerDuration() {
		return customerDuration.sample();
	}
	
	// queue for waiting customers
	protected Queue<Customer> customerQueue;
	
	// queue for free machines
	protected Queue<VendingMachine> freeVendingMachineQueue;
	
	// queue for occupied machines
	protected Queue<VendingMachine> occupiedVendingMachineQueue;
	
	// list of customers for plotting data
	protected ArrayList<Customer> customers;

	// number of vending machines
	protected int numOfMachines;
	

	public VendingMachineModelScenario1(Model owner, String name, boolean showInReport, boolean showInTrace, int numOfMachines) {
		super(owner, name, showInReport, showInTrace);
		this.numOfMachines = numOfMachines;
	}

	@Override
	public String description() {
		return "Ticket vending machine model in a railway station. Modelled with Scenario 1.";
	}

	@Override
	public void doInitialSchedules() {
		NewCustomerEvent firstCustomer = new NewCustomerEvent(this, "CustomerCreation", true);	
		firstCustomer.schedule(new TimeSpan(getCustomerArrivalTime()));
	}

	@Override
	public void init() {
		customerArrivalTime = new ContDistExponential(this, "ArrivalTimeInterval", 2, true, true);
		customerArrivalTime.setNonNegative(true);
		customerArrivalTime.setSeed(9829104);
		
		customerDuration = new ContDistUniform(this, "CustomerDurations", 0.5, 5.0, true, true);
		customerDuration.setSeed(68994545);
		
		customerQueue = new Queue<Customer>(this, "Customer-Queue", true, true);
		
		freeVendingMachineQueue = new Queue<VendingMachine>(this, "FreeVendingMachine-Queue", true, true);
		
		VendingMachine vendingMachine;
		
		for(int i=0; i<numOfMachines; i++){
			vendingMachine = new VendingMachine(this, "VendingMachine", true);
			freeVendingMachineQueue.insert(vendingMachine);
		}
		
		occupiedVendingMachineQueue = new Queue<VendingMachine>(this, "OccupiedVendingMachine-Queue", true, true);
	
		customers = new ArrayList<Customer>();
	}
	
	public static void main(String[] args) {
		
		Experiment vendingMachineExperiment = new Experiment("VendingMachine-Experiment-singleQueue");
	
		VendingMachineModelScenario1 vendingModel = new VendingMachineModelScenario1(null, "VendingMachine-Model", true, true, 2);
	
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));
		
		vendingMachineExperiment.stop(new TimeInstant(240));
		
		vendingMachineExperiment.start();
		
		vendingMachineExperiment.report();
		
		vendingMachineExperiment.finish();
	
	}
	
	public ArrayList<Customer> getCustomerList() {
		return customers;
	}


}
