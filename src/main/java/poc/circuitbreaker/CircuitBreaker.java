package poc.circuitbreaker;

public interface CircuitBreaker {

	int getTryDuration();

	void begin() throws MicroServiceUnavailableException;

	void end() throws MicroServiceUnavailableException;

	void analyseException(Exception e) throws Exception;

}
