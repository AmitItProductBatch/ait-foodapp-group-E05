package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}
