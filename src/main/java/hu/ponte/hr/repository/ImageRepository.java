package hu.ponte.hr.repository;

import hu.ponte.hr.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}
