package com.itm.space.service;

import com.itm.space.BaseUnitTest;
import com.itm.space.domain.entity.Operator;
import com.itm.space.domain.entity.User;
import com.itm.space.mapper.OperatorMapper;
import com.itm.space.model.request.CreateOperatorRequest;
import com.itm.space.model.request.UpdateOperatorRequest;
import com.itm.space.model.response.OperatorResponse;
import com.itm.space.repository.OperatorRepository;
import com.itm.space.repository.UserRepository;
import com.itm.space.service.impl.OperatorServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OperatorServiceImplTest extends BaseUnitTest {

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OperatorMapper operatorMapper;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    private UpdateOperatorRequest updateRequest;
    private CreateOperatorRequest createRequest;

    private UUID operatorId;

    @BeforeEach
    void setUp() {
        operatorId = UUID.randomUUID();

        updateRequest = UpdateOperatorRequest
            .builder()
            .specialization("Техническая поддержка")
            .maxTickets(15)
            .build();

        createRequest = CreateOperatorRequest.builder()
            .userId(operatorId)
            .specialization("Техническая поддержка")
            .maxTickets(15)
            .build();
    }

    @Test
    @DisplayName("Создание оператора успешно - статус 201")
    void createOperator_shouldCreateNewOperator() {
        Operator operator = Operator.builder()
            .id(operatorId)
            .build();

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(User.builder().build()));
        when(operatorRepository.findByUser(any(User.class))).thenReturn(Optional.empty());
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator);
        when(operatorMapper.toOperatorResponse(any(Operator.class))).thenReturn(
            OperatorResponse.builder()
                .id(operatorId)
                .name("testUserName")
                .specialization("Техническая поддержка")
                .maxTickets(15)
                .createdAt(LocalDateTime.now())
                .build()
        );

        OperatorResponse operatorResponse = operatorService.createOperator(createRequest);

        assertNotNull(operatorResponse);
        assertEquals(operatorResponse.getId(), operator.getId());
        assertEquals("Техническая поддержка", operatorResponse.getSpecialization());

        verify(operatorRepository, times(1)).save(any(Operator.class));
    }

    @Test
    @DisplayName("Юзер для создания оператора не найден - статус 404")
    void createOperator_userNotFound_shouldThrowException() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> operatorService.createOperator(createRequest));

        verify(operatorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Оператор уже существует - статус 400")
    void createOperator_OperatorAlreadyExists_shouldThrowException() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(User.builder().build()));
        when(operatorRepository.findByUser(any(User.class))).thenReturn(Optional.of(Operator.builder().build()));

        assertThrows(IllegalArgumentException.class, () -> operatorService.createOperator(createRequest));

        verify(operatorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Оператор обновлен успешно")
    void updateOperator_shouldUpdateExistingOperator() {
        Operator existingOperator = Operator.builder()
            .id(operatorId)
            .specialization("Тест")
            .maxTickets(10)
            .build();

        Operator savedOperator = Operator
            .builder()
            .id(operatorId)
            .specialization("Техническая поддержка")
            .maxTickets(15)
            .build();

        OperatorResponse expectedResponse = OperatorResponse
            .builder()
            .id(operatorId)
            .specialization("Техническая поддержка")
            .maxTickets(15)
            .build();

        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(existingOperator));
        when(operatorRepository.save(existingOperator)).thenReturn(savedOperator);
        when(operatorMapper.toOperatorResponse(any(Operator.class))).thenReturn(expectedResponse);

        OperatorResponse result = operatorService.updateOperator(operatorId, updateRequest);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        assertEquals("Техническая поддержка", existingOperator.getSpecialization());
        assertEquals(15, existingOperator.getMaxTickets());

        verify(operatorRepository).findById(operatorId);
        verify(operatorRepository).save(existingOperator);
        verify(operatorMapper).toOperatorResponse(any(Operator.class));
    }

    @Test
    @DisplayName("Оператор не найден")
    void updateOperator_shouldThrowEntityNotFoundExceptionWhenOperatorNotFound() {
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
            operatorService.updateOperator(operatorId, updateRequest)
        );

        verify(operatorRepository).findById(operatorId);
        verify(operatorRepository, never()).save(any());
        verify(operatorMapper, never()).toOperatorResponse(any());
    }
}
