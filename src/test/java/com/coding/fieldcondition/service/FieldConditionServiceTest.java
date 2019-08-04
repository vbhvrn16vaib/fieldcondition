package com.coding.fieldcondition.service;

import com.coding.fieldcondition.model.ConditionRequest;
import com.coding.fieldcondition.model.FieldConditionResponse;
import com.coding.fieldcondition.model.Statistic;
import com.coding.fieldcondition.repository.FieldStatisticsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldConditionServiceTest {
    @Mock
    FieldStatisticsRepository fieldStatisticsRepository;
    private FieldConditionService fieldConditionService;

    @Before
    public void setUp() {
        fieldConditionService = new FieldConditionService(fieldStatisticsRepository);
    }

    @Test
    public void updateFieldConditions() {
        ZonedDateTime z1 = ZonedDateTime.parse("2019-04-23T08:50Z");
        ConditionRequest request = ConditionRequest.builder().vegetation(BigDecimal.valueOf(23)).occurrenceAt(z1).build();

        when(fieldStatisticsRepository.updateFieldStatistics(Mockito.any())).thenReturn(true);

        Assert.assertTrue(fieldConditionService.updateFieldConditions(request));
    }

    @Test
    public void getVegetationStatistic() {
        when(fieldStatisticsRepository.getFieldStatistics()).thenReturn(Statistic.builder()
                .count(BigDecimal.ONE)
                .build());

        FieldConditionResponse result = fieldConditionService.getVegetationStatistic();

        Assert.assertFalse(result.getVegetation().isEmpty());
        Assert.assertEquals(result.getVegetation().getCount(),BigDecimal.ONE);
        verify(fieldStatisticsRepository,times(1)).getFieldStatistics();
    }
}