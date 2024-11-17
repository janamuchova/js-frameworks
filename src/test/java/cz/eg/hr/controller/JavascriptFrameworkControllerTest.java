package cz.eg.hr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class JavascriptFrameworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavascriptFrameworkRepository frameworkRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateFramework() throws Exception {
        JavascriptFramework framework = new JavascriptFramework(1L,"Angular", 5.0);

        mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(framework)))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    void shouldReturnFramework() throws Exception {
        long id = 1L;
        JavascriptFramework framework = new JavascriptFramework(id,"Angular", 5.0);

        when(frameworkRepository.findById(id)).thenReturn(Optional.of(framework));
        mockMvc.perform(get("/frameworks/{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(framework.getName()))
            .andExpect(jsonPath("$.rating").value(framework.getRating()))
            .andDo(print());
    }

    @Test
    void shouldReturnNotFoundFramework() throws Exception {
        long id = 1L;

        when(frameworkRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/frameworks/{id}", id))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void shouldReturnListOfFrameworks() throws Exception {
        List<JavascriptFramework> frameworks = new ArrayList<>(
            Arrays.asList(new JavascriptFramework(1L, "Angular", 4.5),
                new JavascriptFramework(2L, "React", 5.0),
                new JavascriptFramework(3L, "Vue", 4.1)));

        when(frameworkRepository.findAll()).thenReturn(frameworks);
        mockMvc.perform(get("/frameworks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(frameworks.size()))
            .andDo(print());
    }

    @Test
    void shouldReturnNoContentWhenFilter() throws Exception {
        String name = "framework";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("name", name);

        List<JavascriptFramework> frameworks = Collections.emptyList();

        when(frameworkRepository.findAllByNameContaining(name)).thenReturn(frameworks);
        mockMvc.perform(get("/frameworks").params(paramsMap))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    void shouldUpdateFramework() throws Exception {
        long id = 1L;

        JavascriptFramework framework = new JavascriptFramework(id, "Angular", 5.0);
        JavascriptFramework updatedFramework = new JavascriptFramework(id, "New name", 1.0);

        when(frameworkRepository.findById(id)).thenReturn(Optional.of(framework));
        when(frameworkRepository.save(any(JavascriptFramework.class))).thenReturn(updatedFramework);

        mockMvc.perform(put("/frameworks/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFramework)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(updatedFramework.getName()))
            .andExpect(jsonPath("$.rating").value(updatedFramework.getRating()))
            .andDo(print());
    }

    @Test
    void shouldReturnNotFoundUpdateFramework() throws Exception {
        long id = 1L;

        JavascriptFramework updatedFramework = new JavascriptFramework(id, "New name", 1.0);

        when(frameworkRepository.findById(id)).thenReturn(Optional.empty());
        when(frameworkRepository.save(any(JavascriptFramework.class))).thenReturn(updatedFramework);

        mockMvc.perform(put("/frameworks/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFramework)))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void shouldDeleteFramework() throws Exception {
        long id = 1L;

        doNothing().when(frameworkRepository).deleteById(id);
        mockMvc.perform(delete("/frameworks/{id}", id))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

}
