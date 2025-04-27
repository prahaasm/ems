package org.bigcompany.service;

import org.bigcompany.model.Employee;
import java.util.Map;

public interface EmployeeCompensationService {
    Map<Employee, Double> findManagersPaidLess(Map<Integer, Employee> employeeMap,Integer minPercentage);
    Map<Employee, Double> findManagersPaidMore(Map<Integer, Employee> employeeMap,Integer maxPercentage);
}
