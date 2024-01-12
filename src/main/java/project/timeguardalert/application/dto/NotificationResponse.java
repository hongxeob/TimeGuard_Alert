package project.timeguardalert.application.dto;

public record NotificationResponse(String placeNumber, String bizItemId,
                                   int maxBookingCount, int unitBookingCount) {
}
