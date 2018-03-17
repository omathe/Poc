package poc.microservice;

import poc.ClusterDto;
import poc.PaginatedDto;

public interface LnsWebServices {

	MicroWebService GET_APPLICATION = MicroWebService.name("getApplication").path("/application");
	MicroWebService GET_CLUSTERS = MicroWebService.name("getClusters").path("/application/clusters").returnType(ClusterDto.class);
	MicroWebService GET_CLUSTER = MicroWebService.name("getCluster").path("/application/clusters/{clusterId}").returnType(PaginatedDto.class).returnTypeParameter(ClusterDto.class);

}
