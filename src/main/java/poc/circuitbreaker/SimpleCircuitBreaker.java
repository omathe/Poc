package poc.circuitbreaker;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 *
 * @author olivier
 *
 */
public class SimpleCircuitBreaker implements CircuitBreaker {

	private ConcurrentHashMap<Long, Event> events;
	private AtomicLong openDate;
	private long openDuration = 30;
	private long tryDuration = 3000;
	private int maxFailed = 3;
	private String catched;

	public SimpleCircuitBreaker() {
		events = new ConcurrentHashMap<Long, Event>();
		openDate = new AtomicLong();
		catched = NumberFormatException.class.getName();
	}

	@Override
	public int getTryDuration() {
		return 0;
	}

	@Override
	public void begin() throws MicroServiceUnavailableException {

		check();

		Long threadId = Thread.currentThread().getId();
		System.out.println("threadId = " + threadId);
		events.putIfAbsent(threadId, new Event(Instant.now().toEpochMilli()));

		new Thread(() -> {
			try {
				Thread.sleep(tryDuration);
				System.out.println("threadId = " + threadId);
				Event event = events.get(threadId);
				if (event != null) {
					if (event.getStatus().equals("OK")) {
						events.remove(threadId);
					} else {
						event.setStatus("KO");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public void end() throws MicroServiceUnavailableException {

		Long threadId = Thread.currentThread().getId();
		Event event = events.get(threadId);
		event.setStatus("OK");
	}

	@Override
	public void analyseException(Exception e) throws Exception {

		System.out.println("e.getClass().getName() = " + e.getClass().getName());
		System.out.println("catched.getClass().getName() = " + catched.getClass().getName());

		if (e.getClass().getName().equals(catched)) {
			Long threadId = Thread.currentThread().getId();
			Event event = events.get(threadId);
			event.setStatus("KO");
			check();
		} else {
			throw e;
		}
	}

	private void check() throws MicroServiceUnavailableException {

		boolean open = openDate.get() != 0 && (Instant.now().toEpochMilli() - openDate.get() <= openDuration);

		Map<Boolean, List<Event>> partionedEvents = events.values().stream().collect(Collectors.partitioningBy(event -> Instant.now().toEpochMilli() - event.getStartDate() < tryDuration));

		boolean maxFailedReached = false;
		if (!open) {
			maxFailedReached = partionedEvents.get(Boolean.TRUE).size() >= maxFailed;
			if (maxFailedReached) {
				openDate.set(Instant.now().toEpochMilli());
			}
		}
		// remove expired events
		events.values().removeAll(partionedEvents.get(Boolean.FALSE));
		if (open || maxFailedReached) {
			throw new MicroServiceUnavailableException("Service is open !");
		}
	}

	public static void main(String[] args) {

		ConcurrentHashMap<Long, Event> events = new ConcurrentHashMap<Long, Event>();
		events.putIfAbsent(1L, new Event(100L));
		events.putIfAbsent(2L, new Event(110L));
		events.putIfAbsent(3L, new Event(111L));

		long count = events.values().stream().count();

		long now = 120L;
		long nbFailedInTryingPeriod = events.values().stream().filter(event -> now - event.getStartDate() < 20).count();
		System.out.println("nbFailedInTryingPeriod = " + nbFailedInTryingPeriod);

		Map<Boolean, List<Event>> partionedEvents = events.values().stream().collect(Collectors.partitioningBy(event -> now - event.getStartDate() < 20));
		System.out.println("partionedEvents false = " + partionedEvents.get(Boolean.FALSE).size());
		System.out.println("partionedEvents true = " + partionedEvents.get(Boolean.TRUE).size());

		List<Event> expiredEvents = partionedEvents.get(Boolean.FALSE);
		events.values().removeAll(expiredEvents);

		// dates.removeAll(expiredDates);

	}

}
