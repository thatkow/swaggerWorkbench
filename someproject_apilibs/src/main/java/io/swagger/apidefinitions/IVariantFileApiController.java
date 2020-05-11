package io.swagger.apidefinitions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IVariantFileApiController {

    ResponseEntity<Void> variantFileDelete(String label);

    ResponseEntity<Void> variantFileGet(String label);

    ResponseEntity<Void> variantFilePost(String label, MultipartFile variantFile);
}

