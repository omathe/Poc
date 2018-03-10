package poc.circuitbreaker;

public interface Service {

	void execute(String number) throws Exception;

	void callBack();

}
