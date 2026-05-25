package com.example.DemoHotelBooking.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.DemoHotelBooking.entity.Amenity;
import com.example.DemoHotelBooking.entity.Hotel;
import com.example.DemoHotelBooking.entity.Promotion;
import com.example.DemoHotelBooking.entity.Room;
import com.example.DemoHotelBooking.entity.RoomCategory;
import com.example.DemoHotelBooking.entity.User;
import com.example.DemoHotelBooking.repository.AmenityRepository;
import com.example.DemoHotelBooking.repository.HotelRepository;
import com.example.DemoHotelBooking.repository.PromotionRepository;
import com.example.DemoHotelBooking.repository.RoomCategoryRepository;
import com.example.DemoHotelBooking.repository.RoomRepository;
import com.example.DemoHotelBooking.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Initializing Seed Data...");

        // 1. Seed Users
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .name("Hotel Admin")
                    .email("admin@hotel.com")
                    .password(passwordEncoder.encode("admin123"))
                    .phone("+1-111-222-3333")
                    .role("ADMIN")
                    .build();

            User customer = User.builder()
                    .name("John Doe")
                    .email("customer@hotel.com")
                    .password(passwordEncoder.encode("customer123"))
                    .phone("+1-444-555-6666")
                    .role("CUSTOMER")
                    .build();

            userRepository.saveAll(Arrays.asList(admin, customer));
            logger.info("Seeded Admin (admin@hotel.com) and Customer (customer@hotel.com) users.");
        }

        // 2. Seed Amenities
        if (amenityRepository.count() == 0) {
            List<Amenity> amenities = Arrays.asList(
                    Amenity.builder().amenityName("Free High-Speed WiFi").build(),
                    Amenity.builder().amenityName("Infinity Swimming Pool").build(),
                    Amenity.builder().amenityName("Luxury Spa & Wellness").build(),
                    Amenity.builder().amenityName("Central Air Conditioning").build(),
                    Amenity.builder().amenityName("Complimentary Gourmet Breakfast").build(),
                    Amenity.builder().amenityName("Modern Fitness Center").build(),
                    Amenity.builder().amenityName("Scenic Ocean View").build(),
                    Amenity.builder().amenityName("Fully Stocked Mini Bar").build(),
                    Amenity.builder().amenityName("Flat-screen Smart TV").build(),
                    Amenity.builder().amenityName("24/7 Room Service").build()
            );
            amenityRepository.saveAll(amenities);
            logger.info("Seeded amenities list.");
        }

        List<Amenity> allAmenities = amenityRepository.findAll();

        // 3. Seed Room Categories
        if (roomCategoryRepository.count() == 0) {
            List<RoomCategory> categories = Arrays.asList(
                    RoomCategory.builder().categoryName("Standard Room").build(),
                    RoomCategory.builder().categoryName("Deluxe Room").build(),
                    RoomCategory.builder().categoryName("Executive Suite").build(),
                    RoomCategory.builder().categoryName("Presidential Penthouse").build()
            );
            roomCategoryRepository.saveAll(categories);
            logger.info("Seeded room categories.");
        }

        List<RoomCategory> allCategories = roomCategoryRepository.findAll();
        RoomCategory standardCat = allCategories.stream().filter(c -> c.getCategoryName().equals("Standard Room")).findFirst().orElse(allCategories.get(0));
        RoomCategory deluxeCat = allCategories.stream().filter(c -> c.getCategoryName().equals("Deluxe Room")).findFirst().orElse(allCategories.get(0));
        RoomCategory suiteCat = allCategories.stream().filter(c -> c.getCategoryName().equals("Executive Suite")).findFirst().orElse(allCategories.get(0));
        RoomCategory penthouseCat = allCategories.stream().filter(c -> c.getCategoryName().equals("Presidential Penthouse")).findFirst().orElse(allCategories.get(0));

        // 4. Seed Hotels — ensure at least 10 hotels exist
        if (hotelRepository.count() < 10) {
            List<Hotel> initialHotels = Arrays.asList(
                    Hotel.builder()
                            .hotelName("Grand Palace Resort & Spa")
                            .location("Miami, FL")
                            .description("Experience absolute luxury with direct beach access, multi-cuisine gourmet dining, and a world-class infinity pool overlooking the Atlantic.")
                            .build(),
                    Hotel.builder()
                            .hotelName("The Manhattan Heights Hotel")
                            .location("New York, NY")
                            .description("A sleek, modern tower rising above Midtown Manhattan, featuring a panoramic rooftop sky lounge, fine Italian dining, and swift access to Broadway theaters.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Alpine Heights Mountain Lodge")
                            .location("Denver, CO")
                            .description("Escape to our cozy timber-framed lodge featuring rustic fieldstone fireplaces, heated outdoor thermal pools, and ski-in/ski-out convenience.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Tropical Bay Sands")
                            .location("Honolulu, HI")
                            .description("Enclosed by palm trees and crystalline waters, this Hawaiian paradise offers private beachside cabanas, surf lessons, and authentic Polynesian luaus.")
                            .build(),
                    // Additional hotels to reach 10
                    Hotel.builder()
                            .hotelName("Riverside Boutique")
                            .location("Portland, OR")
                            .description("Charming riverside boutique hotel with artisanal breakfasts and bike rentals for local exploration.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Desert Oasis Retreat")
                            .location("Phoenix, AZ")
                            .description("A serene desert hideaway featuring rooftop stargazing lounges and guided desert hikes.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Lakeside Grand Hotel")
                            .location("Chicago, IL")
                            .description("Historic lakeside hotel with elegant ballrooms, panoramic views of the city skyline, and private marina access.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Sequoia Forest Inn")
                            .location("Groveland, CA")
                            .description("Nestled near giant sequoias, offering guided nature walks and cozy wood-burning fireplaces.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Coastal Cliffs Retreat")
                            .location("Big Sur, CA")
                            .description("Clifftop suites with dramatic Pacific Ocean views and farm-to-table dining experiences.")
                            .build(),
                    Hotel.builder()
                            .hotelName("Urban Central Hotel")
                            .location("San Francisco, CA")
                            .description("Modern downtown hotel steps from transit, museums, and a buzzing nightlife scene.")
                            .build()
            );

            // Save only the number of hotels required to reach 10
            int current = (int) hotelRepository.count();
            int needed = 10 - current;
            if (needed > 0) {
                List<Hotel> toAdd = initialHotels.subList(0, Math.min(needed, initialHotels.size()));
                hotelRepository.saveAll(toAdd);
                logger.info("Seeded {} additional hotels.", toAdd.size());
            }
        }

        List<Hotel> allHotels = hotelRepository.findAll();

        // 5. Seed Rooms
        if (roomRepository.count() == 0) {
            // Seed Rooms for Grand Palace Resort (Hotel 0)
            Hotel miamiHotel = allHotels.get(0);
            roomRepository.save(Room.builder()
                    .hotel(miamiHotel)
                    .category(standardCat)
                    .roomNumber("M-101")
                    .price(BigDecimal.valueOf(129.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(3), allAmenities.get(8)))
                    .build());
            roomRepository.save(Room.builder()
                    .hotel(miamiHotel)
                    .category(deluxeCat)
                    .roomNumber("M-202")
                    .price(BigDecimal.valueOf(189.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(3), allAmenities.get(4), allAmenities.get(6), allAmenities.get(8)))
                    .build());
            roomRepository.save(Room.builder()
                    .hotel(miamiHotel)
                    .category(suiteCat)
                    .roomNumber("M-303")
                    .price(BigDecimal.valueOf(349.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(1), allAmenities.get(2), allAmenities.get(3), allAmenities.get(4), allAmenities.get(5), allAmenities.get(6), allAmenities.get(7), allAmenities.get(8), allAmenities.get(9)))
                    .build());

            // Seed Rooms for The Manhattan Heights (Hotel 1)
            Hotel nyHotel = allHotels.get(1);
            roomRepository.save(Room.builder()
                    .hotel(nyHotel)
                    .category(standardCat)
                    .roomNumber("NY-505")
                    .price(BigDecimal.valueOf(149.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(3), allAmenities.get(8)))
                    .build());
            roomRepository.save(Room.builder()
                    .hotel(nyHotel)
                    .category(deluxeCat)
                    .roomNumber("NY-606")
                    .price(BigDecimal.valueOf(229.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(3), allAmenities.get(4), allAmenities.get(5), allAmenities.get(8), allAmenities.get(9)))
                    .build());

            // Seed Rooms for Alpine Heights (Hotel 2)
            Hotel denverHotel = allHotels.get(2);
            roomRepository.save(Room.builder()
                    .hotel(denverHotel)
                    .category(deluxeCat)
                    .roomNumber("A-102")
                    .price(BigDecimal.valueOf(199.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(4), allAmenities.get(5), allAmenities.get(9)))
                    .build());
            roomRepository.save(Room.builder()
                    .hotel(denverHotel)
                    .category(suiteCat)
                    .roomNumber("A-204")
                    .price(BigDecimal.valueOf(399.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(1), allAmenities.get(2), allAmenities.get(4), allAmenities.get(5), allAmenities.get(7), allAmenities.get(9)))
                    .build());

            // Seed Rooms for Tropical Bay Sands (Hotel 3)
            Hotel hawaiiHotel = allHotels.get(3);
            roomRepository.save(Room.builder()
                    .hotel(hawaiiHotel)
                    .category(deluxeCat)
                    .roomNumber("H-110")
                    .price(BigDecimal.valueOf(259.99))
                    .availabilityStatus(true)
                    .amenities(Arrays.asList(allAmenities.get(0), allAmenities.get(3), allAmenities.get(4), allAmenities.get(6), allAmenities.get(8)))
                    .build());
            roomRepository.save(Room.builder()
                    .hotel(hawaiiHotel)
                    .category(penthouseCat)
                    .roomNumber("H-VIP")
                    .price(BigDecimal.valueOf(899.99))
                    .availabilityStatus(true)
                    .amenities(allAmenities) // VIP Penthouse has all amenities
                    .build());

            logger.info("Seeded rooms matching amenities and categories.");
        }

        // 6. Seed Promotions
        if (promotionRepository.count() == 0) {
            List<Promotion> promotions = Arrays.asList(
                    Promotion.builder()
                            .promoCode("WELCOME10")
                            .discountPercentage(BigDecimal.valueOf(10.0))
                            .startDate(LocalDate.now().minusDays(5))
                            .endDate(LocalDate.now().plusYears(1))
                            .build(),
                    Promotion.builder()
                            .promoCode("SUPERDEAL")
                            .discountPercentage(BigDecimal.valueOf(20.0))
                            .startDate(LocalDate.now())
                            .endDate(LocalDate.now().plusMonths(6))
                            .build(),
                    Promotion.builder()
                            .promoCode("SUMMER30")
                            .discountPercentage(BigDecimal.valueOf(30.0))
                            .startDate(LocalDate.now())
                            .endDate(LocalDate.now().plusMonths(3))
                            .build()
            );
            promotionRepository.saveAll(promotions);
            logger.info("Seeded promotions list.");
        }

        logger.info("Seed Data Initialization Completed Successfully.");
    }
}
