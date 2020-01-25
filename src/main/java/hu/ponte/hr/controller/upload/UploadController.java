package hu.ponte.hr.controller.upload;

import hu.ponte.hr.model.Image;
import hu.ponte.hr.services.ImageStore;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api/file")
public class UploadController {
    final static long limit = 2 * 1024 * 1024; // 2MB

    final ImageStore imageStore;

    public UploadController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @PostMapping(value = "post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Image> handleFormUpload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        boolean isImage = file.getContentType().split("/")[0].equals("image");
        if (file.getSize() > limit) {
            throw new MaxUploadSizeExceededException(limit);
        }

        if (!isImage) {
            throw new MultipartException("Invalid file type");
        }

        Image image = imageStore.save(file);
        String path = request.getRequestURI() + '/' + image.getId().toString();

        return ResponseEntity.created(URI.create(path)).body(image);
    }
}
