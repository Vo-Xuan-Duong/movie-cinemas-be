package com.example.movie_cinemas_be.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class QRCodeService {

    public String generateQRCodeBase64(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            // Tạo ByteArrayOutputStream để lưu hình ảnh PNG
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

            // Chuyển mảng byte thành chuỗi Base64
            byte[] pngBytes = pngOutputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(pngBytes);

            // Trả về chuỗi Base64 (không bao gồm prefix "data:image/png;base64," vì frontend sẽ thêm)
            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void logQRCodeAsAscii(String text, int width, int height){
        try{
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            // Chuyển BitMatrix thành ASCII art
            StringBuilder qrAscii = new StringBuilder();
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                for (int x = 0; x < bitMatrix.getWidth(); x++) {
                    qrAscii.append(bitMatrix.get(x, y) ? "██" : "  "); // Dùng ký tự block hoặc khoảng trắng
                }
                qrAscii.append("\n");
            }

            // In ra terminal qua log.info
            log.info("QR Code for '{}':\n{}", text, qrAscii.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
