package poc;

public class LnsMicroservice implements Microservice {

	@Override
	public Response execute(Context context) {

		// request
		// POST /clusters
		// PUT /clusters/{clusterId}
		// parameter BODY (pas d'id)
		// parameter PATH (id fourni)

		// response
		// retrieve id (from Location header ?, DTO ?)
		//

		return null;
	}

	@Override
	public boolean isConfigured() {
		return true;
	}

}
