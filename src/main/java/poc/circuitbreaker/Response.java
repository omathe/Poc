package poc.circuitbreaker;

public class Response {

	private enum Status {
		OK, KO;
	}

	private enum DeliveryStatus {
		ON_TIME, LATE;
	}

	private Long date;
	private Status status;
	private DeliveryStatus deliveryStatus;

	public Response(Long date) {
		super();
		this.date = date;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public boolean isSuccessful() {

		return status != null && status.equals(Status.OK);
	}

	public boolean hasFailed() {

		return status != null && status.equals(Status.KO);
	}

	public boolean isOnTime() {

		return deliveryStatus != null && deliveryStatus.equals(DeliveryStatus.ON_TIME);
	}

	public boolean isLate() {

		return deliveryStatus != null && deliveryStatus.equals(DeliveryStatus.LATE);
	}

	public void setSuccessful() {
		this.status = Status.OK;
	}

	public void setFailed() {
		this.status = Status.KO;
	}

	public boolean hasNoStatus() {
		return status == null;
	}

	public boolean hasNoDeliveryStatus() {
		return deliveryStatus == null;
	}

	public void setLate() {
		this.deliveryStatus = DeliveryStatus.LATE;
	}

	public void setOnTime() {
		this.deliveryStatus = DeliveryStatus.ON_TIME;
	}

	@Override
	public String toString() {
		return "Response [date=" + date + ", status=" + status + ", deliveryStatus=" + deliveryStatus + "]";
	}

}
