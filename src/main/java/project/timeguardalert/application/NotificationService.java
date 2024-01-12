package project.timeguardalert.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.timeguardalert.application.dto.NotificationResponse;
import project.timeguardalert.infrastructure.NotificationFeignClient;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationFeignClient notificationFeignClient;
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
}
