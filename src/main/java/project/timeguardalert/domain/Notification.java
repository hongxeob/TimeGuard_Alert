package project.timeguardalert.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;
    private String placeNumber;
    private String placeOptionNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSurveyTime;
    private int isReservationStatus;

    @Builder
    public Notification(String placeNumber, String placeName, String placeOptionNumber, LocalDateTime reservationTime, LocalDateTime lastSurveyTime, int isReservationStatus) {
        this.placeNumber = placeNumber;
        this.placeName = placeName;
        this.placeOptionNumber = placeOptionNumber;
        this.reservationTime = reservationTime;
        this.lastSurveyTime = lastSurveyTime;
        this.isReservationStatus = isReservationStatus;
    }
}
