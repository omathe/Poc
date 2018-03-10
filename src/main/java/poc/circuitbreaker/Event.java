package poc.circuitbreaker;

public class Event {

	private enum Status {
		OK, KO;
	}

	private Long date;
	private String status;
	private String deliveryStatus;

	public Event(Long date) {
		super();
		this.date = date;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
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
