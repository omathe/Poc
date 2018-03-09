package poc.circuitbreaker;

import java.util.Random;

public class SimpleService implements Service {

	private CircuitBreaker cb;

	public SimpleService() {
		cb = new SimpleCircuitBreaker();
	}

	@Override
	public void execute(String number, CircuitBreaker cb) throws Exception {

		cb.begin();

		try {
			Random rand = new Random();
			int duration = rand.nextInt(4000);
			System.err.println("thread " + Thread.currentThread().getId() + " : duration = " + duration);

			Thread.sleep(duration);

			Integer integer = Integer.valueOf(number);

			cb.end();
		} catch (Exception e) {
			int i = new Random().nextInt(4000);
			if (i % 2 == 0) {
				cb.analyseException(new IllegalStateException("number must be odd"));
			} else {
				cb.analyseException(e);
			}
		}
	}

	@Override
	public void callBack() {

	}

}
