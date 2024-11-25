package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;

import java.awt.*;
import java.util.List;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootEmployeeTests {

    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DefaultMockMvcBuilder mockMvcBuilder;

    @Test
    public void should_get_employee_list_when_call_get_all_given_no_parameter() throws Exception {
        // given
        List<Employee> employees = employeeRepository.getAll();

        // when
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employees.get(0).getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(employees.get(1).getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(employees.get(2).getId()))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$[0]", employees.get(0)))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$[1]", employees.get(1))

        ;
        // then
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
