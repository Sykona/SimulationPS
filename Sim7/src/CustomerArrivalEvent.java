import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerArrivalEvent extends Event<Customer>{
	
	private VendingMachineModel model;

	public CustomerArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModel) owner;
	}

	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		model.customerQueue.insert(customer);
		sendTraceNote("Lenght of Customer-Queue: " + model.customerQueue.length());
		
		if (!model.freeVendingMachineQueue.isEmpty()) {
			
			VendingMachine vendingMachine = model.freeVendingMachineQueue.first();
			
			model.freeVendingMachineQueue.remove(vendingMachine);

			model.occupiedVendingMachineQueue.insert(vendingMachine);
			
			model.customerQueue.remove(customer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "Customer Finished", true);
			
			customerFinished.schedule(customer, new TimeSpan(model.getCustomerDuration()));
		}
		
		
		
	}

}
