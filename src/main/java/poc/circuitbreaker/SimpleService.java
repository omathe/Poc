package poc.circuitbreaker;

import java.util.Random;

public class SimpleService implements Service {

	private CircuitBreaker cb;

	public SimpleService() {
		cb = new SimpleCircuitBreaker();
	}

	@Override
	public void execute(String number, CircuitBreaker cb) throws Exception {

//		System.out.println("execute");

		cb.begin();

		Integer integer = null;
		try {

			// code de test
			Random rand = new Random();
//			int responseTime = rand.nextInt(4000);
			int responseTime = 4000;
			System.out.println("response time = " + responseTime);
			Thread.sleep(4000);
			// fin code de test

			integer = Integer.valueOf(number);
			cb.end();
			System.out.println("number is " + integer);

		} catch (Exception e) {
//			System.err.println(e);
			cb.analyseException(e);
		}

	}

	@Override
	public void callBack() {

	}

}
