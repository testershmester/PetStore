package by.teachmeskills.api.dto.pet;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pet {
    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public Status status;
}
