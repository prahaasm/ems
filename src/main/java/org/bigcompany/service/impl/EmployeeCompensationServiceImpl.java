package org.bigcompany.service.impl;

import org.bigcompany.model.Employee;
import org.bigcompany.service.EmployeeCompensationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class EmployeeCompensationServiceImpl implements EmployeeCompensationService {

    @Override
    public Map<Employee, Double> findManagersPaidLess(Map<Integer, Employee> employeeMap, Integer minPercentage) {
        Map<Employee, Double> underpaidManagers = new HashMap<>();
        for (Employee manager : employeeMap.values()) {
            List<Employee> directReports = getDirectReports(manager, employeeMap);
            if (!directReports.isEmpty()) {
                double avgSubordinateSalary = directReports.stream()
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .orElse(0.0);
                double minimumRequiredSalary = avgSubordinateSalary * getMultiplier(minPercentage);
                if (manager.getSalary() < minimumRequiredSalary) {
                    underpaidManagers.put(manager, minimumRequiredSalary - manager.getSalary());
                }
            }
        }
        return underpaidManagers;
    }

    @Override
    public Map<Employee, Double> findManagersPaidMore(Map<Integer, Employee> employeeMap, Integer maxPercentage) {
        Map<Employee, Double> overpaidManagers = new HashMap<>();
        for (Employee manager : employeeMap.values()) {
            List<Employee> directReports = getDirectReports(manager, employeeMap);
            if (!directReports.isEmpty()) {
                double avgSubordinateSalary = directReports.stream()
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .orElse(0.0);
                double maximumAllowedSalary = avgSubordinateSalary * getMultiplier(maxPercentage);
                if (manager.getSalary() > maximumAllowedSalary) {
                    overpaidManagers.put(manager, manager.getSalary() - maximumAllowedSalary);
                }
            }
        }
        return overpaidManagers;
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

    private static Double getMultiplier(Integer percentageToAdd) {
        return (100.0 + percentageToAdd) / 100.0;
    }
}
