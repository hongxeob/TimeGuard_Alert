package project.timeguardalert.application;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.timeguardalert.application.dto.NotificationResponse;
import project.timeguardalert.infrastructure.NotificationFeignClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final NotificationFeignClient notificationFeignClient;
	private final UserInputData userInputData;

	public List<NotificationResponse> getHourlySchedules() {
		String placeNumber = userInputData.getPlaceNumber();
		String placeOptionNumber = userInputData.getPlaceOptionNumber();
		String endDateTime = userInputData.getEndDateTime();
		String startDateTime = userInputData.getStartDateTime();

		log.info("endDataTime => {}, startDataTime => {}, placeNumber => {}, placeOptionNumber => {}",
			endDateTime, startDateTime, placeNumber, placeOptionNumber);

		return notificationFeignClient.getHourlySchedules("ko", endDateTime, startDateTime, placeNumber, placeOptionNumber);
	}
}
