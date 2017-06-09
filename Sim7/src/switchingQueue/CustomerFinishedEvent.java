package switchingQueue;

import desmoj.core.simulator.*;
import singleQueue.Customer;

public class CustomerFinishedEvent extends EventOf2Entities<Customer, VendingMachine>{
	
	private VendingMachineModelScenario3 model;

	public CustomerFinishedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModelScenario3) owner;
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
		
		// allow last customers to switch if the queue would be shorter
		int numberOfMachines = model.vendingMachines.size();

		for(int i=0; i<numberOfMachines; i++) {
			VendingMachine machine = model.vendingMachines.get(i);
			if(machine.getCustomerQueue().size()-1 > vendingMachine.getCustomerQueue().size()) {
				switchCustomer(machine, vendingMachine);
			}
		}
	}
	
	private void switchCustomer(VendingMachine from, VendingMachine to) {
		Customer fromCustomer = from.getCustomerQueue().last();
		from.getCustomerQueue().remove(fromCustomer);
		
		to.getCustomerQueue().insert(fromCustomer);
		model.sendTraceNote("Last customer switched from " + from + " to " + to);
	}
	
}
