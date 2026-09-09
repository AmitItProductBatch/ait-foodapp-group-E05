package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.app.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
