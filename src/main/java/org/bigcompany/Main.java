package org.bigcompany;

import org.bigcompany.model.Employee;
import org.bigcompany.parser.EmployeeParser;
import org.bigcompany.service.EmployeeHierarchyService;
import org.bigcompany.service.EmployeeCompensationService;
import org.bigcompany.service.impl.EmployeeHierarchyServiceImpl;
import org.bigcompany.service.impl.EmployeeCompensationServiceImpl;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String filePath = System.getenv().getOrDefault("FILE_PATH", "src/main/resources/test2.csv");
        int minSalaryPercent = parseEnv("MANAGER_MIN_AVG_SALARY_IN_PERCENTAGE", 20);
        int maxSalaryPercent = parseEnv("MANAGER_MAX_AVG_SALARY_IN_PERCENTAGE", 50);
        byte maxReportingDepth = parseLevelInput();

        EmployeeParser employeeParser = new EmployeeParser();
        EmployeeHierarchyService hierarchyService = new EmployeeHierarchyServiceImpl();
        EmployeeCompensationService compensationService = new EmployeeCompensationServiceImpl();

        try {
            Map<Integer, Employee> employeeMap = employeeParser.readEmployees(filePath);
            System.out.println("Employees loaded successfully.");

            hierarchyService.assignLevels(employeeMap);

            printOverAndUnderPaidManagers(compensationService.findManagersPaidLess(employeeMap,minSalaryPercent), "\nManagers earning LESS than " + minSalaryPercent + "% over average of subordinates:", "%s %s (ID: %d) - Underpaid by %.2f%n");
            printOverAndUnderPaidManagers(compensationService.findManagersPaidMore(employeeMap,maxSalaryPercent), "\nManagers earning MORE than " + maxSalaryPercent + "% over average of subordinates:", "%s %s (ID: %d) - Overpaid by %.2f%n");
            printLongReportingLine(hierarchyService, employeeMap, maxReportingDepth);

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    private static void printLongReportingLine(EmployeeHierarchyService hierarchyService, Map<Integer, Employee> employeeMap, byte maxReportingDepth) {
        Map<Employee, Integer> longReportingEmployees = hierarchyService.findEmployeesWithLongReportingLines(employeeMap, maxReportingDepth);
        System.out.println("\nEmployees with too long reporting lines (>4 managers):");
        longReportingEmployees.forEach((employee, depth) -> System.out.printf("%s %s (ID: %d) - Reporting chain length: %d%n",
                employee.getFirstName(), employee.getLastName(), employee.getId(), depth));
    }

    private static void printOverAndUnderPaidManagers(Map<Employee, Double> compensationService, String message, String format) {
        System.out.println(message);
        compensationService.forEach((manager, diff) -> System.out.printf(format,
                manager.getFirstName(), manager.getLastName(), manager.getId(), diff));
    }

    private static int parseEnv(String key, int defaultValue) {
        String value = System.getenv(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            System.out.println("Issue with the Min and max percentage value given. Defaulting to 20/50");
            return defaultValue;
        }
    }

    private static byte parseLevelInput() {
        String value = System.getenv("REPORTING_LEVEL_THRESHOLD");
        try {
            return value != null ? Byte.parseByte(value) : (byte) 4;
        } catch (NumberFormatException e) {
            System.out.println("Issue with the Hierarchy value given. Defaulting 4");
            return (byte) 4;
        }
    }

//    private static <T extends Number> T parseEnv(String key, T defaultValue) {
//        String value = System.getenv(key);
//        try {
//            if (value != null) {
//                if (defaultValue instanceof Integer) {
//                    return (T) Integer.valueOf(value);
//                } else if (defaultValue instanceof Byte) {
//                    return (T) Byte.valueOf(value);
//                }
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Issue with the value for " + key + ". Defaulting to " + defaultValue);
//        }
//        return defaultValue;
//    }
}
