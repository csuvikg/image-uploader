package hu.ponte.hr.controller.upload;

import hu.ponte.hr.model.Image;
import hu.ponte.hr.services.ImageStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api/file")
public class UploadController {
    final ImageStore imageStore;

    public UploadController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @PostMapping(value = "post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Image> handleFormUpload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        try {
            Image image = imageStore.save(file);
            String path = request.getRequestURI() + '/' + image.getId().toString();

            return ResponseEntity.created(URI.create(path)).body(image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
