package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.accommodation.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
