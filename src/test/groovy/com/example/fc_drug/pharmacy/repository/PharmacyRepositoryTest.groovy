package com.example.fc_drug.pharmacy.repository

import com.example.fc_drug.AbstractIntegrationContainerBaseTest
import com.example.fc_drug.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def "PharmacyRepository save"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
        .pharmacyName(name)
        .pharmacyAddress(address)
        .latitude(latitude)
        .longitude(longitude)
        .build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }
}
