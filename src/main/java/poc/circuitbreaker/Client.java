package poc.circuitbreaker;

import java.util.Random;

public class Client {

	public static void main(String[] args) {

		Service service = new SimpleService();

		CircuitBreaker circuitBreaker = new SimpleCircuitBreaker();

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(new Random().nextInt(2000));
			} catch (InterruptedException e1) {
			}

			new Thread(() -> {
				try {
					Random rand = new Random();
					String number = "10";
					if (rand.nextInt(3) % 2 == 0) {
						number += "a";
					}
					service.execute(number, circuitBreaker);
				} catch (Exception e) {
					System.err.println(e);
				}

			}).start();
		}
	}
}
