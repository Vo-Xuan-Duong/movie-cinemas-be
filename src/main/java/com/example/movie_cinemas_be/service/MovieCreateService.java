package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.CompaniResquest;
import com.example.movie_cinemas_be.dtos.request.CountryResquest;
import com.example.movie_cinemas_be.entitys.Compani;
import com.example.movie_cinemas_be.entitys.Country;
import com.example.movie_cinemas_be.reponsitory.CompaniRepository;
import com.example.movie_cinemas_be.reponsitory.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieCreateService {

    private CompaniRepository companiRepository;
    private CountryRepository countryRepository;

    public MovieCreateService(CompaniRepository companiRepository, CountryRepository countryRepository) {
        this.companiRepository = companiRepository;
        this.countryRepository = countryRepository;
    }

    public Compani createCompani(CompaniResquest companiRequest){

        Long id = companiRequest.getId();
        if (companiRequest.getId() == null) {
            throw new IllegalArgumentException("ID compani must not be null");
        }
        if (companiRepository.existsById(id)) {
            System.out.println("Compani with id " + id + " already exists");
            return companiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Compani with ID " + id + " not found"));
        } else {
            Compani companiEntity = new Compani();
            companiEntity.setId(id);
            companiEntity.setName(companiRequest.getName());
            companiEntity.setLogo_path(companiRequest.getLogo_path());
            companiEntity.setOrigin_country(companiRequest.getOrigin_country());

            return companiRepository.save(companiEntity);
        }
    }

    public Country createCountry(CountryResquest country){
        log.debug("Country : " + country.toString());
        if(countryRepository.existsById(country.getIso())){
            System.out.println("Country with id " + country.getIso() + " already exists");
            return countryRepository.findById(country.getIso()).get();
        }else {
            Country countryEntity = new Country();
            countryEntity.setName(country.getName());
            countryEntity.setIso(country.getIso());
            return countryRepository.save(countryEntity);
        }
    }
}
