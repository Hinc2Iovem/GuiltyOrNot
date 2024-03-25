package hinc.come.guiltyornot.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto {
    @NonNull
    Long id;

    @NonNull
    String type;

    @NonNull
    Integer points;

    @NonNull
    String text;

    @NonNull
    Instant time;
}
