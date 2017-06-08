package nQueues;
import desmoj.core.simulator.*;
import desmoj.extensions.visualization2d.engine.model.List;

import java.util.ArrayList;

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
	
	// queue for free machines
	protected ArrayList<VendingMachine> vendingMachines;
	

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
		customerArrivalTime.setNonNegative(true);
		
		customerDuration = new ContDistUniform(this, "CustomerDurations", 0.5, 10.0, true, true);
		
		vendingMachines = new ArrayList<VendingMachine>();
		
		VendingMachine vendingMachine;
		int machines = 2;
		
		for(int i=0; i<machines; i++){
			vendingMachine = new VendingMachine(this, "VendingMachine", true);
			vendingMachines.add(vendingMachine);
		}

	}
	
	public static void main(String[] args) {
		
		Experiment vendingMachineExperiment = new Experiment("VendingMachine-Experiment-nQueues");
	
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
