package project.timeguardalert.application;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import project.timeguardalert.config.TelegramProperties;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramService extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;
    private final UserInputData userInputData;
    private final List<UserInputData> userInputDataList = new ArrayList<>(); // 사용자 입력 데이터 리스트
    private final Logger log = LoggerFactory.getLogger(getClass());


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
            if (text.startsWith("/setValues")) {
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
                    } else {
                        throw new IllegalArgumentException("잘못된 값을 입력하셨습니다.");
                    }
                }
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
}
