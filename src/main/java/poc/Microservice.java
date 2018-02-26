package poc;

public interface Microservice {

	boolean isConfigured();

	Response execute(Context context);

}
