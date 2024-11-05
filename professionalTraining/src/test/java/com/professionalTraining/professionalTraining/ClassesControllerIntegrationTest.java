package com.professionalTraining.professionalTraining;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.professionalTraining.professionalTraining.controllers.ClassesController;
import com.professionalTraining.professionalTraining.dto.ClassesDTO;
import com.professionalTraining.professionalTraining.services.ClassesService;
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
import org.springframework.test.web.servlet.MvcResult;

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
public class ClassesControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassesService classesService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createClass_ShouldReturnCreatedClass() throws Exception {
        ClassesDTO classesDTO = new ClassesDTO();
        classesDTO.setName("Java Basics");
        classesDTO.setRoomNum(101);
        classesDTO.setInstructorId(1L);
        when(classesService.saveClass(any(ClassesDTO.class))).thenReturn(classesDTO);
        String json = objectMapper.writeValueAsString(classesDTO);
        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java Basics"))
                .andExpect(jsonPath("$.roomNum").value(101))
                .andExpect(jsonPath("$.instructorId").value(1L));
        verify(classesService).saveClass(any(ClassesDTO.class));
    }

    @Test
    public void getAllClasses_ShouldReturnListOfClasses() throws Exception {
        ClassesDTO classesDTO1 = new ClassesDTO();
        classesDTO1.setName("Java Basics");
        classesDTO1.setRoomNum(101);
        classesDTO1.setInstructorId(1L);

        ClassesDTO classesDTO2 = new ClassesDTO();
        classesDTO2.setName("Spring Basics");
        classesDTO2.setRoomNum(102);
        classesDTO2.setInstructorId(2L);

        List<ClassesDTO> classesDTOList = new ArrayList<>();
        classesDTOList.add(classesDTO1);
        classesDTOList.add(classesDTO2);

        when(classesService.getAllClasses()).thenReturn(classesDTOList);

        mockMvc.perform(get("/classes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java Basics"))
                .andExpect(jsonPath("$[0].roomNum").value(101))
                .andExpect(jsonPath("$[0].instructorId").value(1L))
                .andExpect(jsonPath("$[1].name").value("Spring Basics"))
                .andExpect(jsonPath("$[1].roomNum").value(102))
                .andExpect(jsonPath("$[1].instructorId").value(2L));

        verify(classesService, times(1)).getAllClasses();
    }

    @Test
    public void getClassById_ShouldReturnClass() throws Exception {
        ClassesDTO classesDTO = new ClassesDTO();
        classesDTO.setName("Java Basics");
        classesDTO.setRoomNum(101);
        classesDTO.setInstructorId(1L);

        when(classesService.getClassById(anyLong())).thenReturn(classesDTO);

        mockMvc.perform(get("/classes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java Basics"))
                .andExpect(jsonPath("$.roomNum").value(101))
                .andExpect(jsonPath("$.instructorId").value(1L));

        verify(classesService, times(1)).getClassById(1L);
    }

    @Test
    public void updateClass_ShouldReturnUpdatedClass() throws Exception {
        ClassesDTO updatedClassesDTO = new ClassesDTO();
        updatedClassesDTO.setName("Java Advanced");
        updatedClassesDTO.setRoomNum(201);
        updatedClassesDTO.setInstructorId(1L);

        when(classesService.updateClass(anyLong(), any(ClassesDTO.class))).thenReturn(updatedClassesDTO);

        mockMvc.perform(put("/classes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClassesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java Advanced"))
                .andExpect(jsonPath("$.roomNum").value(201))
                .andExpect(jsonPath("$.instructorId").value(1L));

        verify(classesService, times(1)).updateClass(anyLong(), any(ClassesDTO.class));
    }

    @Test
    public void deleteClass_ShouldReturnNoContent() throws Exception {
        doNothing().when(classesService).deleteClass(anyLong());

        mockMvc.perform(delete("/classes/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(classesService, times(1)).deleteClass(1L);
    }
}
