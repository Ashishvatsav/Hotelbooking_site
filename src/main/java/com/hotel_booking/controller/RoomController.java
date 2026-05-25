package com.hotel_booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_booking.entity.Room;
import com.hotel_booking.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/hotel/{hotelId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@PathVariable Long hotelId, @RequestBody Room room) {
        return roomService.createRoom(hotelId, room);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<Room> getRoomsByHotel(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotel(hotelId);
    }

    @GetMapping("/{roomId}")
    public Room getRoomById(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @PutMapping("/{roomId}")
    public Room updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        return roomService.updateRoom(roomId, room);
    }

    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return "Room deleted successfully";
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/type/{roomType}")
    public List<Room> getRoomsByType(@PathVariable String roomType) {
        return roomService.getRoomsByType(roomType);
    }
}
