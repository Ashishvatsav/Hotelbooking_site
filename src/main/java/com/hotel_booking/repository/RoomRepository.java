package com.hotel_booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId);

    List<Room> findByAvailable(Boolean available);

    List<Room> findByRoomType(String roomType);
}
