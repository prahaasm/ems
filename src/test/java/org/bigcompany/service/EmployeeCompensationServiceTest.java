package org.bigcompany.service;

import org.bigcompany.model.Employee;
import org.bigcompany.service.impl.EmployeeCompensationServiceImpl;
import org.bigcompany.service.impl.EmployeeHierarchyServiceImpl;
import org.bigcompany.testdata.TestEmployeeDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeCompensationServiceTest {

    private EmployeeCompensationService compensationService;
    private Map<Integer, Employee> employeeMap;

    @BeforeEach
    void setUp() {
        EmployeeHierarchyService hierarchyService = new EmployeeHierarchyServiceImpl();
        compensationService = new EmployeeCompensationServiceImpl();
        employeeMap = TestEmployeeDataFactory.createSampleEmployeeData();
        hierarchyService.assignLevels(employeeMap);
    }

    @Test
    void shouldFindManagersPaidLess() {
        Map<Employee, Double> underpaidManagers = compensationService.findManagersPaidLess(employeeMap,20);
        assertTrue(underpaidManagers.containsKey(employeeMap.get(2)), "Manager (ID:2) should be underpaid.");
    }

    @Test
    void shouldFindManagersPaidMore() {
        employeeMap.get(2).setSalary(100000.0); // Overpay the manager artificially
        Map<Employee, Double> overpaidManagers = compensationService.findManagersPaidMore(employeeMap,50);
        assertTrue(overpaidManagers.containsKey(employeeMap.get(2)), "Manager (ID:2) should be overpaid.");
    }
}
