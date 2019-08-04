package com.coding.fieldcondition.repository;

import com.coding.fieldcondition.model.Statistic;
import com.coding.fieldcondition.model.VegetationDetail;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.time.temporal.ChronoUnit.DAYS;

@Repository
public class FieldStatisticsRepository {
    private NavigableMap<Instant, Statistic> requestMap = new ConcurrentSkipListMap<>();
    private Statistic statistic;
    private final int MAX_DAYS = 30;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.UNNECESSARY;

    @SneakyThrows
    public synchronized Boolean updateFieldStatistics(VegetationDetail detail) {
        ZonedDateTime now = ZonedDateTime.now();
        if (isValidRequest(detail.getOccurenceAt(), now)) {
            requestMap.putIfAbsent(detail.getOccurenceAt().toInstant(), Statistic.builder().build());
            requestMap.put(detail.getOccurenceAt().toInstant(),
                    requestMap.get(detail.getOccurenceAt().toInstant()).updateSummary(detail.getVegetation()));
            return true;

        }
        return false;
    }

    private boolean isValidRequest(ZonedDateTime now, ZonedDateTime occurenceAt) {
        return DAYS.between(now, occurenceAt) <= MAX_DAYS;
    }

    @SneakyThrows
    public Statistic getFieldStatistics() {
        ZonedDateTime now = ZonedDateTime.now();
        requestMap = (NavigableMap<Instant, Statistic>) requestMap.tailMap(now.minus(MAX_DAYS, DAYS).toInstant());
        calculateStatistics(requestMap);
        return statistic.isEmpty() ? Statistic.builder()
                .max(BigDecimal.ZERO)
                .min(BigDecimal.ZERO)
                .avg(BigDecimal.ZERO)
                .build()
                : statistic;
    }

    private void calculateStatistics(NavigableMap<Instant, Statistic> requestMap) {
        if (statistic == null) {
            statistic = Statistic.builder().build();
        }

        requestMap.forEach((key, summary) -> {
            statistic.setMin(statistic.getMin() == null ? summary.getMin().setScale(2, ROUNDING_MODE)
                    : statistic.getMin().min(summary.getMin()).setScale(2, ROUNDING_MODE));
            statistic.setMax(statistic.getMax() == null ? summary.getMax().setScale(2, ROUNDING_MODE)
                    : statistic.getMax().max(summary.getMax()).setScale(2, ROUNDING_MODE));
            statistic.setSum(statistic.getSum() == null ? summary.getSum()
                    : statistic.getSum().add(summary.getSum()));
            statistic.setCount(statistic.getCount() == null ? summary.getCount()
                    : statistic.getCount().add(summary.getCount()));
        });
        if (statistic.getSum() != null) {
            statistic.setAvg(statistic.getSum().divide(statistic.getCount(), 2, ROUNDING_MODE));
        }
    }
}
