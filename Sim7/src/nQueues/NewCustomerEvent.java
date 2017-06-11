package nQueues;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import singleQueue.Customer;
/**
 * New customer event for scenario 2
 * @author Oliver Remy, Max Göttl, Dennis Strähhuber
 */
public class NewCustomerEvent extends ExternalEvent {
	
	private VendingMachineModelScenario2 model;
	
	public NewCustomerEvent (Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModelScenario2) owner;
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
