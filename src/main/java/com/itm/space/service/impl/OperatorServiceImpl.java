package com.itm.space.service.impl;

import com.itm.space.domain.entity.Operator;
import com.itm.space.domain.entity.User;
import com.itm.space.mapper.OperatorMapper;
import com.itm.space.model.request.CreateOperatorRequest;
import com.itm.space.model.request.UpdateOperatorRequest;
import com.itm.space.model.response.OperatorResponse;
import com.itm.space.repository.OperatorRepository;
import com.itm.space.repository.UserRepository;
import com.itm.space.service.OperatorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.itm.space.constant.ErrorMessagesConstant.BAD_REQUEST_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;
    private final OperatorMapper operatorMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OperatorResponse updateOperator(UUID id, UpdateOperatorRequest updateOperatorRequest) {
        Operator operator = operatorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        operator.setSpecialization(updateOperatorRequest.getSpecialization());
        operator.setMaxTickets(updateOperatorRequest.getMaxTickets());

        operatorRepository.save(operator);

        return operatorMapper.toOperatorResponse(operator);
    }

    @Override
    public OperatorResponse createOperator(CreateOperatorRequest createOperatorRequest) {
        User user = userRepository.findById(createOperatorRequest.getUserId())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (operatorRepository.findByUser(user).isPresent()) {
            throw new IllegalArgumentException(BAD_REQUEST_MESSAGE);
        }

        Operator newOperator = Operator.builder()
            .user(user)
            .specialization(createOperatorRequest.getSpecialization())
            .maxTickets(createOperatorRequest.getMaxTickets())
            .build();

        operatorRepository.save(newOperator);

        return operatorMapper.toOperatorResponse(newOperator);
    }
}
