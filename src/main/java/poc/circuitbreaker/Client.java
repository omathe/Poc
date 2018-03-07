package poc.circuitbreaker;

import java.util.Random;

public class Client {

	public static void main(String[] args) {

		Service service = new SimpleService();

		CircuitBreaker circuitBreaker = new SimpleCircuitBreaker();

		for (int i = 0; i < 2; i++) {

			new Thread(() -> {
				Random rand = new Random();
				int sleep = rand.nextInt(3000);

				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e1) {
				}

				try {
					service.execute("10", circuitBreaker);
				} catch (Exception e) {
					System.err.println(e);
				}

			}).start();
		}

	}

}
