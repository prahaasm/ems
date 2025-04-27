package org.bigcompany;

import org.bigcompany.model.Employee;
import org.bigcompany.parser.EmployeeParser;
import org.bigcompany.service.EmployeeService;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : "src/main/resources/test2.csv";

        EmployeeParser employeeParser = new EmployeeParser();
        EmployeeService employeeService = new EmployeeService();

        try {
            Map<Integer, Employee> employeeMap = employeeParser.readEmployees(filePath);
            System.out.println("Employees loaded successfully.");

            employeeService.assignLevels(employeeMap);

            // 1. Managers underpaid
            Map<Employee, Double> underpaidManagers = employeeService.findManagersPaidLess(employeeMap);
            System.out.println("\nManagers earning LESS than 20% over average of subordinates:");
            underpaidManagers.forEach((manager, diff) -> System.out.printf("%s %s (ID: %d) - Underpaid by %.2f%n",
                    manager.getFirstName(), manager.getLastName(), manager.getId(), diff));

            // 2. Managers overpaid
            Map<Employee, Double> overpaidManagers = employeeService.findManagersPaidMore(employeeMap);
            System.out.println("\nManagers earning MORE than 50% over average of subordinates:");
            overpaidManagers.forEach((manager, diff) -> System.out.printf("%s %s (ID: %d) - Overpaid by %.2f%n",
                    manager.getFirstName(), manager.getLastName(), manager.getId(), diff));

            // 3. Employees with too long reporting line
            Map<Employee, Integer> longReportingEmployees = employeeService.findEmployeesWithLongReportingLines(employeeMap, (byte) 4);
            System.out.println("\nEmployees with too long reporting lines (>4 managers):");
            longReportingEmployees.forEach((employee, depth) -> System.out.printf("%s %s (ID: %d) - Reporting chain length: %d%n",
                    employee.getFirstName(), employee.getLastName(), employee.getId(), depth));

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}
