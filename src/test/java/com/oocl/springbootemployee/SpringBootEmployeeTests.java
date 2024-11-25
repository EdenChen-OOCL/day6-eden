package com.oocl.springbootemployee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class SpringBootEmployeeTests {

    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JacksonTester<List<Employee>> json;
    @Autowired
    private JacksonTester<Employee> jsonObject;

    @BeforeEach
    void setUp() {
        employeeRepository.getAll().clear();
        employeeRepository.save(new Employee(0, "Lily", 20, Gender.FEMALE, 8000));
        employeeRepository.save(new Employee(1, "Tom", 21, Gender.MALE, 9000));
        employeeRepository.save(new Employee(2, "Jacky", 19, Gender.MALE, 7000));
    }

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
    public void should_should_return_a_employee_when_get_by_id_given_employee_id_0() throws Exception {
        // Given
        Employee expectEmployee = employeeRepository.getAll().get(0);
        // When
        // Then
        client.perform(MockMvcRequestBuilders.get("/employees/" + 0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Gender.FEMALE.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(8000))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void should_return_employee_by_gender() throws Exception {
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", "FEMALE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(Gender.FEMALE.name()))
        ;
    }

    @Test
    void should_creat_employee_when_post_given_employee() throws Exception {
        String givenEmployee = " {\n" +
                "        \"name\": \"Lily\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"FEMALE\",\n" +
                "        \"salary\": 8000.0\n" +
                "    }";
        Employee givenEmployeeObject = jsonObject.parseObject(givenEmployee);
        client.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(givenEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(givenEmployeeObject.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(givenEmployeeObject.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(givenEmployeeObject.getGender().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(givenEmployeeObject.getSalary()))
                ;
    }

    @Test
    public void should_update_employee_age_salary_when_put_given_age_21_salary_7000() throws Exception {
        // Given
        String newEmployee = " {\n" +
                "        \"id\": 0,\n" +
                "        \"age\": 21,\n" +
                "        \"salary\": 7000.0\n" +
                "    }";
        Employee givenEmployeeObject = jsonObject.parseObject(newEmployee);
        // When
        client.perform(MockMvcRequestBuilders.put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(givenEmployeeObject.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(givenEmployeeObject.getSalary()))
        ;
        // Then
    }

    @Test
    public void should_remove_employee_when_delete_given_employee_id() throws Exception {
        // Given
        int employeeId = 0;
        // When
        client.perform(MockMvcRequestBuilders.delete("/employees/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // Then
        assertThat(employeeRepository.getAll().size()).isEqualTo(2);
        assertNull(employeeRepository.getEmployeeById(employeeId));
    }

    @Test
    public void should_page_query_employee_when_get_all_given_page_and_page_size() throws Exception {
        // Given
        List<Employee> expectEmployees = List.of(
                new Employee(0, "Lily", 20, Gender.FEMALE, 8000),
                new Employee(1, "Tom", 21, Gender.MALE, 9000),
                new Employee(2, "Jacky", 19, Gender.MALE, 7000),
                new Employee(3, "Jacky", 19, Gender.MALE, 7000),
                new Employee(4, "Jacky", 19, Gender.MALE, 7000)
        );
        for (int i = 3; i < 10; i++) {
            employeeRepository.save(new Employee(i, "Jacky", 19, Gender.MALE, 7000));
        }
        int pageIndex = 1;
        int pageSize = 5;
        // When
        String responseBody = client.perform(MockMvcRequestBuilders.get("/employees")
                .param("pageIndex", String.valueOf(pageIndex))
                .param("pageSize", String.valueOf(pageSize)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        // Then
        List<Employee> resultEmployee = json.parseObject(responseBody);

        assertThat(resultEmployee).hasSize(pageSize);
        assertThat(expectEmployees).isEqualTo(resultEmployee);
    }
}
