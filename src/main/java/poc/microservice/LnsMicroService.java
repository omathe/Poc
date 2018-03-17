package poc.microservice;

public class LnsMicroService implements MicroService {

	@Override
	public String getName() {
		return "LNS";
	}

	@Override
	public String getBaseUrl() {
		return "http://192.168.4.23:8081";
	}

	@Override
	public boolean isConfigured() {

		return !getBaseUrl().isEmpty();
	}

}
