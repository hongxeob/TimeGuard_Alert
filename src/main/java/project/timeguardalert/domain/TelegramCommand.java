package project.timeguardalert.domain;

public enum TelegramCommand {
	CANCEL("/취소"),
	WAIT("/대기");

	private final String text;

	TelegramCommand(String text) {
		this.text = text;
	}

	public static TelegramCommand from(String text) {
		for (TelegramCommand command : TelegramCommand.values()) {
			if (command.text.equals(text)) {
				return command;
			}
		}
		return null;
	}
}
