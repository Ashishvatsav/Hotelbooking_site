package com.hotel_booking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hotel_booking.dto.HotelRequestDTO;
import com.hotel_booking.dto.HotelResponseDTO;
import com.hotel_booking.entity.Hotel;
import com.hotel_booking.exception.ResourceNotFoundException;
import com.hotel_booking.repository.HotelRepository;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponseDTO createHotel(HotelRequestDTO dto) {
        if (hotelRepository.existsByNameAndLocation(dto.getName(), dto.getLocation())) {
            throw new com.hotel_booking.exception.DuplicateResourceException("Hotel already exists with name and location");
        }
        Hotel hotel = mapToEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return mapToDTO(savedHotel);
    }

    public List<HotelResponseDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public HotelResponseDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return mapToDTO(hotel);
    }

    public HotelResponseDTO updateHotel(Long id, HotelRequestDTO dto) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        if (hotelRepository.existsByNameAndLocationAndIdNot(existingHotel.getName(), dto.getLocation(), id)) {
            throw new com.hotel_booking.exception.DuplicateResourceException("Another hotel already exists with the same name and location");
        }

        existingHotel.setLocation(dto.getLocation());
        existingHotel.setDescription(dto.getDescription());
        existingHotel.setAmenities(dto.getAmenities());
        existingHotel.setPricePerNight(dto.getPricePerNight());
        existingHotel.setRating(dto.getRating());
        existingHotel.setAvailable(dto.getAvailable());

        return mapToDTO(hotelRepository.save(existingHotel));
    }

    public List<HotelResponseDTO> getHotelsByLocation(String location) {
        return hotelRepository.findByLocationIgnoreCase(location).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelResponseDTO> getHotelsByRating(Double rating) {
        return hotelRepository.findByRatingGreaterThanEqual(rating).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelResponseDTO> getAvailableHotels() {
        return hotelRepository.findByAvailable(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelResponseDTO> getHotelsByPriceRange(Double minPrice, Double maxPrice) {
        return hotelRepository.findByPricePerNightBetween(minPrice, maxPrice).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelResponseDTO> getHotelsByAmenity(String amenity) {
        return hotelRepository.findByAmenitiesContainingIgnoreCase(amenity).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Hotel mapToEntity(HotelRequestDTO dto) {
        return Hotel.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .amenities(dto.getAmenities())
                .pricePerNight(dto.getPricePerNight())
                .rating(dto.getRating())
                .available(dto.getAvailable())
                .build();
    }

    private HotelResponseDTO mapToDTO(Hotel hotel) {
        return HotelResponseDTO.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .location(hotel.getLocation())
                .description(hotel.getDescription())
                .amenities(hotel.getAmenities())
                .pricePerNight(hotel.getPricePerNight())
                .rating(hotel.getRating())
                .available(hotel.getAvailable())
                .build();
    }
}
