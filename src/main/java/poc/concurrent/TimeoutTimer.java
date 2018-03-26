package poc.concurrent;

public class TimeoutTimer implements Runnable {

	private boolean stop;

	@Override
	public void run() {

		try {
			Thread.sleep(3000);
			if (!stop) {
				System.out.println("processing");
			} else {
				System.err.println("stopped !");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
