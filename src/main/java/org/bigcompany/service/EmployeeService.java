package org.bigcompany.service;

import org.bigcompany.model.Employee;

import java.util.*;

public class EmployeeService {

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

    public Map<Employee, Double> findManagersPaidLess(Map<Integer, Employee> employeeMap) {
        Map<Employee, Double> underpaidManagers = new HashMap<>();
        for (Employee manager : employeeMap.values()) {
            List<Employee> directReports = getDirectReports(manager, employeeMap);
            if (!directReports.isEmpty()) {
                double avgSubordinateSalary = directReports.stream()
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .orElse(0.0);
                double minimumRequiredSalary = avgSubordinateSalary * 1.2;
                if (manager.getSalary() < minimumRequiredSalary) {
                    underpaidManagers.put(manager, minimumRequiredSalary - manager.getSalary());
                }
            }
        }
        return underpaidManagers;
    }

    public Map<Employee, Double> findManagersPaidMore(Map<Integer, Employee> employeeMap) {
        Map<Employee, Double> overpaidManagers = new HashMap<>();
        for (Employee manager : employeeMap.values()) {
            List<Employee> directReports = getDirectReports(manager, employeeMap);
            if (!directReports.isEmpty()) {
                double avgSubordinateSalary = directReports.stream()
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .orElse(0.0);
                double maximumAllowedSalary = avgSubordinateSalary * 1.5;
                if (manager.getSalary() > maximumAllowedSalary) {
                    overpaidManagers.put(manager, manager.getSalary() - maximumAllowedSalary);
                }
            }
        }
        return overpaidManagers;
    }

    public Map<Employee, Integer> findEmployeesWithLongReportingLines(Map<Integer, Employee> employeeMap, byte maxDepthAllowed) {
        Map<Employee, Integer> longReportingEmployees = new HashMap<>();
        for (Employee employee : employeeMap.values()) {
            int depth = employee.getLevel() != null ? employee.getLevel() : 0;
            if (depth > maxDepthAllowed + 1) { // CEO is Level 1
                longReportingEmployees.put(employee, depth - 1);
            }
        }
        return longReportingEmployees;
    }

    private List<Employee> getDirectReports(Employee manager, Map<Integer, Employee> employeeMap) {
        List<Employee> reports = new ArrayList<>();
        for (Employee e : employeeMap.values()) {
            if (manager.getId().equals(e.getManagerId())) {
                reports.add(e);
            }
        }
        return reports;
    }
}
