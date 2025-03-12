package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.CompaniResquestAdd;
import com.example.movie_cinemas_be.dtos.request.CountryResquestAdd;
import com.example.movie_cinemas_be.entitys.Compani;
import com.example.movie_cinemas_be.entitys.Country;
import com.example.movie_cinemas_be.reponsitory.CompaniRepository;
import com.example.movie_cinemas_be.reponsitory.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MovieCreateService {

    private CompaniRepository companiRepository;
    private CountryRepository countryRepository;

    public MovieCreateService(CompaniRepository companiRepository, CountryRepository countryRepository) {
        this.companiRepository = companiRepository;
        this.countryRepository = countryRepository;
    }

    public Compani createCompani(CompaniResquestAdd companiRequest) {
        Long id = companiRequest.getId();
        if (id == null) {
            throw new IllegalArgumentException("ID compani must not be null");
        }

        Optional<Compani> existingCompani = companiRepository.findById(id);
        if (existingCompani.isPresent()) {
            System.out.println("Compani with id " + id + " already exists");
            return existingCompani.get(); // An toàn vì đã kiểm tra isPresent()
        } else {
            Compani companiEntity = new Compani();
            companiEntity.setId(id);
            companiEntity.setName(companiRequest.getName());
            companiEntity.setLogo_path(companiRequest.getLogo_path());
            companiEntity.setOrigin_country(companiRequest.getOrigin_country());
            return companiRepository.save(companiEntity);
        }
    }

    public Compani getCompaniById(Long id) {
        Optional<Compani> existingCompani = companiRepository.findById(id);
        if (existingCompani.isPresent()) {
            return existingCompani.get();
        }
        return null;
    }

    public Country getCountryById(String iso) {
        Optional<Country> existingCountry = countryRepository.findById(iso);
        if (existingCountry.isPresent()) {
            return existingCountry.get();
        }
        return null;
    }

    public Country createCountry(CountryResquestAdd country) {
        log.debug("Country : {}", country.toString());
        String iso = country.getIso();

        Optional<Country> existingCountry = countryRepository.findById(iso);
        if (existingCountry.isPresent()) {
            System.out.println("Country with id " + iso + " already exists");
            return existingCountry.get(); // An toàn vì đã kiểm tra isPresent()
        } else {
            Country countryEntity = new Country();
            countryEntity.setName(country.getName());
            countryEntity.setIso(iso);
            return countryRepository.save(countryEntity);
        }
    }
}
