package poc;

import java.util.ArrayList;
import java.util.List;

public class Client {

	public static void main(String[] args) {

		Microservice microservice = new LnsMicroservice();
		// microservice.ws(createCluster).header("Authorization", "").body(clusterDto).execute();

		Context context = new Context();

		Response response = microservice.execute(context);
		int status = response.getStatus();
		ClusterDto clusterDto = (ClusterDto) response.getResult();

		// en cas d'erreur : rollback
		response.rollback();
		// microservice.ws(deleteCluster).header("Authorization", "").path("clusterId", clusterId).body(clusterDto).execute();


		Class<?> result = null;

		result = ClusterDto.class;

		List<ClusterDto> result2 = new ArrayList<ClusterDto>();


		System.err.println(result2.getClass());
		System.err.println(result2.getClass().getEnclosingClass());


	}

}
