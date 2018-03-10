package poc.circuitbreaker;

import java.util.Random;

public class SimpleService implements Service {

	private CircuitBreaker cb;

	public SimpleService() {
		cb = new CircuitBreaker("LNS");
	}

	@Override
	public void execute(String number) throws Exception {

		cb.begin();

		try {
			Random rand = new Random();
			int duration = rand.nextInt(4000);
			System.out.println("thread " + Thread.currentThread().getId() + " : NUMBER " + number);
			System.out.println("thread " + Thread.currentThread().getId() + " : DURATION " + duration);

			Thread.sleep(500);

			Integer integer = Integer.valueOf(number);

			cb.end();
		} catch (Exception e) {
			cb.analyseException(e);

			/*int i = new Random().nextInt(4000);
			if (i % 2 == 0) {
				cb.analyseException(new IllegalStateException("number must be odd"));
			} else {
				cb.analyseException(e);
			}*/
		}
	}

	@Override
	public void callBack() {

	}

}
