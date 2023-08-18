package project.timeguardalert.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import project.timeguardalert.application.dto.NotificationResponse;

import java.util.List;

@FeignClient(name = "naverNotification", url = "https://api.booking.naver.com")
public interface NotificationFeignClient {

    @GetMapping("/v3.0/businesses/{placeNumber}/biz-items/{placeOptionNumber}/hourly-schedules")
    List<NotificationResponse> getHourlySchedules(
            @RequestParam("lang") String lang,
            @RequestParam("endDateTime") String endDateTime,
            @RequestParam("startDateTime") String startDateTime,
            @PathVariable("placeNumber") String placeNumber,
            @PathVariable("placeOptionNumber") String placeOptionNumber
    );
}