package ru.conveyor.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.conveyor.api.dto.FactoryStatusDto;
import ru.conveyor.api.dto.NewConveyorValueDto;
import ru.conveyor.data.IntersectionPoint;
import ru.conveyor.service.FactoryService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FactoryControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FactoryService factoryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();
    }

    @Test
    void factoryApiTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/factory/conveyor/a/status")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();


        List<Integer> statusA = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        assertThat(statusA.size(), is(factoryService.getStatusConveyorA().size()));


        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/factory/conveyor/b/status")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();


        List<Integer> statusB = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        assertThat(statusB.size(), is(factoryService.getStatusConveyorB().size()));


        // Push A
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/factory/conveyor/a/push")
            .content(objectMapper.writeValueAsBytes(new NewConveyorValueDto(5)))
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

        Integer returnedValue = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertThat(returnedValue, is(statusA.get(statusA.size() - 1)));

        // Validate A
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/factory/conveyor/a/status")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();


        statusA = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        assertThat(statusA.get(0), is(5));
        assertThat(statusA.get(statusA.size() - 1), not(returnedValue));


        // Push B
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/factory/conveyor/b/push")
            .content(objectMapper.writeValueAsBytes(new NewConveyorValueDto(5)))
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

        returnedValue = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertThat(returnedValue, is(statusB.get(statusB.size() - 1)));

        // Validate B
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/factory/conveyor/b/status")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();


        statusB = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        assertThat(statusB.get(0), is(5));
        assertThat(statusB.get(statusB.size() - 1), not(returnedValue));


        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/factory/status")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();


        FactoryStatusDto factoryStatusDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FactoryStatusDto.class);

        // Validate intersection points
        for (IntersectionPoint point : factoryStatusDto.getIntersectionPoints()) {
            int valueA = factoryStatusDto.getConveyorA().get(point.getIndexA());
            int valueB = factoryStatusDto.getConveyorB().get(point.getIndexB());

            assertThat(valueA, CoreMatchers.is(valueB));
        }

        // Can't push non-prime number
        mockMvc.perform(MockMvcRequestBuilders.post("/factory/conveyor/b/push")
            .content(objectMapper.writeValueAsBytes(new NewConveyorValueDto(4)))
            .contentType("application/json"))
            .andExpect(status().isBadRequest())
            .andReturn();
    }

}
