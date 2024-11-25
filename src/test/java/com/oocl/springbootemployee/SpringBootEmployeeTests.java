package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.util.Collections;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;

import java.awt.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class SpringBootEmployeeTests {

    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DefaultMockMvcBuilder mockMvcBuilder;
    @Autowired
    private JacksonTester<List<Employee>> json;

    @Test
    public void should_get_employee_list_when_call_get_all_given_no_parameter() throws Exception {
        // given
        List<Employee> employees = employeeRepository.getAll();

        // when
        String responseBody = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andReturn().getResponse().getContentAsString();
        // then
        List<Employee> resultEmployee = json.parseObject(responseBody);
        assertThat(employees).isEqualTo(resultEmployee);
    }

    @Test
    public void should_should_return_a_employee_when_employeeController_given_employee_id_1() {
        // Given

        // When

        // Then
    }

    @Test
    void should_return_employee_by_gender() throws Exception {

        String givenEmployee = " {\n" +
                "        \"id\": 5,\n" +
                "        \"name\": \"Lily\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"FEMALE\",\n" +
                "        \"salary\": 8000.0\n" +
                "    }";
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", "FEMALE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(Gender.FEMALE.name()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Gender.FEMALE))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(8000));
        ;
    }
}
