package org.bigcompany.service;

import org.bigcompany.model.Employee;
import org.bigcompany.service.impl.EmployeeHierarchyServiceImpl;
import org.bigcompany.testdata.TestEmployeeDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeHierarchyServiceTest {

    private EmployeeHierarchyService hierarchyService;
    private Map<Integer, Employee> employeeMap;

    @BeforeEach
    void setUp() {
        hierarchyService = new EmployeeHierarchyServiceImpl();
        employeeMap = TestEmployeeDataFactory.createSampleEmployeeData();
        hierarchyService.assignLevels(employeeMap);
    }

    @Test
    void shouldAssignCorrectLevels() {
        assertEquals(Byte.valueOf((byte) 1), employeeMap.get(1).getLevel());
        assertEquals(Byte.valueOf((byte) 2), employeeMap.get(2).getLevel());
        assertEquals(Byte.valueOf((byte) 3), employeeMap.get(3).getLevel());
        assertEquals(Byte.valueOf((byte) 6), employeeMap.get(7).getLevel());
    }

    @Test
    void shouldFindEmployeesWithLongReportingLines() {
        Map<Employee, Integer> longReportingEmployees = hierarchyService.findEmployeesWithLongReportingLines(employeeMap, (byte) 4);
        assertEquals(1, longReportingEmployees.size());
        assertEquals(5, longReportingEmployees.get(employeeMap.get(7)));
    }
}
