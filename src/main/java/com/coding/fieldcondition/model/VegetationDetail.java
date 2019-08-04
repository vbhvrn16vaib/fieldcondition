package com.coding.fieldcondition.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Data
@Builder
@ToString
public class VegetationDetail implements Comparable<VegetationDetail>{
    private AtomicInteger occurence;
    private BigDecimal vegetation;
    private ZonedDateTime occurenceAt;

    @Override
    public int compareTo(VegetationDetail o) {
        int x = occurenceAt.toInstant().compareTo(o.getOccurenceAt().toInstant());
        if(x == 0){
            return vegetation.compareTo(o.vegetation);
        }
        return x;
    }
}
