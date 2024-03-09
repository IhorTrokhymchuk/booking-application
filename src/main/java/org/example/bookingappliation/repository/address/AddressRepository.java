package org.example.bookingappliation.repository.address;

import org.example.bookingappliation.model.accommodation.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
