package poc.circuitbreaker;

public class MicroServiceUnavailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public MicroServiceUnavailableException(String message) {
		super(message);
	}

}
