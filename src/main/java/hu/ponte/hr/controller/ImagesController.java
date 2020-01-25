package hu.ponte.hr.controller;

import hu.ponte.hr.model.Image;
import hu.ponte.hr.services.ImageStore;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    private final ImageStore imageStore;

    public ImagesController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        return imageStore.findAllMeta();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        Optional<Image> image = imageStore.getById(id);

        if (image.isPresent()) {
            Image imageContent = image.get();
            InputStream in = new ByteArrayInputStream(imageContent.getImage().getData());
            response.setContentType(imageContent.getMimeType());
            response.setContentLength((int) imageContent.getSize());
            IOUtils.copy(in, response.getOutputStream());
        }
    }
}
