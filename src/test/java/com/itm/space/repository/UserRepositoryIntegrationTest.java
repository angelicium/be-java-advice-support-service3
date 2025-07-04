package com.itm.space.repository;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;


@DisplayName("Интеграционный тест UserRepository")
class UserRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .name("John Doe")
                .email("john-doe@mail.ru")
                .build();
    }

    @Test
    @DisplayName("Создание сущности в БД")
    @Transactional
    void saveUser(){

        User savedUser = userRepository.save(user);

        User fetсhedUser = userRepository.findById(savedUser.getId()).orElseThrow();

        Assertions.assertEquals(fetсhedUser.getName(), savedUser.getName());
        Assertions.assertEquals(fetсhedUser.getEmail(), savedUser.getEmail());
    }

    @Test
    @DisplayName("Обновление сущности в БД")
    @Transactional
    void updateUser(){

        User savedUser = userRepository.save(user);
        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();

        updatedUser.setName("Jane Doe");
        updatedUser.setEmail("jane-doe@mail.ru");

        userRepository.save(updatedUser);

        User fetсhedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        Assertions.assertEquals(fetсhedUser.getName(), updatedUser.getName());
        Assertions.assertEquals(fetсhedUser.getEmail(), updatedUser.getEmail());
    }
}