package project.timeguardalert.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.timeguardalert.application.dto.NotificationResponse;
import project.timeguardalert.infrastructure.NotificationFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationFeignClient notificationFeignClient;

    public List<NotificationResponse> getHourlySchedules(String placeNumber, String placeOptionNumber, String endDateTime, String startDateTime) {
        return notificationFeignClient.getHourlySchedules("ko", endDateTime, startDateTime, placeNumber, placeOptionNumber);
    }
}
