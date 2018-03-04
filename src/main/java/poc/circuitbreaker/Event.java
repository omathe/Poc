package poc.circuitbreaker;

public class Event {

	private Long startDate;
	private Long endDate;
	private String status;

	public Event(Long startDate) {
		super();
		this.status = "PENDING";
		this.startDate = startDate;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Event [startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}

}
