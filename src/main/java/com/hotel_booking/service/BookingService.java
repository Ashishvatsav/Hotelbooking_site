package com.hotel_booking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.User;
import com.hotel_booking.exception.BookingConflictException;
import com.hotel_booking.exception.ResourceNotFoundException;
import com.hotel_booking.repository.BookingRepository;
import com.hotel_booking.repository.RoomRepository;
import com.hotel_booking.repository.UserRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Booking createBooking(Long userId, Long roomId, Booking booking) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        List<Booking> conflictingBookings = bookingRepository
                .findByRoomIdAndCheckOutDateAfterAndCheckInDateBefore(roomId, booking.getCheckInDate(), booking.getCheckOutDate());
        if (!conflictingBookings.isEmpty()) {
            throw new BookingConflictException("Room already booked for selected dates");
        }

        booking.setUser(user);
        booking.setRoom(room);
        Booking savedBooking = bookingRepository.save(booking);
        room.setAvailable(false);
        roomRepository.save(room);
        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
