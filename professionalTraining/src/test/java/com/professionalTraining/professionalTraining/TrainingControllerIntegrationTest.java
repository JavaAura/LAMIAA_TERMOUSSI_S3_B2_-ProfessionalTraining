package com.professionalTraining.professionalTraining;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.professionalTraining.professionalTraining.controllers.TrainingController;
import com.professionalTraining.professionalTraining.dto.TrainingDTO;
import com.professionalTraining.professionalTraining.services.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainingControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingService trainingService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createTraining_ShouldReturnCreatedTraining() throws Exception {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setId(1L);
        trainingDTO.setTitle("Java Basics");
        trainingDTO.setLevel("Beginner");
        trainingDTO.setMinCapacity(1);
        trainingDTO.setMaxCapacity(10);
        trainingDTO.setStartDate(LocalDate.now().plusDays(1));
        trainingDTO.setEndDate(LocalDate.now().plusDays(5));

        when(trainingService.saveTraining(any(TrainingDTO.class))).thenReturn(trainingDTO);
        String json = objectMapper.writeValueAsString(trainingDTO);

        mockMvc.perform(post("/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"))
                .andExpect(jsonPath("$.level").value("Beginner"))
                .andExpect(jsonPath("$.minCapacity").value(1))
                .andExpect(jsonPath("$.maxCapacity").value(10));

        verify(trainingService).saveTraining(any(TrainingDTO.class));
    }

    @Test
    public void getAllTrainings_ShouldReturnListOfTrainings() throws Exception {
        TrainingDTO trainingDTO1 = new TrainingDTO();
        trainingDTO1.setId(1L);
        trainingDTO1.setTitle("Java Basics");
        trainingDTO1.setLevel("Beginner");

        TrainingDTO trainingDTO2 = new TrainingDTO();
        trainingDTO2.setId(2L);
        trainingDTO2.setTitle("Spring Basics");
        trainingDTO2.setLevel("Intermediate");

        List<TrainingDTO> trainingDTOList = new ArrayList<>();
        trainingDTOList.add(trainingDTO1);
        trainingDTOList.add(trainingDTO2);

        when(trainingService.getAllTrainings()).thenReturn(trainingDTOList);

        mockMvc.perform(get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Java Basics"))
                .andExpect(jsonPath("$[1].title").value("Spring Basics"));

        verify(trainingService, times(1)).getAllTrainings();
    }

    @Test
    public void getTrainingById_ShouldReturnTraining() throws Exception {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setId(1L);
        trainingDTO.setTitle("Java Basics");
        trainingDTO.setLevel("Beginner");

        when(trainingService.getTrainingById(anyLong())).thenReturn(trainingDTO);

        mockMvc.perform(get("/trainings/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"))
                .andExpect(jsonPath("$.level").value("Beginner"));

        verify(trainingService, times(1)).getTrainingById(1L);
    }

    @Test
    public void updateTraining_ShouldReturnUpdatedTraining() throws Exception {
        TrainingDTO updatedTrainingDTO = new TrainingDTO();
        updatedTrainingDTO.setId(1L);
        updatedTrainingDTO.setTitle("Java Advanced");
        updatedTrainingDTO.setLevel("Advanced");
        updatedTrainingDTO.setMinCapacity(1);
        updatedTrainingDTO.setMaxCapacity(10);
        updatedTrainingDTO.setStartDate(LocalDate.now().plusDays(1));
        updatedTrainingDTO.setEndDate(LocalDate.now().plusDays(5));

        when(trainingService.updateTraining(anyLong(), any(TrainingDTO.class))).thenReturn(updatedTrainingDTO);

        mockMvc.perform(put("/trainings/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTrainingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Advanced"))
                .andExpect(jsonPath("$.level").value("Advanced"));

        verify(trainingService, times(1)).updateTraining(anyLong(), any(TrainingDTO.class));
    }

    @Test
    public void deleteTraining_ShouldReturnNoContent() throws Exception {
        doNothing().when(trainingService).deleteTraining(anyLong());

        mockMvc.perform(delete("/trainings/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(trainingService, times(1)).deleteTraining(1L);
    }

    @Test
    public void getTrainingsByTitle_ShouldReturnListOfTrainings() throws Exception {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setId(1L);
        trainingDTO.setTitle("Java Basics");
        trainingDTO.setLevel("Beginner");

        List<TrainingDTO> trainingDTOList = new ArrayList<>();
        trainingDTOList.add(trainingDTO);

        when(trainingService.getTrainingsByTitle(any(String.class))).thenReturn(trainingDTOList);

        mockMvc.perform(get("/trainings/search/title")
                        .param("title", "Java Basics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Java Basics"));

        verify(trainingService, times(1)).getTrainingsByTitle("Java Basics");
    }

    @Test
    public void getTrainingsByStatus_ShouldReturnListOfTrainings() throws Exception {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setId(1L);
        trainingDTO.setTitle("Java Basics");
        trainingDTO.setLevel("Beginner");

        List<TrainingDTO> trainingDTOList = new ArrayList<>();
        trainingDTOList.add(trainingDTO);

        when(trainingService.getTrainingsByStatus(any(String.class))).thenReturn(trainingDTOList);

        mockMvc.perform(get("/trainings/search/status")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Java Basics"));

        verify(trainingService, times(1)).getTrainingsByStatus("ACTIVE");
    }

    @Test
    public void getTrainingsByTitleAndLevel_ShouldReturnListOfTrainings() throws Exception {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setId(1L);
        trainingDTO.setTitle("Java Basics");
        trainingDTO.setLevel("Beginner");

        List<TrainingDTO> trainingDTOList = new ArrayList<>();
        trainingDTOList.add(trainingDTO);

        when(trainingService.getTrainingsByTitleAndLevel(any(String.class), any(String.class)))
                .thenReturn(trainingDTOList);

        mockMvc.perform(get("/trainings/search/titleAndLevel")
                        .param("title", "Java Basics")
                        .param("level", "Beginner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Java Basics"));

        verify(trainingService, times(1)).getTrainingsByTitleAndLevel("Java Basics", "Beginner");
    }

}
