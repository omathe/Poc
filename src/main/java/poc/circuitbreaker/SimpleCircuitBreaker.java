package poc.circuitbreaker;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Simple CircuitBreaker implementation
 *
 * @author oma
 */
public class SimpleCircuitBreaker implements CircuitBreaker {

	/**
	 * Map of events. The key is the Thread identifier
	 */
	private ConcurrentHashMap<Long, Event> events;

	/**
	 * Date of opening the circuit
	 */
	private AtomicLong openDate;

	/**
	 * Duration of opening circuit in ms
	 */
	private long openDuration = 5000;

	/**
	 * Period during which failed events are counted in ms
	 */
	private long failedPeriod = 3000;

	/**
	 * Maximum number of failed events accepted during the failedPeriod
	 */
	private int maxFailed = 3;

	/**
	 * The exceptions that set an event to 'KO'
	 */
	private String catched;

	public SimpleCircuitBreaker() {
		events = new ConcurrentHashMap<Long, Event>();
		openDate = new AtomicLong();
		catched = NumberFormatException.class.getName();
	}

	@Override
	public void begin() throws MicroServiceUnavailableException {

		check();

		Long threadId = Thread.currentThread().getId();
		System.out.println("thread " + threadId + " : BEGIN");

		events.putIfAbsent(threadId, new Event(Instant.now().toEpochMilli()));

		new Thread(() -> {
			try {
				Thread.sleep(failedPeriod);
				Event event = events.get(threadId);
				if (event != null && event.getDeliveryStatus() == null && event.getStatus() == null) {
					event.setDeliveryStatus("LATE");
					event.setDate(Instant.now().toEpochMilli());
					System.err.println("thread " + threadId + " : LATE");
				}
			} catch (InterruptedException e) {
			}
		}).start();
	}

	@Override
	public void end() throws MicroServiceUnavailableException {

		Long threadId = Thread.currentThread().getId();
		System.out.println("thread " + threadId + " : END");

		Event event = events.get(threadId);
		if (event != null && event.getDeliveryStatus() == null) {
			event.setDeliveryStatus("ON_TIME");
			event.setStatus("OK");
			System.out.println("thread " + threadId + " : OK");
		}
	}

	@Override
	public void analyseException(Exception e) throws Exception {

		Long threadId = Thread.currentThread().getId();
		Event event = events.get(threadId);

		if (event != null) {
			if (e.getClass().getName().equals(catched)) {
				System.err.println("thread " + threadId + " : KO");
				event.setStatus("KO");
				event.setDate(Instant.now().toEpochMilli());
				System.err.println("thread " + threadId + " : CATCHED EXCEPTION " + e.getClass().getSimpleName());
				check();
			} else {
				System.err.println("thread " + threadId + " : THREW EXCEPTION " + e.getClass().getSimpleName());
				throw e;
			}
		}
	}

	private void check() throws MicroServiceUnavailableException {

		boolean open = openDate.get() != 0 && (Instant.now().toEpochMilli() - openDate.get() <= openDuration);

		Map<Boolean, List<Event>> partionedEvents = events.values().stream()
				.collect(Collectors.partitioningBy(event -> Instant.now().toEpochMilli() - event.getDate() > failedPeriod));

		boolean maxFailedReached = false;
		if (!open) {
			// we count the number of KO and LATE events among the not expired events
			maxFailedReached = partionedEvents.get(Boolean.FALSE).stream()
					.filter(event -> "KO".equals(event.getStatus()) || "LATE".equals(event.getDeliveryStatus()))
					.count() >= maxFailed;

			if (maxFailedReached) {
				openDate.set(Instant.now().toEpochMilli());
			}
		}

		// remove expired events
		new Thread(() -> {
			events.values().removeAll(partionedEvents.get(Boolean.TRUE));
		}).start();

		if (open || maxFailedReached) {
			System.err.println("open = " + open + ", maxFailedReached = " + maxFailedReached + (maxFailedReached ? " (" + partionedEvents.get(Boolean.FALSE).size() + ")" : ""));
			throw new MicroServiceUnavailableException("Service is open !");
		}
	}

}
