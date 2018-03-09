package poc.circuitbreaker;

public class Event {

	private enum Status {
		OK, KO;
	}

	private Long date;
	private String status;
	private String deliveryStatus;

	public Event(Long startDate) {
		super();
		this.date = startDate;
	}

	public Long getDate() {
		return date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Override
	public String toString() {
		return "Event [date=" + date + ", status=" + status + "]";
	}

}
