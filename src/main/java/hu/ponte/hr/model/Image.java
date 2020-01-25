package hu.ponte.hr.model;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "images")
@Builder
@Getter
public class Image {
    @Id
    private UUID id;
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;
    private Binary image;
}
