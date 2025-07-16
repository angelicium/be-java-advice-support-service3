package com.itm.space.controller.impl;

import com.itm.space.controller.OperatorController;
import com.itm.space.model.request.CreateOperatorRequest;
import com.itm.space.model.request.UpdateOperatorRequest;
import com.itm.space.model.response.OperatorResponse;
import com.itm.space.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

import static com.itm.space.constant.ApiConstant.OPERATORS_URL;

@RestController
@RequiredArgsConstructor
public class OperatorControllerImpl implements OperatorController {

    private final OperatorService operatorService;

    @Override
    public ResponseEntity<OperatorResponse> createOperator(CreateOperatorRequest createOperatorRequest) {
        OperatorResponse operatorResponse = operatorService.createOperator(createOperatorRequest);

        return ResponseEntity
            .created(URI.create(OPERATORS_URL + operatorResponse.getId()))
            .body(operatorResponse);
    }

    @Override
    public ResponseEntity<OperatorResponse> updateOperator(UUID id, UpdateOperatorRequest updateOperatorRequest) {
        OperatorResponse operatorResponse = operatorService.updateOperator(id, updateOperatorRequest);
        return ResponseEntity.ok(operatorResponse);
    }
}
