package poc.microservice;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parameter {

	public enum Location {
		BODY, HEADER, PATH, QUERY;
	}

	public enum Type {
		TEXT, FILE;
	}

	public static Set<String> locations = Arrays.stream(Location.values()).map(e -> e.name()).collect(Collectors.toSet());
	public static Set<String> types = Arrays.stream(Type.values()).map(e -> e.name()).collect(Collectors.toSet());

	public static Set<String> headerNames = Stream.of("Accept", "Authorization", "Content-Type").collect(Collectors.toSet());

	private String type;
	private String location;
	private String name;
	private String value;

	public Parameter(final Type type, final Location location, final String name, final String value) {
		super();
		this.type = type.name();
		this.location = location.name();
		this.name = name;
		this.value = value;
	}

	public static Parameter header(final String name, final String value) {
		return new Parameter(Type.TEXT, Location.HEADER, name, value);
	}

	public static Parameter query(final String name, final String value) {
		return new Parameter(Type.TEXT, Location.QUERY, name, value);
	}

	public static Parameter path(final String name, final String value) {
		return new Parameter(Type.TEXT, Location.PATH, name, value);
	}

	public Parameter location(final Location location) {
		this.location = location.name();
		return this;
	}

	public Parameter name(final String name) {
		this.name = name;
		return this;
	}

	public Parameter value(final String value) {
		this.value = value;
		return this;
	}

	public String getType() {
		return type;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	boolean isPathParameter() {
		return location.equals(Location.PATH.name());
	}

	boolean isHeaderParameter() {
		return location.equals(Location.HEADER.name());
	}

	boolean isQueryParameter() {
		return location.equals(Location.QUERY.name());
	}

	@Override
	public String toString() {
		return "Parameter [type=" + type + ", location=" + location + ", name=" + name + ", value=" + value + "]";
	}

}
