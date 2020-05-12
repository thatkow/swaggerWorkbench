package io.swagger.api;

import org.springframework.core.io.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.apidefinitions.IVariantFileApiController;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-05-11T06:47:04.949Z")
@Controller
@Configuration()
public class VariantFileApiController implements VariantFileApi {

    public ResponseEntity<Void> variantFileDelete(@NotNull @ApiParam(value = "Reference label for this file", required = true) @Valid @RequestParam(value = "label", required = true) String label) {
        return mapper.variantFileDelete(label);
    }

    public ResponseEntity<Void> variantFileGet(@NotNull @ApiParam(value = "Reference label for this file", required = true) @Valid @RequestParam(value = "label", required = true) String label) {
        return mapper.variantFileGet(label);
    }

    public ResponseEntity<Void> variantFilePost(@NotNull @ApiParam(value = "Reference label for this file", required = true) @Valid @RequestParam(value = "label", required = true) String label, @ApiParam(value = "Must contain columns MarkerID, Ref, Alt, Position, Chromosome, Pos") @Valid @RequestPart(value = "variantFile", required = true) MultipartFile variantFile) {
        return mapper.variantFilePost(label, variantFile);
    }

    @Autowired()
    private IVariantFileApiController mapper;
}

