package poc.circuitbreaker;

public class CircuitBreakerStatus {

	private boolean open;

	public CircuitBreakerStatus() {
		this.open = false;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}
}
