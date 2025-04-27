package org.bigcompany.testdata;

import org.bigcompany.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class TestEmployeeDataFactory {

    public static Map<Integer, Employee> createSampleEmployeeData() {
        Map<Integer, Employee> employeeMap = new HashMap<>();

        employeeMap.put(1, createEmployee(1, "Rajesh", "Sharma", 100000.0, null));
        employeeMap.put(2, createEmployee(2, "Amit", "Verma", 41000.0, 1));
        employeeMap.put(3, createEmployee(3, "Sub1", "Test", 40000.0, 2));
        employeeMap.put(4, createEmployee(4, "Sub2", "Test", 39000.0, 2));
        employeeMap.put(5, createEmployee(5, "Sub3", "Test", 35000.0, 3));
        employeeMap.put(6, createEmployee(6, "Sub4", "Test", 34000.0, 5));
        employeeMap.put(7, createEmployee(7, "Sub5", "Test", 33000.0, 6));

        return employeeMap;
    }

    private static Employee createEmployee(int id, String firstName, String lastName, double salary, Integer managerId) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setManagerId(managerId);
        return employee;
    }
}
