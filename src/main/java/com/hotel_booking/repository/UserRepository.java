package com.hotel_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
