package project.timeguardalert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.RequiredArgsConstructor;
import project.timeguardalert.application.TelegramBotProcessor;

@Component
@RequiredArgsConstructor
public class TelegramConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBotProcessor telegramBotProcessor) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(telegramBotProcessor);
            return api;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
