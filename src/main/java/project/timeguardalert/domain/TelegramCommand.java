package project.timeguardalert.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum TelegramCommand {
	CANCEL("/취소"),
	WAIT("/대기");

	private final String text;

	TelegramCommand(String text) {
		this.text = text;
	}

	public static TelegramCommand from(String text) {
		return Arrays.stream(TelegramCommand.values())
			.filter(command -> command.text.equals(text))
			.findFirst()
			.orElseThrow(() -> {
				log.warn("GET:READ:NOT_FOUND_TELEGRAM_COMMAND : {}", text);
				return new IllegalArgumentException("잘못된 커맨드 입력 =>  " + text, new RuntimeException(text));
			});
	}
}
