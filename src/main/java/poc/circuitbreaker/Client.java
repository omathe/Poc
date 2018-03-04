package poc.circuitbreaker;

public class Client {

	public static void main(String[] args) {

		Service service = new SimpleService();

		CircuitBreaker circuitBreaker = new SimpleCircuitBreaker();

		try {
			service.execute("10a", circuitBreaker);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
