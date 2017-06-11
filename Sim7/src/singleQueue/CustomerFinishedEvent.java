package singleQueue;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerFinishedEvent extends Event<Customer>{
	
	private VendingMachineModelScenario1 model;

	public CustomerFinishedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModelScenario1) owner;
	}

	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		// queue not empty, handle next customer
		if(!model.customerQueue.isEmpty()) {
			Customer nextCustomer = model.customerQueue.first();
			model.customerQueue.remove(nextCustomer);
			nextCustomer.setDequeue(model.presentTime());
			model.customers.add(nextCustomer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "Customer Finished", true);
			
			customerFinished.schedule(nextCustomer, new TimeSpan(model.getCustomerDuration()));
		}
		// queue empty, set machine to free
		else {
			VendingMachine vendingMachine = model.occupiedVendingMachineQueue.first();
			model.occupiedVendingMachineQueue.remove(vendingMachine);
			
			model.freeVendingMachineQueue.insert(vendingMachine);
		}
		
	}
	
}
