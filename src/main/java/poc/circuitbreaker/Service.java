package poc.circuitbreaker;

public interface Service {

	void execute(String number, CircuitBreaker cb) throws Exception;

	void callBack();

}
