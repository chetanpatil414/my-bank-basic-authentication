package com.mybank.repository;

import com.mybank.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FetchUserDetailRepository extends CrudRepository<Customer,Long> {

    Optional<Customer> findByEmail(String username);
}
