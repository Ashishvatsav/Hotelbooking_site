package com.hotel_booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel_booking.entity.Hotel;
import com.hotel_booking.entity.Room;
import com.hotel_booking.exception.ResourceNotFoundException;
import com.hotel_booking.repository.HotelRepository;
import com.hotel_booking.repository.RoomRepository;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Room createRoom(Long hotelId, Room room) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
    }

    public Room updateRoom(Long roomId, Room updatedRoom) {
        Room room = getRoomById(roomId);
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setRoomType(updatedRoom.getRoomType());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        room.setCapacity(updatedRoom.getCapacity());
        room.setAvailable(updatedRoom.getAvailable());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {
        Room room = getRoomById(roomId);
        roomRepository.delete(room);
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailable(true);
    }

    public List<Room> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType);
    }
}
