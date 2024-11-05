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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void getAllClasses_ShouldReturnPageOfClasses() throws Exception {
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

        Page<ClassesDTO> classesDTOPage = new PageImpl<>(classesDTOList, PageRequest.of(0, 10), classesDTOList.size());

        when(classesService.getAllClasses(any(Pageable.class))).thenReturn(classesDTOPage);

        mockMvc.perform(get("/classes?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2)) // Check content length
                .andExpect(jsonPath("$.content[0].name").value("Java Basics"))
                .andExpect(jsonPath("$.content[0].roomNum").value(101))
                .andExpect(jsonPath("$.content[0].instructorId").value(1L))
                .andExpect(jsonPath("$.content[1].name").value("Spring Basics"))
                .andExpect(jsonPath("$.content[1].roomNum").value(102))
                .andExpect(jsonPath("$.content[1].instructorId").value(2L));

        verify(classesService, times(1)).getAllClasses(any(Pageable.class)); // Verify service interaction with pageable
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
