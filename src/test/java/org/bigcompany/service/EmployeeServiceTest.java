package org.bigcompany.service;

import org.bigcompany.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private Map<Integer, Employee> employeeMap;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
        employeeMap = new HashMap<>();

        // CEO (level 1)
        Employee ceo = new Employee();
        ceo.setId(1);
        ceo.setFirstName("Rajesh");
        ceo.setLastName("Sharma");
        ceo.setSalary(100000.0);
        ceo.setManagerId(null); // CEO has no manager

        // Manager (level 2)
        Employee manager = new Employee();
        manager.setId(2);
        manager.setFirstName("Amit");
        manager.setLastName("Verma");
        manager.setSalary(41000.0);
        manager.setManagerId(1); // Reports to CEO

        // Subordinate 1 (level 3)
        Employee subordinate1 = new Employee();
        subordinate1.setId(3);
        subordinate1.setFirstName("Sub1");
        subordinate1.setLastName("Test");
        subordinate1.setSalary(40000.0);
        subordinate1.setManagerId(2); // Reports to Manager

        // Subordinate 2 (level 3)
        Employee subordinate2 = new Employee();
        subordinate2.setId(4);
        subordinate2.setFirstName("Sub2");
        subordinate2.setLastName("Test");
        subordinate2.setSalary(39000.0);
        subordinate2.setManagerId(2); // Reports to Manager

        // Subordinate 3 (level 4)
        Employee subordinate3 = new Employee();
        subordinate3.setId(5);
        subordinate3.setFirstName("Sub3");
        subordinate3.setLastName("Test");
        subordinate3.setSalary(35000.0);
        subordinate3.setManagerId(3); // Reports to Subordinate 1

        // Subordinate 4 (level 5)
        Employee subordinate4 = new Employee();
        subordinate4.setId(6);
        subordinate4.setFirstName("Sub4");
        subordinate4.setLastName("Test");
        subordinate4.setSalary(34000.0);
        subordinate4.setManagerId(5); // Reports to Subordinate 3

        // Subordinate 5 (level 6)
        Employee subordinate5 = new Employee();
        subordinate5.setId(7);
        subordinate5.setFirstName("Sub5");
        subordinate5.setLastName("Test");
        subordinate5.setSalary(33000.0);
        subordinate5.setManagerId(6); // Reports to Subordinate 4

        // Add all employees to the employeeMap
        employeeMap.put(1, ceo);
        employeeMap.put(2, manager);
        employeeMap.put(3, subordinate1);
        employeeMap.put(4, subordinate2);
        employeeMap.put(5, subordinate3);
        employeeMap.put(6, subordinate4);
        employeeMap.put(7, subordinate5);

        // Assign levels to employees
        employeeService.assignLevels(employeeMap);
    }

    @Test
    void testFindManagersPaidLess() {
        // Find managers who are paid less than their subordinates' average salary
        Map<Employee, Double> underpaid = employeeService.findManagersPaidLess(employeeMap);
        assertTrue(underpaid.containsKey(employeeMap.get(2)), "Manager should be underpaid.");
    }

    @Test
    void testFindManagersPaidMore() {
        // Overpay the manager
        employeeMap.get(2).setSalary(100000.0); // Overpay the manager
        Map<Employee, Double> overpaid = employeeService.findManagersPaidMore(employeeMap);
        assertTrue(overpaid.containsKey(employeeMap.get(2)), "Manager should be overpaid.");
    }

    @Test
    void testFindEmployeesWithLongReportingLines() {
        // Test employees with reporting lines longer than the allowed limit
        Map<Employee, Integer> longLines = employeeService.findEmployeesWithLongReportingLines(employeeMap, (byte) 4);
        assertTrue(longLines.containsKey(employeeMap.get(7)), "Subordinate5 should have too long reporting line.");
        assertFalse(longLines.containsKey(employeeMap.get(6)), "Subordinate4 should NOT have too long reporting line.");
    }
}
