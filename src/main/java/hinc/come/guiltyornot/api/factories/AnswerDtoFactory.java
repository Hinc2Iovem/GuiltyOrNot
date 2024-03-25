package hinc.come.guiltyornot.api.factories;

import hinc.come.guiltyornot.api.dto.AnswerDto;
import org.springframework.stereotype.Component;

@Component
public class AnswerDtoFactory {

    public AnswerDto makeAnswerDto(AnswerDto entity) {

        return AnswerDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .text(entity.getText())
                .time(entity.getTime())
                .points(entity.getPoints())
                .build();
    }
}
