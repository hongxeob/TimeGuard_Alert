package project.timeguardalert.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import project.timeguardalert.application.TelegramService;

@Component
@RequiredArgsConstructor
public class TelegramConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramService telegramService) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(telegramService);
            return api;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
