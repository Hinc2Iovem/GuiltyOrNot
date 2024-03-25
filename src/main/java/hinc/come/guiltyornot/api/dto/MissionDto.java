package hinc.come.guiltyornot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mission {
    @NonNull
    Long id;

    @NonNull
    String title;

    @NonNull
    String description;

    @NonNull
    Integer defeat_exp;

    @NonNull
    Integer defeat_money;

    @NonNull
    Integer reward_exp;

    @NonNull
    Integer reward_money;

    @JsonProperty("level_of_difficulty")
    @NonNull
    Integer levelOfDifficulty;
}
