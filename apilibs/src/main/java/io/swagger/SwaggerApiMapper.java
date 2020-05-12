package io.swagger;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.apidefinitions.ISwaggerApiEntity;

@Configuration
public class SwaggerApiMapper implements ISwaggerApiEntity {


private static final String NOT_YET_IMPLEMENTED = "Not yet implemented";


//	private static EntityStore entityStore;
//
//
//	@Autowired
//	public void setEntityStore(EntityStore store) {
//		this.entityStore = store;
//	}


	@Override
	public ResponseEntity<Void> variantFileDelete(String label) {
		throw new RuntimeException(NOT_YET_IMPLEMENTED);
	}


	@Override
	public ResponseEntity<Void> variantFileGet(String label) {
		throw new RuntimeException(NOT_YET_IMPLEMENTED);
	}


	@Override
	public ResponseEntity<Void> variantFilePost(String label, MultipartFile variantFile) {
		throw new RuntimeException(NOT_YET_IMPLEMENTED);
	}

}
