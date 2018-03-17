package poc.microservice;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public void test() throws Exception {

		java.lang.reflect.Type type1 = null;


		ObjectMapper objectMapper = new ObjectMapper();

		JavaType valueType;

		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);

		// 2
		JavaType type2 = objectMapper.getTypeFactory().constructType(String.class);
		byte[] bytes = null;
		objectMapper.readValue(bytes, type2);

		// method 3 OK
		Class<?> returnType = Long.class;
		JavaType type3 = objectMapper.getTypeFactory().constructType(returnType);

		// method 4
		List<Long> returnType4;
		JavaType type4 = objectMapper.getTypeFactory().constructParametricType(List.class, Long.class);

		// method 5
		// .returnType(ClusterDto)
		// .returnType(PaginatedDto).returnTypeParameter(ClusterDto)
	}


	public static void main(String[] args) {


		ObjectMapper objectMapper = new ObjectMapper();


		try {
			byte[] bytes = null;
			objectMapper.readValue(bytes, new TypeReference<String>() {	});



			Class<?> theClass = String.class;
			//objectMapper.readValue(bytes, new TypeReference<theClass>() {	});


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
