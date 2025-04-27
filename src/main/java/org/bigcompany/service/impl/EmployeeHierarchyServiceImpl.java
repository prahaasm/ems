
package org.bigcompany.service.impl;

import org.bigcompany.model.Employee;
import org.bigcompany.service.EmployeeHierarchyService;

import java.util.HashMap;
import java.util.Map;

public class EmployeeHierarchyServiceImpl implements EmployeeHierarchyService {

    @Override
    public void assignLevels(Map<Integer, Employee> employeeMap) {
        for (Employee employee : employeeMap.values()) {
            if (employee.getLevel() == null) {
                assignEmployeeLevel(employee, employeeMap);
            }
        }
    }

    private byte assignEmployeeLevel(Employee employee, Map<Integer, Employee> employeeMap) {
        if (employee.getManagerId() == null) {
            employee.setLevel((byte) 1);
        } else {
            Employee manager = employeeMap.get(employee.getManagerId());
            if (manager.getLevel() == null) {
                manager.setLevel(assignEmployeeLevel(manager, employeeMap));
            }
            employee.setLevel((byte) (manager.getLevel() + 1));
        }
        return employee.getLevel();
    }

    @Override
    public Map<Employee, Integer> findEmployeesWithLongReportingLines(Map<Integer, Employee> employeeMap, byte maxDepthAllowed) {
        Map<Employee, Integer> longReportingEmployees = new HashMap<>();
        for (Employee employee : employeeMap.values()) {
            int depth = employee.getLevel() != null ? employee.getLevel() : 0;
            if (depth > maxDepthAllowed + 1) {
                longReportingEmployees.put(employee, depth - 1);
            }
        }
        return longReportingEmployees;
    }
}
