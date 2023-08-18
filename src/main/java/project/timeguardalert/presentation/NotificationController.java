package project.timeguardalert.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.timeguardalert.application.NotificationService;
import project.timeguardalert.application.dto.NotificationResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/get-hourly-schedules")
    public List<NotificationResponse> getHourlySchedules(
            @RequestParam String placeNumber,
            @RequestParam String placeOptionNumber,
            @RequestParam String endDateTime,
            @RequestParam String startDateTime) {

        return notificationService.getHourlySchedules(placeNumber, placeOptionNumber, endDateTime, startDateTime);
    }
}
