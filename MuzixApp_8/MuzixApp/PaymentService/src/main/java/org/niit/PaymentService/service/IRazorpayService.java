package org.niit.PaymentService.service;

import com.razorpay.RazorpayException;
import org.niit.PaymentService.domain.OrderRequest;
import org.niit.PaymentService.domain.OrderResponse;

import java.util.Optional;

public interface IRazorpayService {
    public OrderResponse createOrder(OrderRequest orderRequest) throws RazorpayException;
    public OrderResponse updateSubscriptionStatus(String email, OrderResponse orderDetails);
    public Optional<OrderResponse> getSubscriptionDetail(String email);
}
