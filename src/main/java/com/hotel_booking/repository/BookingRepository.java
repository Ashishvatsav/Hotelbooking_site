package com.hotel_booking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(Long roomId, LocalDate checkIn, LocalDate checkOut);
}
