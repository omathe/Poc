package poc.microservice;

public class Client {

	public static void main(String[] args) {


		Parameters parameters = Parameters.build().addAll(Parameter.header("Authorization", "bearer 132"), Parameter.path("endpointId", "DDDDDDDDDDDDDDDD"));

		Parameters parameters2 = Parameters.build()
				.addHeader("Authorization", "bearer 132")
				.addQuery("fields", "name");

	}

}
