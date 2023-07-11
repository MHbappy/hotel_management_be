package com.hotel.hotel_management;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.model.*;
import com.hotel.hotel_management.repository.*;
import com.hotel.hotel_management.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.*;

@SpringBootApplication
@OpenAPIDefinition
public class HotelApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private PaymentStatusRepository paymentStatusRepository;
	@Autowired
	private RoomAvailabilityStatusRepository roomAvailabilityStatusRepository;
	@Autowired
	private RoomStatusRepository roomStatusRepository;
	@Autowired
	private RoomTypeRepository roomTypeRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) {
		List<PaymentStatus> paymentStatusArrayList = Arrays.asList(new PaymentStatus(1l, "CANCELLED", true), new PaymentStatus(2l, "PAID", true));
		List<RoomAvailabilityStatus> roomAvailabilityStatusList = Arrays.asList(new RoomAvailabilityStatus(1l, "RESERVED", true), new RoomAvailabilityStatus(2l, "OPEN", true));
		List<RoomStatus> roomStatusList = Arrays.asList(new RoomStatus(1l, "READY", true), new RoomStatus(2l, "NOT READY", true));
		List<RoomType> roomTypeList = Arrays.asList(new RoomType(1l, "GORGEOUS", true), new RoomType(2l, "MID", true), new RoomType(3l, "LOW", true));

		paymentStatusRepository.saveAll(paymentStatusArrayList);
		roomAvailabilityStatusRepository.saveAll(roomAvailabilityStatusList);
		roomStatusRepository.saveAll(roomStatusList);
		roomTypeRepository.saveAll(roomTypeList);

		Optional<Roles> adminRoles = rolesRepository.findById(Constrains.admin);
		Optional<Roles> guestRoles = rolesRepository.findById(Constrains.guest);
		Optional<Roles> staffRoles = rolesRepository.findById(Constrains.staff);

		if (!adminRoles.isPresent()){
			rolesRepository.save(new Roles(Constrains.admin));
		}
		if (!guestRoles.isPresent()){
			rolesRepository.save(new Roles(Constrains.guest));
		}
		if (!staffRoles.isPresent()){
			rolesRepository.save(new Roles(Constrains.staff));
		}

		Users admin = userService.searchWithoutException("admin@localhost");
		Users guest = userService.searchWithoutException("guest@localhost");
		Users staff = userService.searchWithoutException("staff@localhost");

		if (admin == null){
			Users newAdmin = new Users();
			newAdmin.setPassword("123456");
			newAdmin.setEmail("admin@localhost");
			newAdmin.setRoles(new HashSet<>(Arrays.asList(new Roles(Constrains.admin))));
			newAdmin.setIsActive(true);
			userService.signup(newAdmin);
		}

		if (guest == null){
			Users newGuest = new Users();
			newGuest.setPassword("123456");
			newGuest.setEmail("guest@localhost");
			newGuest.setRoles(new HashSet<>(Arrays.asList(new Roles(Constrains.guest))));
			newGuest.setIsActive(true);
			userService.signup(newGuest);
		}

		if (staff == null){
			Users newStaff = new Users();
			newStaff.setPassword("123456");
			newStaff.setEmail("staff@localhost");
			newStaff.setRoles(new HashSet<>(Arrays.asList(new Roles(Constrains.staff))));
			newStaff.setIsActive(true);
			userService.signup(newStaff);
		}
	}
}
