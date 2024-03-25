package hinc.come.guiltyornot.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @NonNull
    Long id;

    @NonNull
    String role;

    @NonNull
    String username;

    @NonNull
    String password;

    Integer exp;
    Integer money;
}
