package com.example.fc_drug.direction.service;

import com.example.fc_drug.api.dto.DocumentDto;
import com.example.fc_drug.api.service.KakaoCategorySearchService;
import com.example.fc_drug.direction.entity.Direction;
import com.example.fc_drug.direction.repository.DirectionRepository;
import com.example.fc_drug.pharmacy.PharmacyDto;
import com.example.fc_drug.pharmacy.service.PharmacySearchService;
import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    // 약국 최대 검색 갯수
    private static final int MAX_SEARCH_COUNT = 3;
    // 반경
    private static final double RADIUS_KM = 10.0;

    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";
    private final PharmacySearchService pharmacySearchService;

    private final DirectionRepository directionRepository;

    private final KakaoCategorySearchService kakaoCategorySearchService;

    private final Base62Service base62Service;

    @Transactional(readOnly = true)
    public String findDirectionUrlById(String encodedId){
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction = directionRepository.findById(decodedId).orElse(null);

        String params =
                String.join(
                        "," ,
                        direction.getTargetPharmacyName(),
                        String.valueOf(direction.getTargetLatitude()),
                        String.valueOf(direction.getTargetLongitude())
                );

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString();
        return result;
    }

    public List<Direction> saveAll(List<Direction> directionList){
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }
    public List<Direction> buildDirectionList(DocumentDto documentDto){

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return pharmacySearchService.searchPharmacyDtoList()
                .stream().map(pharmacyDto -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetPharmacyName(pharmacyDto.getPharmacyName())
                        .targetAddress(pharmacyDto.getPharmacyAddress())
                        .targetLatitude(pharmacyDto.getLatitude())
                        .targetLongitude(pharmacyDto.getLongitude())
                        .distance(
                                calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                pharmacyDto.getLatitude(), pharmacyDto.getLongitude())
                        )

                        .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    public List<Direction> buildDirectionListByCategoryApi(DocumentDto documentDto){

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return kakaoCategorySearchService.requestCategorySearch(documentDto.getLatitude(), documentDto.getLongitude(), RADIUS_KM)
                .getDocumentList().stream().map(resultDocumentDto -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetPharmacyName(resultDocumentDto.getPlaceName())
                        .targetAddress(resultDocumentDto.getAddressName())
                        .targetLatitude(resultDocumentDto.getLatitude())
                        .targetLongitude(resultDocumentDto.getLongitude())
                        .distance(
                              resultDocumentDto.getDistance() * 0.001
                        )

                        .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }
    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}