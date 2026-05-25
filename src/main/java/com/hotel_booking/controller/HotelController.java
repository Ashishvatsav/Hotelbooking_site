package com.hotel_booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_booking.dto.HotelRequestDTO;
import com.hotel_booking.dto.HotelResponseDTO;
import com.hotel_booking.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponseDTO createHotel(@Valid @RequestBody HotelRequestDTO dto) {
        return hotelService.createHotel(dto);
    }

    @PutMapping("/{id}")
    public HotelResponseDTO updateHotel(@PathVariable Long id, @Valid @RequestBody HotelRequestDTO dto) {
        return hotelService.updateHotel(id, dto);
    }

    @GetMapping
    public List<HotelResponseDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponseDTO getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/location/{location}")
    public List<HotelResponseDTO> getHotelsByLocation(@PathVariable String location) {
        return hotelService.getHotelsByLocation(location);
    }

    @GetMapping("/rating/{rating}")
    public List<HotelResponseDTO> getHotelsByRating(@PathVariable Double rating) {
        return hotelService.getHotelsByRating(rating);
    }

    @GetMapping("/available")
    public List<HotelResponseDTO> getAvailableHotels() {
        return hotelService.getAvailableHotels();
    }

    @GetMapping("/price")
    public List<HotelResponseDTO> getHotelsByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return hotelService.getHotelsByPriceRange(min, max);
    }

    @GetMapping("/amenities/{amenity}")
    public List<HotelResponseDTO> getHotelsByAmenity(@PathVariable String amenity) {
        return hotelService.getHotelsByAmenity(amenity);
    }
}
