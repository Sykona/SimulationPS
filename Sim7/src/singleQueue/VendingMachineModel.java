package singleQueue;
import desmoj.core.simulator.*;
import desmoj.core.dist.*;

public class VendingMachineModel extends Model{
	
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
	

	public VendingMachineModel(Model owner, String name, boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return "Ticket vending machine model in a railway station.";
	}

	@Override
	public void doInitialSchedules() {
		NewCustomerEvent firstCustomer = new NewCustomerEvent(this, "CustomerCreation", true);	
		firstCustomer.schedule(new TimeSpan(getCustomerArrivalTime()));
	}

	@Override
	public void init() {
		customerArrivalTime = new ContDistExponential(this, "ArrivalTimeInterval", 3.0, true, true);
		customerArrivalTime.setSeed(9829105);
		customerArrivalTime.setNonNegative(true);
		
		customerDuration = new ContDistUniform(this, "CustomerDurations", 0.5, 10.0, true, true);
		customerDuration.setSeed(68994544);
		
		customerQueue = new Queue<Customer>(this, "Customer-Queue", true, true);
		
		freeVendingMachineQueue = new Queue<VendingMachine>(this, "FreeVendingMachine-Queue", true, true);
		
		VendingMachine vendingMachine;
		int machines = 2;
		
		for(int i=0; i<machines; i++){
			vendingMachine = new VendingMachine(this, "VendingMachine", true);
			freeVendingMachineQueue.insert(vendingMachine);
		}
		
		occupiedVendingMachineQueue = new Queue<VendingMachine>(this, "OccupiedVendingMachine-Queue", true, true);
	}
	
	public static void main(String[] args) {
		
		Experiment vendingMachineExperiment = new Experiment("VendingMachine-Experiment-singleQueue");
	
		VendingMachineModel vendingModel = new VendingMachineModel(null, "VendingMachine-Model", true, true);
	
		vendingModel.connectToExperiment(vendingMachineExperiment);
		
		vendingMachineExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		vendingMachineExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));
		
		vendingMachineExperiment.stop(new TimeInstant(240));
		
		vendingMachineExperiment.start();
		
		vendingMachineExperiment.report();
		
		vendingMachineExperiment.finish();
	
	}


}
