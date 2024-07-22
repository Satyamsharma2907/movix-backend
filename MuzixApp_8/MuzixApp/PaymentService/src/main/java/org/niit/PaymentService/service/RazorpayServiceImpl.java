package org.niit.PaymentService.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.niit.PaymentService.domain.OrderRequest;
import org.niit.PaymentService.domain.OrderResponse;
import org.niit.PaymentService.repository.RazorpayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class RazorpayServiceImpl implements IRazorpayService{

    private RazorpayRepository razorpayRespository;
    private RazorpayClient client;
    private static final String SECRET_ID1 = "rzp_test_wmDl5TjphiigG5";
    private static final String SECRET_KEY1 = "GLHzG7KW47OM0n8fcpWlmwQQ";
    private static final String SECRET_ID2 = "rzp_test_J4fInjDpTX475d";
    private static final String SECRET_KEY2 = "r8fNXAB78RmsVfdiQbWGwyjr";

    boolean flag = false;

    @Autowired
    public RazorpayServiceImpl(RazorpayRepository razorpayRespository) {
        this.razorpayRespository = razorpayRespository;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) throws RazorpayException {
        OrderResponse response = new OrderResponse();

        if (orderRequest.getAmount().intValue() > 1000) {
            client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);
        } else {
            client = new RazorpayClient(SECRET_ID2, SECRET_KEY2);
        }

        Order order = createRazorPayOrder(orderRequest.getAmount());
        System.out.println("---------------------------");
        String orderId = (String) order.get("id");
        System.out.println("Order ID: " + orderId);
        System.out.println("---------------------------");
        response.setEmail(orderRequest.getEmail());
        response.setRazorpayOrderId(orderId);
        response.setApplicationFee("" + orderRequest.getAmount());
        if (orderRequest.getAmount().intValue() > 1000) {
            response.setSecretKey(SECRET_KEY1);
            response.setSecretId(SECRET_ID1);
            response.setPgName("razor1");
        } else {
            response.setSecretKey(SECRET_KEY2);
            response.setSecretId(SECRET_ID2);
            response.setPgName("razor2");
        }

        return razorpayRespository.save(response);
    }

    private Order createRazorPayOrder(BigInteger amount) throws RazorpayException {

        JSONObject options = new JSONObject();
        options.put("amount", amount.multiply(new BigInteger("100")));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", true); // You can enable this if you want to do Auto Capture.
        return client.orders.create(options);
    }

    @Override
    public OrderResponse updateSubscriptionStatus(String email, OrderResponse orderDetails) {
        OrderResponse orderResponse = null;
        if (razorpayRespository.findById(email).isPresent()) {
            orderResponse = razorpayRespository.findById(email).get();
            orderResponse.setApplicationFee(orderDetails.getApplicationFee());
            orderResponse.setSubscribed(orderDetails.isSubscribed());
            razorpayRespository.save(orderResponse);
            flag = true;
        }
        return orderResponse;
    }

    @Override
    public Optional<OrderResponse> getSubscriptionDetail(String email) {
        if (razorpayRespository.findById(email).isPresent()) {
            return Optional.of(razorpayRespository.findById(email).get());
        }
        return null;
    }
}
