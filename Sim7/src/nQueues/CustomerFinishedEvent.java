package nQueues;

import desmoj.core.simulator.*;
import singleQueue.Customer;

public class CustomerFinishedEvent extends EventOf2Entities<Customer, VendingMachine>{
	
	private VendingMachineModelScenario2 model;

	public CustomerFinishedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModelScenario2) owner;
	}

	@Override
	public void eventRoutine(Customer customer, VendingMachine vendingMachine) {
		
		// get the queue from the machine of the finished customer
		Queue<Customer> customerQueue = vendingMachine.getCustomerQueue();
		
		model.sendTraceNote("Customer: " + customer + " finished.");
		model.customers.add(customer);
		
		// queue not empty, handle next customer
		if(!customerQueue.isEmpty()) {
			Customer nextCustomer = customerQueue.first();
			customerQueue.remove(nextCustomer);
			nextCustomer.setDequeue(model.presentTime());
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "CustomerFinished", true);
			
			customerFinished.schedule(nextCustomer, vendingMachine, new TimeSpan(model.getCustomerDuration()));
		}
		// queue empty, set machine to free
		else{
			vendingMachine.setState(0);
		}
	}
	
}
