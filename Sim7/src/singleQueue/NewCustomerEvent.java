package singleQueue;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class NewCustomerEvent extends ExternalEvent {
	
	private VendingMachineModel model;
	
	public NewCustomerEvent (Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModel) owner;
	}
	
	@Override
	public void eventRoutine() throws SuspendExecution {
		
		// create new customer
		Customer customer = new Customer(model, "Customer", true);
		
		// new customer arriving event
		CustomerArrivalEvent customerArrival = new CustomerArrivalEvent(model, "CustomerArrival", true);
		
		// activate it
		customerArrival.schedule(customer, new TimeSpan(0.0));
		
		// new event for next customer
		NewCustomerEvent newCustomer = new NewCustomerEvent(model, "CustomerCreation", true);
		newCustomer.schedule(new TimeSpan(model.getCustomerArrivalTime()));
	}
	
}
