package org.bigcompany.service;

import org.bigcompany.model.Employee;
import java.util.Map;

public interface EmployeeHierarchyService {
    void assignLevels(Map<Integer, Employee> employeeMap);
    Map<Employee, Integer> findEmployeesWithLongReportingLines(Map<Integer, Employee> employeeMap, byte maxDepthAllowed);
}
