package switchingQueue;
import desmoj.core.simulator.*;
import singleQueue.Customer;

import java.util.ArrayList;

import desmoj.core.dist.*;

/**
 * Vending machine model scenario 3
 * @author Oliver Remy, Max Göttl, Dennis Strähhuber
 */
public class VendingMachineModelScenario3 extends Model{
	
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
	
	// random generator for enqueuing customers
	private ContDistUniform queue;
	public double getQueue() {
		return queue.sample();
	}
	
	// queue for free machines
	protected ArrayList<VendingMachine> vendingMachines;
	
	// list of customers for plotting data
	protected ArrayList<Customer> customers;

	// number of vending machines
	protected int numOfMachines;
	
	// interval values
	protected double lowerBoundCustomerDuration;
	protected double upperBoundCustomerDuration;
	protected double arrivalTimeInterval;
	
	// value of random assignement
	protected boolean randomAssignement;
	

	public VendingMachineModelScenario3(Model owner, String name, boolean showInReport, boolean showInTrace, int numOfMachines, double lowerBoundCustomerDuration, double upperBoundCustomerDuration, double arrivalTimeInterval, boolean randomAssignement) {
		super(owner, name, showInReport, showInTrace);
		this.numOfMachines = numOfMachines;
		this.lowerBoundCustomerDuration = lowerBoundCustomerDuration;
		this.upperBoundCustomerDuration = upperBoundCustomerDuration;
		this.arrivalTimeInterval = arrivalTimeInterval;
		this.randomAssignement = randomAssignement;
	}

	@Override
	public String description() {
		return "Ticket vending machine model in a railway station. Modelled with Scenario 3.";
	}

	@Override
	public void doInitialSchedules() {
		NewCustomerEvent firstCustomer = new NewCustomerEvent(this, "CustomerCreation", true);	
		firstCustomer.schedule(new TimeSpan(getCustomerArrivalTime()));
	}

	@Override
	public void init() {
		customerArrivalTime = new ContDistExponential(this, "ArrivalTimeInterval", arrivalTimeInterval, true, true);
		customerArrivalTime.setNonNegative(true);
		
		customerDuration = new ContDistUniform(this, "CustomerDurations", lowerBoundCustomerDuration, upperBoundCustomerDuration, true, true);
		
		if(randomAssignement)
			queue = new ContDistUniform(this, "Queue", 0.0, numOfMachines, true, true);
		
		vendingMachines = new ArrayList<VendingMachine>();
		
		VendingMachine vendingMachine;
		
		for(int i=0; i<numOfMachines; i++){
			vendingMachine = new VendingMachine(this, "VendingMachine", true, i);
			vendingMachines.add(vendingMachine);
		}
		
		customers = new ArrayList<Customer>();

	}
	
	public static void main(String[] args) {
		
		Experiment vendingMachineExperiment = new Experiment("VendingMachine-Experiment-switchingQueue");
	
		VendingMachineModelScenario3 vendingModel = new VendingMachineModelScenario3(null, "VendingMachine-Model", true, true, 2, 0.5, 10.0, 3, false);
	
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
	
	public long getQueueSeed() {
		return queue.getInitialSeed();
	}
	
	public void setArrivalSeed(long seed) {
		customerArrivalTime.reset(seed);
	}
	
	public void setDurationSeed(long seed) {
		customerDuration.reset(seed);
	}
	
	public void setQueueSeed(long seed) {
		queue.reset(seed);
	}
	
	public boolean getRandomAssignement() {
		return randomAssignement;
	}

}
