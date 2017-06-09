package switchingQueue;
import desmoj.core.simulator.*;
import singleQueue.Customer;

import java.util.ArrayList;

import desmoj.core.dist.*;

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
	
	// queue for free machines
	protected ArrayList<VendingMachine> vendingMachines;
	
	// list of customers for plotting data
	protected ArrayList<Customer> customers;
	

	public VendingMachineModelScenario3(Model owner, String name, boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
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
		customerArrivalTime = new ContDistExponential(this, "ArrivalTimeInterval", 3, true, true);
		customerArrivalTime.setNonNegative(true);
		customerArrivalTime.setSeed(9829104);
		
		customerDuration = new ContDistUniform(this, "CustomerDurations", 0.5, 5.0, true, true);
		customerDuration.setSeed(68994545);
		
		vendingMachines = new ArrayList<VendingMachine>();
		
		VendingMachine vendingMachine;
		int machines = 2;
		
		for(int i=0; i<machines; i++){
			vendingMachine = new VendingMachine(this, "VendingMachine", true, i);
			vendingMachines.add(vendingMachine);
		}
		
		customers = new ArrayList<Customer>();

	}
	
	public static void main(String[] args) {
		
		Experiment vendingMachineExperiment = new Experiment("VendingMachine-Experiment-switchingQueue");
	
		VendingMachineModelScenario3 vendingModel = new VendingMachineModelScenario3(null, "VendingMachine-Model", true, true);
	
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
