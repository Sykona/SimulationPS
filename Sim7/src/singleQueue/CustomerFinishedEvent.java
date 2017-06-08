package singleQueue;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerFinishedEvent extends Event<Customer>{
	
	private VendingMachineModel model;

	public CustomerFinishedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModel) owner;
	}

	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		if(!model.customerQueue.isEmpty()) {
			Customer nextCustomer = model.customerQueue.first();
			model.customerQueue.remove(nextCustomer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "Customer Finished", true);
			
			customerFinished.schedule(customer, new TimeSpan(model.getCustomerDuration()));
		}
		else {
			VendingMachine vendingMachine = model.occupiedVendingMachineQueue.first();
			model.occupiedVendingMachineQueue.remove(vendingMachine);
			
			model.freeVendingMachineQueue.insert(vendingMachine);
		}
		
	}
	
}
