package nQueues;
import java.util.Random;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerArrivalEvent extends Event<Customer>{
	
	private VendingMachineModel model;
	private Random r = new Random();

	public CustomerArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModel) owner;
	}

	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		int machines = model.vendingMachines.size();
		int queue = r.nextInt(machines);
		
		VendingMachine toInsert = model.vendingMachines.get(queue);
		
		model.sendTraceNote("Customer: " + customer + " Goes to Machine: " + toInsert);
		
		if(!toInsert.getCustomerQueue().isEmpty() || toInsert.getState() == 1) {
			toInsert.getCustomerQueue().insert(customer);
			model.sendTraceNote("Current Queue on Machine: " + toInsert + " : " + toInsert.getCustomerQueue().size());
		}
		else if(toInsert.getState() == 0){
			toInsert.setState(1);
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "CustomerFinished", true);
			customerFinished.schedule(customer, toInsert, new TimeSpan(model.getCustomerDuration()));
		}
		
	}

}
