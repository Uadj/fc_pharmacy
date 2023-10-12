package com.example.fc_drug.pharmacy.service;

import com.example.fc_drug.pharmacy.entity.Pharmacy;
import com.example.fc_drug.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }
        entity.changePharmacyAddress(address);
    }

    public void updateAddressWithoutTransaction(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }
        entity.changePharmacyAddress(address);
    }
}
