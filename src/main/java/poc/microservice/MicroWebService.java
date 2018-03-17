package poc.microservice;

public class MicroWebService {

	private String name;
	private String method;
	private String path;
	private Class<?> returnType;
	private Class<?> returnTypeParameter;
	private Class<?> errorType;


//	private TypeReference<?> error;
//	private List<String> pathIdentifiers;

	private MicroWebService(final String name) {
		this.name = name;
	}

	public static MicroWebService name(final String name) {
		return new MicroWebService(name);
	}

	public MicroWebService method(final String method) {
		this.method = method;
		return this;
	}

	public MicroWebService path(final String path) {
		this.path = path;
		return this;
	}

	public MicroWebService returnType(final Class<?> returnType) {
		this.returnType = returnType;
		return this;
	}

	public MicroWebService returnTypeParameter(final Class<?> returnTypeParameter) {
		this.returnTypeParameter = returnTypeParameter;
		return this;
	}

	public MicroWebService errorType(final Class<?> errorType) {
		this.errorType = errorType;
		return this;
	}




//	public MicroWebService pathIdentifier(final String pathIdentifier) {
//		if (pathIdentifiers == null) {
//			pathIdentifiers = new ArrayList<String>();
//		}
//		pathIdentifiers.add(pathIdentifier);
//		return this;
//	}
//
//	public MicroWebService result(final Class<?> result) {
//		this.result = result;
//		return this;
//	}
//
//	public MicroWebService error(final TypeReference<?> error) {
//		this.error = error;
//		return this;
//	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public Class<?> getReturnTypeParameter() {
		return returnTypeParameter;
	}

	public Class<?> getErrorType() {
		return errorType;
	}

	public String getName() {
		return name;
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

//	public Class<?> getResult() {
//		return result;
//	}
//
//	public TypeReference<?> getError() {
//		return error;
//	}

}
