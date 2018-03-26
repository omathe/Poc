package poc.concurrent;

public class TestFuture {

	public static void main(String[] args) {


		TimeoutTimer timeoutTimer = new TimeoutTimer();

		Thread thread2 = new Thread(timeoutTimer);
		thread2.start();

		timeoutTimer.setStop(true);


	}
}
