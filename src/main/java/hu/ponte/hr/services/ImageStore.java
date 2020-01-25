package hu.ponte.hr.services;

import hu.ponte.hr.model.Image;
import hu.ponte.hr.repository.ImageRepository;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageStore {
    final ImageRepository repository;

    final SignService signService;

    public ImageStore(ImageRepository repository, SignService signService) {
        this.repository = repository;
        this.signService = signService;
    }

    public Image save(MultipartFile file) throws IOException {
        byte[] data = file.getBytes();

        Image image = Image.builder()
                .id(UUID.randomUUID())
                .name(file.getName())
                .mimeType(file.getContentType())
                .size(file.getSize())
                .digitalSign(signService.sign(data))
                .image(new Binary(data))
                .build();
        
        return repository.save(image);
    }
}
