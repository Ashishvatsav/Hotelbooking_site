package com.hotel_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponseDTO {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String amenities;
    private Double pricePerNight;
    private Double rating;
    private Boolean available;
}
