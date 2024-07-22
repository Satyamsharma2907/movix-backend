package org.niit.PaymentService.controller;

import com.razorpay.RazorpayException;
import org.niit.PaymentService.domain.OrderRequest;
import org.niit.PaymentService.domain.OrderResponse;
import org.niit.PaymentService.service.IRazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentservice")
public class RazorpayController {

    private IRazorpayService razorpayService;

    @Autowired
    public RazorpayController(IRazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) throws RazorpayException {
        try {
            return new ResponseEntity<>(razorpayService.createOrder(orderRequest), HttpStatus.CREATED);
        }
        catch (RazorpayException e) {
            throw new RazorpayException("RazorPay Exception!!!");
        }
    }

    @PutMapping("/status/{email}")
    public ResponseEntity<?> updateOrder(@PathVariable String email, @RequestBody OrderResponse orderResponse) {
        return new ResponseEntity<>(razorpayService.updateSubscriptionStatus(email, orderResponse), HttpStatus.CREATED);
    }

    @GetMapping("/subscription/{email}")
    public ResponseEntity<?> getOrder(@PathVariable String email) {
        return new ResponseEntity<>(razorpayService.getSubscriptionDetail(email), HttpStatus.OK);
    }
}
