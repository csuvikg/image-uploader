package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.model.Image;
import hu.ponte.hr.repository.ImageRepository;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
                .name(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .size(file.getSize())
                .digitalSign(SignService.sign(data))
                .image(new Binary(data))
                .build();

        return repository.save(image);
    }

    public List<ImageMeta> findAllMeta() {
        return repository.findAllOmitImage().stream()
                .map(Image::toImageMeta)
                .collect(Collectors.toList());
    }

    public Optional<Image> getById(String id) {
        UUID imageId;
        try {
            imageId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return repository.findById(imageId);
    }
}
