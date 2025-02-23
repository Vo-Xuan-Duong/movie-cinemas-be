package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.response.PaymentResponse;
import com.example.movie_cinemas_be.entitys.Payment;
import com.example.movie_cinemas_be.service.BookingService;
import com.example.movie_cinemas_be.service.MoMoService;
import com.example.movie_cinemas_be.service.PaymentService;
import com.example.movie_cinemas_be.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;
    private final RestClient.Builder builder;
    private final VNPAYService vnpayService;
    private final MoMoService momoService;

    public PaymentController(PaymentService paymentService,
                             BookingService bookingService,
                             RestClient.Builder builder,
                             VNPAYService vNPAYService, MoMoService moMoService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.builder = builder;
        this.vnpayService = vNPAYService;
        this.momoService = moMoService;
    }

    @PostMapping("/create_payment")
    public ResponseCustom<PaymentResponse> createPayment(@RequestParam("amount") long amount,
                                                         @RequestParam("orderInfo") String orderInfo,
                                                         @RequestParam("methodPayment")  String methodPayment,
                                                         HttpServletRequest request) {

        String paymentUrl;

        try {
            if (methodPayment.equals(Payment.PaymentMethod.VNPAY.name())) {
                log.info("Creating VNPAY Payment: {}", Payment.PaymentMethod.VNPAY.name());
                paymentUrl = vnpayService.createPayment(request, (int) amount, orderInfo);

                return ResponseCustom.<PaymentResponse>builder()
                        .message("VNPAY Payment Created")
                        .data(PaymentResponse.builder()
                                .message("VNPAY Payment Created")
                                .url(paymentUrl)
                                .build())
                        .build();

            } else if (methodPayment.equals(Payment.PaymentMethod.MOMO.name())) {
                log.info("Creating MOMO Payment: {}", Payment.PaymentMethod.MOMO.name());
                paymentUrl = momoService.createPayment(request, (int) amount, orderInfo);
                return ResponseCustom.<PaymentResponse>builder()
                        .message("MOMO Payment successfully")

                        .build();

            } else if (methodPayment.equals(Payment.PaymentMethod.PAYPAL.name())) {
                log.info("Creating PAYPAL Payment: {}", Payment.PaymentMethod.PAYPAL.name());
                // Chưa implement, trả về lỗi tạm thời
                return ResponseCustom.<PaymentResponse>builder()
                                .message("PayPal is not implemented yet")
                                .build();

            } else if (methodPayment.equals(Payment.PaymentMethod.ZALO_PAY.name())) {
                log.info("Creating ZALO_PAY Payment: {}", Payment.PaymentMethod.ZALO_PAY.name());
                // Chưa implement, trả về lỗi tạm thời
                return ResponseCustom.<PaymentResponse>builder()
                                .message("ZaloPay is not implemented yet")
                                .build();

            } else {
                log.warn("Invalid payment method: {}", methodPayment);
                return ResponseCustom.<PaymentResponse>builder()
                                .message("Failed to create payment: Invalid method " + methodPayment)
                                .build();
            }

        } catch (Exception e) {
            log.error("Error creating payment for method {}: {}", methodPayment, e.getMessage());
            return ResponseCustom.<PaymentResponse>builder()
                            .message("Failed to create payment: " + e.getMessage())
                            .build();
        }
    }

    @GetMapping("/return_payment")
    public ResponseCustom<Map<String, Object>> returnPayment(HttpServletRequest request, @RequestParam("paymentMethod") String methodPayment ,@RequestParam("vnp_OrderInfo") String bookingCode ) {

        try {
            if (methodPayment.equals(Payment.PaymentMethod.VNPAY.name())) {
                log.info("Creating VNPAY Payment: {}", Payment.PaymentMethod.VNPAY.name());
                return ResponseCustom.<Map<String, Object>>builder()
                        .message("Successfully returned payment")
                        .data(vnpayService.returnPayment(request, bookingCode))
                        .build();

            } else if (methodPayment.equals(Payment.PaymentMethod.MOMO.name())) {
                log.info("Creating MOMO Payment: {}", Payment.PaymentMethod.MOMO.name());

            } else if (methodPayment.equals(Payment.PaymentMethod.PAYPAL.name())) {
                log.info("Creating PAYPAL Payment: {}", Payment.PaymentMethod.PAYPAL.name());
                // Chưa implement, trả về lỗi tạm thời


            } else if (methodPayment.equals(Payment.PaymentMethod.ZALO_PAY.name())) {
                log.info("Creating ZALO_PAY Payment: {}", Payment.PaymentMethod.ZALO_PAY.name());
                // Chưa implement, trả về lỗi tạm thời


            } else {
                log.warn("Invalid payment method: {}", methodPayment);
                return ResponseCustom.<Map<String, Object>>builder()
                        .message("Failed to create payment: Invalid method " + methodPayment)
                        .build();
            }

            return ResponseCustom.<Map<String, Object>>builder()
                    .message("Failed to create payment: Payment URL is invalid")
                    .build();

        } catch (Exception e) {
            log.error("Error creating payment for method {}: {}", methodPayment, e.getMessage());
            return ResponseCustom.<Map<String, Object>>builder()
                    .message("Failed to create payment: " + e.getMessage())
                    .build();
        }


    }


    @PostMapping("/confirm")
    public ResponseCustom confirmPayment(@RequestParam("bookingId") Long bookingId) {
        if (paymentService.verifyPayment(bookingId)) {
            bookingService.confirmBooking(bookingId);
            return ResponseCustom.builder()
                    .message("Thanh toán thành công ,Vé đã được xác nhận")
                    .build();
        }else{
            bookingService.cancelBooking(bookingId);
            return ResponseCustom.builder()
                    .message("Thanh toán thất bai vé đã bị hủy")
                    .build();
        }

    }

}
