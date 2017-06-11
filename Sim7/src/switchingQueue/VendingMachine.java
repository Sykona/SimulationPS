package switchingQueue;
import desmoj.core.simulator.*;
import singleQueue.Customer;

/**
 * Vending machine entity scenario 3
 * @author Oliver Remy, Max Göttl, Dennis Strähhuber
 */
public class VendingMachine extends Entity{
	
	// Queue for waiting customers
	protected Queue<Customer> customerQueue;
	protected int id;
	
	protected int state; // 0 = free; 1 = occupied

	public VendingMachine(Model owner, String name, boolean showInTrace, int id){
		super(owner, name, showInTrace);
		this.id = id;
		customerQueue = new Queue<Customer>((VendingMachineModelScenario3) owner, "CustomerQueue" + id, true, true);
		state = 0;
	}
	
	public Queue<Customer> getCustomerQueue() {
		return customerQueue;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getId() {
		return id;
	}
}
