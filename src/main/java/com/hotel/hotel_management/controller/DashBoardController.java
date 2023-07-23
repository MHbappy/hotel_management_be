package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.projection.DashBoardCardInfo;
import com.hotel.hotel_management.model.projection.DashBoardCardInfoImpl;
import com.hotel.hotel_management.model.projection.DayWiseReservationCount;
import com.hotel.hotel_management.model.projection.DayWiseReservationCountImpl;
import com.hotel.hotel_management.repository.ReservationRepository;
import com.hotel.hotel_management.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {

    private final ReservationRepository reservationRepository;

    @GetMapping("/card-info")
    List<DashBoardCardInfo> getDashBoardCardInfo(){
        List<String> cardList = new ArrayList<>();
        cardList.add("NOT READY");
        cardList.add("GORGEOUS");
        cardList.add("READY");
        cardList.add("RESERVED");
        cardList.add("TOTAL ROOM");
        cardList.add("MID");

        List<DashBoardCardInfo> dashBoardCardInfos = reservationRepository.getDashBoardCardInfo();
        List<DashBoardCardInfo> newDashboardInfo = new ArrayList<>();
        for (String dashBoardCardInfo : cardList){
            boolean isExist = false;
            for (DashBoardCardInfo dashBoardCardInfo1 : dashBoardCardInfos){
                if (dashBoardCardInfo1.getName().equals(dashBoardCardInfo)){
                    newDashboardInfo.add(dashBoardCardInfo1);
                    isExist = true;
                    break;
                }
            }
            if (!isExist){
                newDashboardInfo.add(new DashBoardCardInfoImpl(0, dashBoardCardInfo));
            }
        }
        return newDashboardInfo;
    }

    @GetMapping("/monthly-reservation-info")
    List<DayWiseReservationCount> getMonthlyReservationInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<DayWiseReservationCount> dayWiseReservationCounts = reservationRepository.getDayWiseReservation();
        List<DayWiseReservationCount> newDayWiseReservationCount = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        // Print the last 30 days
        for (int i = 0; i <= 30; i++) {
            LocalDate previousDate = currentDate.minusDays(i);
            String formattedDate = formatter.format(previousDate);
            Boolean isExist = false;

            for (DayWiseReservationCount dayWiseReservationCount: dayWiseReservationCounts){
                if (dayWiseReservationCount.getStartDate().toString().equals(formattedDate)){
                    newDayWiseReservationCount.add(dayWiseReservationCount);
                    isExist = true;
                    break;
                }
            }
            if (!isExist){
                newDayWiseReservationCount.add(new DayWiseReservationCountImpl(0, formattedDate));
            }

        }
        return newDayWiseReservationCount;
    }

}
