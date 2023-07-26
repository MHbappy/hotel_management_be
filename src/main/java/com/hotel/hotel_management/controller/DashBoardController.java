package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.projection.DashBoardCardInfo;
import com.hotel.hotel_management.model.projection.DayWiseReservationCount;
import com.hotel.hotel_management.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @GetMapping("/card-info")
    List<DashBoardCardInfo> getDashBoardCardInfo(){
        return dashBoardService.getDashBoardCardInfo();
    }

    @GetMapping("/monthly-reservation-info")
    List<DayWiseReservationCount> getMonthlyReservationInfo(){
        return dashBoardService.getMonthlyReservationInfo();
    }
}
