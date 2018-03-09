package poc.circuitbreaker;

public interface CircuitBreaker {

	void begin() throws MicroServiceUnavailableException;

	void end() throws MicroServiceUnavailableException;

	void analyseException(Exception e) throws Exception;

}
