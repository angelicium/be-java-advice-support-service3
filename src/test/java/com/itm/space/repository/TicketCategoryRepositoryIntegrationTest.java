package com.itm.space.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.TicketCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TicketCategoryRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;

    @Test
    @DisplayName("Создание TicketCategory в БД - успешно")
    @ExpectedDataSet(value = "dataset/repository/TicketCategoryRepository/save/01-expectedTicketCategory.yaml")
    void createTicketCategorySuccess() {
        TicketCategory ticketCategoryToSave = TicketCategory.builder()
                .id(1)
                .name("Юридические вопросы")
                .description("Вопросы юридического характера")
                .build();

        assertDoesNotThrow(() -> ticketCategoryRepository.save(ticketCategoryToSave));

        TicketCategory foundTicketCategory = ticketCategoryRepository.findById(ticketCategoryToSave.getId())
                .orElseThrow();

       Assertions.assertNotNull(foundTicketCategory);
        assertEquals(foundTicketCategory.getName(), ticketCategoryToSave.getName());
        assertEquals(foundTicketCategory.getDescription(), ticketCategoryToSave.getDescription());
    }

    @Test
    @DisplayName("Обновление TicketCategory в БД - успешно")
    @DataSet(value = "dataset/repository/TicketCategoryRepository/update/01-ticketCategory.yaml",
            cleanBefore = true,
            cleanAfter = true)
    @ExpectedDataSet(value = "dataset/repository/TicketCategoryRepository/update/01-expectedTicketCategory.yaml")
    void updateTicketCategorySuccess() {
        Integer existingTicketCategoryId = 3;

        TicketCategory existingTicketCategory = ticketCategoryRepository.findById(existingTicketCategoryId)
                .orElseThrow();

        String newNameForTicketCategory = "Прочее";
        String newDescriptionForTicketCategory = "Общие или неклассифицированные вопросы";

        existingTicketCategory.setName(newNameForTicketCategory);
        existingTicketCategory.setDescription(newDescriptionForTicketCategory);
        ticketCategoryRepository.save(existingTicketCategory);

        TicketCategory updatedTicketCategory = ticketCategoryRepository.findById(existingTicketCategoryId)
                .orElseThrow();

        assertNotNull(updatedTicketCategory);
        assertEquals(newNameForTicketCategory, updatedTicketCategory.getName());
        assertEquals(newDescriptionForTicketCategory, updatedTicketCategory.getDescription());
    }

}
