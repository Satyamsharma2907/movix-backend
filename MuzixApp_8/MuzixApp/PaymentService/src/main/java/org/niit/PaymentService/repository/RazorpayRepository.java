package org.niit.PaymentService.repository;

import org.niit.PaymentService.domain.OrderRequest;
import org.niit.PaymentService.domain.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazorpayRepository extends JpaRepository<OrderResponse,String> {

}
