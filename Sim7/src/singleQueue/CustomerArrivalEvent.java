package singleQueue;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerArrivalEvent extends Event<Customer>{
	
	private VendingMachineModelScenario1 model;

	public CustomerArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModelScenario1) owner;
	}

	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		// insert customer
		model.customerQueue.insert(customer);
		customer.setEnqueue(model.presentTime());
		sendTraceNote("Lenght of Customer-Queue: " + model.customerQueue.length());
		
		if (!model.freeVendingMachineQueue.isEmpty()) {
			
			VendingMachine vendingMachine = model.freeVendingMachineQueue.first();
			
			model.freeVendingMachineQueue.remove(vendingMachine);

			model.occupiedVendingMachineQueue.insert(vendingMachine);
			
			// remove customer
			model.customerQueue.remove(customer);
			customer.setDequeue(model.presentTime());
			model.customers.add(customer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "Customer Finished", true);
			customerFinished.schedule(customer, new TimeSpan(model.getCustomerDuration()));
		}		
		
	}

}
