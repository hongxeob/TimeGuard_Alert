package project.timeguardalert.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.timeguardalert.application.dto.NotificationResponse;
import project.timeguardalert.config.TelegramProperties;
import project.timeguardalert.domain.TelegramCommand;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramService extends TelegramLongPollingBot {

	private final TelegramProperties telegramProperties;
	private final NotificationService notificationService;
	private final UserInputData userInputData;
	private final List<UserInputData> userInputDataList = new ArrayList<>(); // 사용자 입력 데이터 리스트
	private final TaskScheduler scheduler;
	private ScheduledFuture<?> scheduledFuture;

	@Override
	public String getBotUsername() {
		return "reservation_notify_bot";
	}

	@Override
	public String getBotToken() {
		return telegramProperties.getBotToken();
	}

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasMessage() && update.getMessage().hasText()) {
			String text = update.getMessage().getText();
			String[] tokens = text.split(" ");
			TelegramCommand command = TelegramCommand.from(tokens[0]);
			switch (command) {
				case CANCEL:
					cancelWaiting();
					break;
				case WAIT:
					startWaiting(text);
					break;
				default:
					sendNotification("알 수 없는 명령어입니다.");
			}
		}
	}

	public void sendNotification(String message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(telegramProperties.getChatId());
		sendMessage.setText(message);

		try {
			execute(sendMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isInputDataEmpty() {
		if (!userInputDataList.isEmpty()) {
			return false;
		}

		return true;
	}

	public void startScheduler() {
		scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				hourlySchedulesCheck();
			}
		}, 1000 * 3);// 30초마다 실행
		log.info("스케줄러 호출");
	}

	public void hourlySchedulesCheck() {
		log.info("대기중");
		if (isInputDataEmpty()) {
			return;
		}

		List<NotificationResponse> schedules = notificationService.getHourlySchedules();

		if (schedules.get(0).unitBookingCount() == 0) {
			sendNotification(
				"지금 예약하세요!! \n" +
					"https://booking.naver.com/booking/13/bizes/" +
					"" + userInputData.getPlaceNumber() +
					"/items/" +
					"" + userInputData.getPlaceOptionNumber() +
					"?area=bmp&service-target=map-pc");
			log.info("메시지 발송 완료");
			stopScheduler();
			sendNotification("취소 예약 알림이 완료되었습니다!");
		}
	}

	public void stopScheduler() {
		if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
			scheduledFuture.cancel(true);
			log.info("스케줄러가 중지되었습니다.");
		} else {
			log.info("이미 스케줄러가 중지되었거나 아직 시작되지 않았습니다.");
		}

		// 사용자 입력 데이터 초기화
		userInputDataList.clear();
		log.info("사용자 입력 데이터가 초기화되었습니다.");
	}

	private void cancelWaiting() {
		stopScheduler();
		sendNotification("대기가 취소되었습니다.");
	}

	private void startWaiting(String text) {
		String[] tokens = text.split(" ");
		if (tokens.length >= 2) {
			String inputValues = tokens[1];
			String[] values = inputValues.split(",");

			if (values.length == 4) {
				String placeNumber = values[0].trim();
				String placeOptionNumber = values[1].trim();
				String endDateTime = values[2].trim();
				String startDateTime = values[3].trim();

				userInputData.setPlaceNumber(placeNumber);
				userInputData.setPlaceOptionNumber(placeOptionNumber);
				userInputData.setEndDateTime(endDateTime);
				userInputData.setStartDateTime(startDateTime);

				userInputDataList.add(userInputData);
				startScheduler();
			} else {
				throw new IllegalArgumentException("잘못된 값을 입력하셨습니다.");
			}
		} else {
			throw new IllegalArgumentException("잘못된 값을 입력하셨습니다.");
		}
	}
}
