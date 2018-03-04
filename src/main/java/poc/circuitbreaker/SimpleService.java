package poc.circuitbreaker;

public class SimpleService implements Service {

	private CircuitBreaker cb;

	public SimpleService() {
		cb = new SimpleCircuitBreaker();
	}

	@Override
	public void execute(String number, CircuitBreaker cb) throws Exception {

		System.out.println("execute");

		cb.begin();

		Integer integer = null;
		try {
			integer = Integer.valueOf(number);
			cb.end();

		} catch (Exception e) {
			System.err.println(e);
			cb.analyseException(e);
		}
		System.out.println("number is " + integer);

	}

	@Override
	public void callBack() {

	}

}
