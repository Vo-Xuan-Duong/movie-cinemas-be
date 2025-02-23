package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.DiscountRequest;
import com.example.movie_cinemas_be.dtos.response.DiscountResponse;
import com.example.movie_cinemas_be.entitys.Discount;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.DiscountRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        Discount discount = new Discount();
        discount.setCode(discountRequest.getCode());
        discount.setDiscountAmount(discountRequest.getDiscountAmount());
        discount.setDiscountRate(discountRequest.getDiscountRate());

        return convertToDiscountResponse(discountRepository.save(discount));
    }

    public DiscountResponse getDiscountByCode(String code) {
        return convertToDiscountResponse(discountRepository.findDiscountByCode(code));
    }

    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll().stream().map(discount -> convertToDiscountResponse(discount)).collect(Collectors.toList());
    }

    public DiscountResponse updateDiscount(long id ,DiscountRequest discountRequest) {
        Discount discount = discountRepository.findById(id).orElseThrow(()-> new CustomException("Discount not found", ErrorCode.NO_DATA_IN_DATABASE));

        discount.setDiscountAmount(discountRequest.getDiscountAmount());
        discount.setDiscountRate(discountRequest.getDiscountRate());
        discount.setCode(discountRequest.getCode());
        return convertToDiscountResponse(discountRepository.save(discount));
    }

    public void deleteDiscount(long id) {
        discountRepository.deleteById(id);
    }



    public DiscountResponse convertToDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .code(discount.getCode())
                .discountAmount(discount.getDiscountAmount())
                .discountRate(discount.getDiscountRate())
                .id(discount.getId())
                .build();
    }
}
