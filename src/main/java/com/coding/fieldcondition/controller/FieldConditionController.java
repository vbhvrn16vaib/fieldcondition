package com.coding.fieldcondition.controller;

import com.coding.fieldcondition.model.ConditionRequest;
import com.coding.fieldcondition.service.FieldConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class FieldConditionController {
    private FieldConditionService service;

    public FieldConditionController(FieldConditionService service) {
        this.service = service;
    }

    @GetMapping("/field-statistics")
    public ResponseEntity getFieldStatistics(){
        return new ResponseEntity(service.getVegetationStatistic(), HttpStatus.OK);
    }

    @PostMapping("/field-conditions")
    public ResponseEntity captureFieldCondition(@RequestBody @Valid ConditionRequest request){
        if(service.updateFieldConditions(request)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
