package hu.ponte.hr.repository;

import hu.ponte.hr.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image, UUID> {
    // Queries all data but omits the image binary field, so getting metadata is cheaper
    @Query(value="{}", fields="{'image': 0}")
    List<Image> findAllOmitImage();
}
