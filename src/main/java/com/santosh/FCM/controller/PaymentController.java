package com.santosh.FCM.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${rzp.key.secret}")
    private String razorpaySecretKey;
    @Value("${rzp.key.id}")
    private String razorpayKeyId;
    @Value("${rzp.currency}")
    private String currency;

    @GetMapping("/createOrderId/{amount}")
    public String createPaymentOrderId(@PathVariable String amount) {
        String orderId=null;
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecretKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // amount in the smallest currency unit
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", "order_rcptid_11");

            Order order = razorpay.orders.create(orderRequest);
            orderId = order.get("id");
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
        return orderId;
    }
}
