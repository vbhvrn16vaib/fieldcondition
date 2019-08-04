package com.coding.fieldcondition.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
public class Statistic {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal avg;
    @JsonIgnore
    private BigDecimal sum;
    @JsonIgnore
    private BigDecimal count;

    public Statistic updateSummary(BigDecimal vegetation) {
        return Statistic.builder()
                .max(max == null ? vegetation : max.max(vegetation).setScale(2, RoundingMode.UNNECESSARY))
                .min(min == null ? vegetation : min.min(vegetation).setScale(2, RoundingMode.UNNECESSARY))
                .count(count == null ? BigDecimal.ONE : count.add(BigDecimal.ONE).setScale(2, RoundingMode.UNNECESSARY))
                .sum(sum == null ? vegetation : sum.add(vegetation).setScale(2, RoundingMode.UNNECESSARY))
                .build();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return count == null;
    }
}
