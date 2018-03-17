package poc.microservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import poc.microservice.Parameter.Location;
import poc.microservice.Parameter.Type;

public class Parameters {

	private List<Parameter> parameters;

	private Parameters() {
		parameters = new ArrayList<>();
	}

	public static Parameters build() {
		return new Parameters();
	}

	public Parameters addHeader(final String name, final String value) {

		parameters.add(new Parameter(Type.TEXT, Location.HEADER, name, value));
		return this;
	}

	public Parameters addQuery(final String name, final String value) {

		parameters.add(new Parameter(Type.TEXT, Location.QUERY, name, value));
		return this;
	}

	public Parameters addPath(final String name, final String value) {

		parameters.add(new Parameter(Type.TEXT, Location.PATH, name, value));
		return this;
	}

	public Parameters addAll(final Parameter... p) {

		List<Parameter> list = Arrays.stream(p).collect(Collectors.toList());
		parameters.addAll(list);
		return this;
	}

//	public HttpHeaders getHeaders() {
//
//		HttpHeaders headers = parameters == null ? null : new HttpHeaders();
//		if (parameters != null) {
//			parameters.stream().filter(p -> p.getLocation().equals(Parameter.Location.HEADER.name())).forEach(p -> headers.add(p.getName(), p.getValue()));
//		}
//		return headers;
//	}

//	public MultiValueMap<String, String> getQueryParameters() {
//
//		MultiValueMap<String, String> queryParameters = parameters == null ? null : new LinkedMultiValueMap<>();
//		if (parameters != null) {
//			parameters.stream().filter(p -> p.getLocation().equals(Parameter.Location.QUERY.name())).forEach(p -> queryParameters.add(p.getName(), p.getValue()));
//		}
//		return queryParameters;
//	}

}
