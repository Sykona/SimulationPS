package singleQueue;
import desmoj.core.simulator.*;

public class Customer extends Entity {
	
	// enqueue and dequeue of the waiting queue
	protected TimeInstant enqueue;
	protected TimeInstant dequeue;
	
	public Customer(Model owner, String name, boolean showInTrace){
		super(owner, name, showInTrace);
	}
	
	public void setEnqueue(TimeInstant enqueue) {
		this.enqueue = enqueue;
	}
	
	public void setDequeue(TimeInstant dequeue) {
		this.dequeue = dequeue;
	}
	
	public double getDuration(){
		if(enqueue != null && dequeue != null)
			return dequeue.getTimeAsDouble() - enqueue.getTimeAsDouble();
		else if (enqueue == null && dequeue == null)
			return 0.0;
		else {
			System.err.println("Customer Duration Failed");
			return 0.0;
		}
	}
	
}
