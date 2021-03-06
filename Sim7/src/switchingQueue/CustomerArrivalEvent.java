package switchingQueue;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import singleQueue.Customer;

/**
 * Customer arrival event scenario 3
 * @author Oliver Remy, Max Göttl, Dennis Strähhuber
 */
public class CustomerArrivalEvent extends Event<Customer>{
	
	private VendingMachineModelScenario3 model;

	public CustomerArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (VendingMachineModelScenario3) owner;
		
	}

	@Override
	public void eventRoutine(Customer customer) throws SuspendExecution {
		
		int numberOfMachines = model.vendingMachines.size();
		int machine = 0;
		
		if(model.getRandomAssignement()) {
			// random machine
			machine = (int) model.getQueue();
		}else {
			int minQueueLength = model.vendingMachines.get(0).getCustomerQueue().size();
			int state = model.vendingMachines.get(0).getState();
			// find the best queue and hence the machine
			for(int i=1; i<numberOfMachines; i++){
				int queueLength = model.vendingMachines.get(i).getCustomerQueue().size();
				int tempState = model.vendingMachines.get(i).getState();
				if(queueLength < minQueueLength){
					machine = i;
					minQueueLength = queueLength;
					state = tempState;
				} else if(queueLength == minQueueLength && tempState < state){
					machine = i;
					minQueueLength = queueLength;
					state = tempState;
				}
			}
		}
		
		// machine where the customer goes to
		VendingMachine toInsert = model.vendingMachines.get(machine);	
		model.sendTraceNote("Customer: " + customer + " Goes to Machine: " + toInsert);
		
		// insert customer
		toInsert.getCustomerQueue().insert(customer);
		customer.setEnqueue(model.presentTime());
		
		// machine is free
		if(toInsert.getState() == 0){
			toInsert.setState(1);
			// remove customer
			toInsert.getCustomerQueue().remove(customer);
			customer.setDequeue(model.presentTime());
			model.customers.add(customer);
			
			CustomerFinishedEvent customerFinished = new CustomerFinishedEvent(model, "CustomerFinished", true);
			customerFinished.schedule(customer, toInsert, new TimeSpan(model.getCustomerDuration()));
		}
		
	}

}
