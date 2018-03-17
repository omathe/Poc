package poc.microservice;

public interface LnsWebServices {

	MicroWebService GET_APPLICATION = MicroWebService.name("getApplication").path("/application");
	MicroWebService GET_CLUSTERS = MicroWebService.name("getClusters").path("/application/clusters");
	MicroWebService GET_CLUSTER = MicroWebService.name("getCluster").path("/application/clusters/{clusterId}");

}
