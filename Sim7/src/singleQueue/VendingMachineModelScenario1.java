package singleQueue;
import desmoj.core.simulator.*;

import java.util.ArrayList;
import java.util.Random;

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
	
	// interval values
	protected double lowerBoundCustomerDuration;
	protected double upperBoundCustomerDuration;
	protected double arrivalTimeInterval;
	

	public VendingMachineModelScenario1(Model owner, String name, boolean showInReport, boolean showInTrace, int numOfMachines, double lowerBoundCustomerDuration, double upperBoundCustomerDuration, double arrivalTimeInterval) {
		super(owner, name, showInReport, showInTrace);
		this.numOfMachines = numOfMachines;
		this.lowerBoundCustomerDuration = lowerBoundCustomerDuration;
		this.upperBoundCustomerDuration = upperBoundCustomerDuration;
		this.arrivalTimeInterval = arrivalTimeInterval;
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
		Random r = new Random();
		customerArrivalTime = new ContDistExponential(this, "ArrivalTimeInterval", arrivalTimeInterval, true, true);
		customerArrivalTime.setNonNegative(true);
		customerArrivalTime.reset(Math.abs(r.nextLong()));
		
		customerDuration = new ContDistUniform(this, "CustomerDurations", lowerBoundCustomerDuration, upperBoundCustomerDuration, true, true);
		customerDuration.reset(Math.abs(r.nextLong()));
		
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
	
		VendingMachineModelScenario1 vendingModel = new VendingMachineModelScenario1(null, "VendingMachine-Model", true, true, 2, 0.5, 10.0, 3);
	
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
	
	public long getArrivalSeed() {
		return customerArrivalTime.getInitialSeed();
	}
	
	public long getDurationSeed() {
		return customerDuration.getInitialSeed();
	}
	
	public void setArrivalSeed(long seed) {
		customerArrivalTime.setSeed(seed);
	}
	
	public void setDurationSeed(long seed) {
		customerDuration.setSeed(seed);
	}


}
