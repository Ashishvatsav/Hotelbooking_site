package com.example.DemoHotelBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.DemoHotelBooking.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocationContainingIgnoreCase(String location);
    List<Hotel> findByHotelNameContainingIgnoreCase(String hotelName);

    @Query("SELECT DISTINCT h.location FROM Hotel h")
    List<String> findUniqueLocations();
}
