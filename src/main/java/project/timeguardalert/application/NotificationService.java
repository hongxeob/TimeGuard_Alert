package project.timeguardalert.application;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.timeguardalert.application.dto.NotificationResponse;
import project.timeguardalert.infrastructure.NotificationFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationFeignClient notificationFeignClient;
    private final TelegramService telegramService;
    private final UserInputData userInputData;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Transactional(readOnly = true)
    public List<NotificationResponse> getHourlySchedules() {
        String placeNumber = userInputData.getPlaceNumber();
        String placeOptionNumber = userInputData.getPlaceOptionNumber();
        String endDateTime = userInputData.getEndDateTime();
        String startDateTime = userInputData.getStartDateTime();

        log.info("endDataTime => {}, startDataTime => {}, placeNumber => {}, placeOptionNumber => {}",
                endDateTime, startDateTime, placeNumber, placeOptionNumber);

        return notificationFeignClient.getHourlySchedules("ko", endDateTime, startDateTime, placeNumber, placeOptionNumber);

    }

    @Scheduled(fixedRate = 1000 * 60 / 10) // 스케줄러, 1분마다 실행
    public void hourlySchedulesCheck() {

        if (telegramService.isInputDataEmpty()) {
            return;
        }

        List<NotificationResponse> schedules = getHourlySchedules();

        if (schedules.get(0).unitBookingCount() == 0) {
            telegramService.sendNotification("지금 예약하세요!!");
        }
    }
}
