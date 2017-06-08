package nQueues;
import desmoj.core.simulator.*;

public class VendingMachine extends Entity{
	
	// Queue for waiting customers
	protected Queue<Customer> customerQueue;
	
	protected int state; // 0 = free; 1 = occupied

	public VendingMachine(Model owner, String name, boolean showInTrace){
		super(owner, name, showInTrace);
		customerQueue = new Queue<Customer>((VendingMachineModel) owner, "CustomerQueue", true, true);
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
}
