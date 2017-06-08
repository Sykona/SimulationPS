package nQueues;

import desmoj.core.simulator.*;

public class CustomerFinishedEvent extends EventOf2Entities<Customer, VendingMachine>{
	
	private VendingMachineModel model;

	public CustomerFinishedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModel) owner;
	}

	/*
	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		if(!model.customerQueue.isEmpty()) {
			Customer nextCustomer = model.customerQueue.first();
			model.customerQueue.remove(nextCustomer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "CustomerFinished", true);
			
			customerFinished.schedule(customer, new TimeSpan(model.getCustomerDuration()));
		}
		else {
			VendingMachine vendingMachine = model.occupiedVendingMachineQueue.first();
			model.occupiedVendingMachineQueue.remove(vendingMachine);
			
			model.freeVendingMachineQueue.insert(vendingMachine);
		}
		
	}*/

	@Override
	public void eventRoutine(Customer customer, VendingMachine vendingMachine) {
		
		Queue<Customer> customerQueue = vendingMachine.getCustomerQueue();
		
		model.sendTraceNote("Customer: " + customer + " finished.");
		
		if(!customerQueue.isEmpty()) {
			Customer nextCustomer = customerQueue.first();
			customerQueue.remove(nextCustomer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "CustomerFinished", true);
			
			customerFinished.schedule(nextCustomer, vendingMachine, new TimeSpan(model.getCustomerDuration()));
		}
		else{
			vendingMachine.setState(0);
		}
	}
	
}
