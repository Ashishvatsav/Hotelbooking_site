package com.hotel_booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	List<Hotel> findByLocationIgnoreCase(String location);

	List<Hotel> findByRatingGreaterThanEqual(Double rating);

	List<Hotel> findByAvailable(Boolean available);

	List<Hotel> findByPricePerNightBetween(Double minPrice, Double maxPrice);

	List<Hotel> findByAmenitiesContainingIgnoreCase(String amenities);

	boolean existsByNameAndLocation(String name, String location);

	boolean existsByNameAndLocationAndIdNot(String name, String location, Long id);
}
