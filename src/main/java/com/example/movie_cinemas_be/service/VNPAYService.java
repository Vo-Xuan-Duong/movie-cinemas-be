package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.config.VNPAYConfig;
import com.example.movie_cinemas_be.dtos.response.BookingResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class VNPAYService {


    private final BookingService bookingService;

    public VNPAYService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public String createPayment(HttpServletRequest request, long amount, String orderInfor){
        //Các bạn có thể tham khảo tài liệu hướng dẫn và điều chỉnh các tham số
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPAYConfig.getIpAddress(request);
        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
        String vnp_Amount =  String.valueOf(amount*100);
        String vnp_CurrCode = "VND" ;
        String vnp_OrderType = "order-type";
        String vnp_Locale = "vn";
        String urlReturn = VNPAYConfig.vnp_ReturnUrl;
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());


        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String secretKey = VNPAYConfig.secretKey;
        String vnp_SecureHash = VNPAYConfig.hmacSHA512(secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;

        log.info("VNPAYService paymentUrl:{}", paymentUrl);
        return paymentUrl;
    }

    public Map<String, Object> returnPayment(HttpServletRequest request, String bookingCode){
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPAYConfig.hashAllFields(fields);
        int paymentStatus;
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                paymentStatus=1;
            } else {
                paymentStatus=0;
            }
        } else {
            paymentStatus=-1;
        }

        BookingResponse  bookingResponse = bookingService.getBookingByBookingCode(bookingCode);


        Map<String, Object> response = new HashMap<>();
        response.put("booking", bookingResponse);
        response.put("orderInfo", request.getParameter("vnp_OrderInfo"));
        response.put("paymentTime", request.getParameter("vnp_PayDate"));
        response.put("transactionId", request.getParameter("vnp_TransactionNo"));
        response.put("totalPrice", request.getParameter("vnp_Amount") != null
                ? Long.parseLong(request.getParameter("vnp_Amount")) / 100
                : 0);
        response.put("paymentStatus", paymentStatus == 1 ? "Success" : (paymentStatus == 0 ? "Failed" : "Invalid Signature"));

        return response;
    }

}


