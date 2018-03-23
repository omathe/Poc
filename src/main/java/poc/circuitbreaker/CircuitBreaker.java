package poc.circuitbreaker;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * A CircuitBreaker allows to make service as unavailable when several timeout or exceptions occur during a period (failedPeriod).
 * If the number of failed (maxFailed) is reached, the circuit breaker is opening during a period (openDuration).
 * During this period, the service is unavailable and a specific Exception is thrown (MicroServiceUnavailableException).
 *  After that period the circuit breaker is closed and the service is accessible again.
 *
 * @author oma
 */
public class CircuitBreaker {

	/**
	 * Circuit breaker name
	 */
	private String name;

	/**
	 * Map of responses. The key is the Thread identifier
	 */
	private ConcurrentHashMap<Long, Response> responses;

	/**
	 * Date of opening the circuit
	 */
	private AtomicLong openDate;

	/**
	 * Duration of opening circuit (in ms)
	 */
	private long openDuration = 5000;

	/**
	 * Period during which failed responses are counted (in ms)
	 */
	private long failedPeriod = 3000;

	/**
	 * Maximum number of failed responses accepted during the failedPeriod
	 */
	private int maxFailed = 3;

	/**
	 * The exceptions that set a response to failed
	 */
	private String catched;

	private CircuitBreakerStatus circuitBreakerStatus;

	public CircuitBreaker(String name) {
		this.name = name;
		responses = new ConcurrentHashMap<Long, Response>();
		openDate = new AtomicLong();
		catched = NumberFormatException.class.getName();
		circuitBreakerStatus = new CircuitBreakerStatus();
	}

	public CircuitBreakerStatus begin() {

		check();

		Long threadId = Thread.currentThread().getId();
		System.out.println("thread " + threadId + " : BEGIN");

		responses.putIfAbsent(threadId, new Response(Instant.now().toEpochMilli()));

		new Thread(() -> {
			try {
				Thread.sleep(failedPeriod);
				Response response = responses.get(threadId);
				if (response != null && response.hasNoDeliveryStatus() && response.hasNoStatus()) {
					response.setLate();
					response.setDate(Instant.now().toEpochMilli());
					System.err.println("thread " + threadId + " : LATE");
				}
			} catch (InterruptedException e) {
			}
		}).start();
		return circuitBreakerStatus;
	}

	public void end() {

		Long threadId = Thread.currentThread().getId();
		System.out.println("thread " + threadId + " : END");

		Response response = responses.get(threadId);
		if (response != null && response.hasNoDeliveryStatus()) {
			response.setOnTime();
			response.setSuccessful();
			System.out.println("thread " + threadId + " : OK");
		}
	}

	public CircuitBreakerStatus analyseException(Exception e) {

		Long threadId = Thread.currentThread().getId();
		Response response = responses.get(threadId);

		if (response != null) {
			if (e.getClass().getName().equals(catched)) {
				System.err.println("thread " + threadId + " : KO");
				response.setFailed();
				response.setDate(Instant.now().toEpochMilli());
				System.err.println("thread " + threadId + " : CATCHED EXCEPTION " + e.getClass().getSimpleName());
				check();
			}
		}
		return circuitBreakerStatus;
	}

	private CircuitBreakerStatus check() {

		boolean open = openDate.get() != 0 && (Instant.now().toEpochMilli() - openDate.get() <= openDuration);

		circuitBreakerStatus.setOpen(false);
		if (open) {
			System.err.println("open = " + open);
			circuitBreakerStatus.setOpen(true);
		} else {
			Map<Boolean, List<Response>> partionedResponses = responses.values().stream()
					.collect(Collectors.partitioningBy(response -> response.hasFailed() || response.isLate()));

			long failedCount = partionedResponses.get(Boolean.TRUE).stream().count();

			// remove expired responses
			new Thread(() -> {
				responses.values().removeAll(partionedResponses.get(Boolean.FALSE));
			}).start();

			if (failedCount >= maxFailed) {

				openDate.set(Instant.now().toEpochMilli());
				System.err.println("maxFailedReached (" + failedCount + ")");
				circuitBreakerStatus.setOpen(true);
				responses.clear();
			}
		}
		return circuitBreakerStatus;
	}

}
