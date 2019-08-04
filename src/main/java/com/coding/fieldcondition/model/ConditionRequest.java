package com.coding.fieldcondition.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
public class ConditionRequest {
    @NotNull
    private BigDecimal vegetation;
    @NotNull
    private ZonedDateTime occurrenceAt;
}
