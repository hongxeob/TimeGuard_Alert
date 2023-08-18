package project.timeguardalert.application.dto;

public record NotificationResponse(boolean isUnitSaleDay, String unitStartTime, int unitBookingCount) {
}
