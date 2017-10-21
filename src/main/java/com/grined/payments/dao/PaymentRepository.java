package com.grined.payments.dao;

import com.grined.payments.entity.db.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface PaymentRepository extends MongoRepository<Payment, String>{
    Stream<Payment> streamByDeletedFalse();
    Page<Payment> findByDeletedFalse(Pageable pageable);
}
