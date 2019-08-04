package com.coding.fieldcondition.service;

import com.coding.fieldcondition.model.ConditionRequest;
import com.coding.fieldcondition.model.FieldConditionResponse;
import com.coding.fieldcondition.model.VegetationDetail;
import com.coding.fieldcondition.repository.FieldStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FieldConditionService {
    private final FieldStatisticsRepository fieldStatisticsRepository;

    public boolean updateFieldConditions(ConditionRequest request){
        return fieldStatisticsRepository.updateFieldStatistics(VegetationDetail.builder()
                .occurenceAt(request.getOccurrenceAt())
                .vegetation(request.getVegetation().setScale(2, RoundingMode.DOWN))
                .build());
    }

    public FieldConditionResponse getVegetationStatistic(){
        return FieldConditionResponse.builder()
                .vegetation(fieldStatisticsRepository.getFieldStatistics())
                .build();
    }
}
