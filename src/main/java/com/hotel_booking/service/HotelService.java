package com.hotel_booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel_booking.entity.Hotel;
import com.hotel_booking.repository.HotelRepository;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
}
