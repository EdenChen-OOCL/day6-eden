package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Company;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(0, "OOCL"));
        companies.add(new Company(1, "Tencent"));
        companies.add(new Company(2, "Alibaba"));
    }

    public List<Company> getAll() {
        return companies;
    }
}
