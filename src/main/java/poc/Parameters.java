package poc;

public class Parameters {

	/*private List<Parameter> parameters;

	private Parameters() {
		parameters = new ArrayList<>();
	}

	public static Parameters create() {
		return new Parameters();
	}

	public Parameters addAll(final Parameter... p) {

		List<Parameter> list = Arrays.stream(p).collect(Collectors.toList());
		parameters.addAll(list);
		return this;
	}

	public HttpHeaders getHeaders() {

		HttpHeaders headers = parameters == null ? null : new HttpHeaders();
		if (parameters != null) {
			parameters.stream()
					.filter(p -> p.getLocation().equals(Parameter.Location.HEADER.name()))
					.forEach(p -> headers.add(p.getName(), p.getValue()));
		}
		return headers;
	}

	public MultiValueMap<String, String> getQueryParameters() {

		MultiValueMap<String, String> queryParameters = parameters == null ? null : new LinkedMultiValueMap<>();
		if (parameters != null) {
			parameters.stream()
					.filter(p -> p.getLocation().equals(Parameter.Location.QUERY.name()))
					.forEach(p -> queryParameters.add(p.getName(), p.getValue()));
		}
		return queryParameters;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}
*/
}
