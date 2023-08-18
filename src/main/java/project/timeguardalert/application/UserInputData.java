package project.timeguardalert.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserInputData {
    private String placeNumber;
    private String placeOptionNumber;
    private String endDateTime;
    private String startDateTime;
}
