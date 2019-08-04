package com.coding.fieldcondition.repository;

import com.coding.fieldcondition.model.Statistic;
import com.coding.fieldcondition.model.VegetationDetail;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.NavigableMap;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class FieldStatisticsRepositoryTest {
    FieldStatisticsRepository fieldStatisticsRepository = new FieldStatisticsRepository();

    @Before
    public void setUp() {
        ZonedDateTime z5 = ZonedDateTime.now().minusDays(5);
        fieldStatisticsRepository.updateFieldStatistics(VegetationDetail.builder()
                .occurenceAt(z5)
                .vegetation(BigDecimal.ONE)
                .build());
    }

    @Test
    public void shouldAddVegetationConditionIfLessThanThirtyDays() {
        ZonedDateTime z29 = ZonedDateTime.now().minusDays(29);
        VegetationDetail vg = VegetationDetail.builder().occurenceAt(z29)
                .vegetation(BigDecimal.ONE)
                .build();

        fieldStatisticsRepository.updateFieldStatistics(vg);

        NavigableMap<ZonedDateTime, Statistic> requestMap = (NavigableMap<ZonedDateTime, Statistic>) getField(fieldStatisticsRepository, "requestMap");
        assertEquals("Failed to add the new data !!", requestMap.size(), 2);
    }

    @Test
    public void shouldNotAddVegetationConditionIfMoreThanThirtyDays() {
        ZonedDateTime z31 = ZonedDateTime.now().minusDays(31);
        VegetationDetail vg = VegetationDetail.builder().occurenceAt(z31)
                .vegetation(BigDecimal.ONE)
                .build();

        fieldStatisticsRepository.updateFieldStatistics(vg);

        NavigableMap<ZonedDateTime, Statistic> requestMap = (NavigableMap<ZonedDateTime, Statistic>) getField(fieldStatisticsRepository, "requestMap");
        assertEquals("Failed to check data date !!", requestMap.size(), 1);
    }


    @Test
    public void shouldGetTheFieldStatistics(){
        Statistic result = fieldStatisticsRepository.getFieldStatistics();

        assertEquals(result.getCount(),BigDecimal.ONE);
        assertEquals(result.getSum(),BigDecimal.ONE);
        assertEquals(result.getAvg(),BigDecimal.ONE.setScale(2, RoundingMode.UNNECESSARY));
    }
}