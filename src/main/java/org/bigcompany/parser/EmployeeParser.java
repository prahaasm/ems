package org.bigcompany.parser;

import org.bigcompany.exception.MalformedLineException;
import org.bigcompany.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeParser {

    public Map<Integer, Employee> readEmployees(String filePath) throws IOException {
        Map<Integer, Employee> employeeMap = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();

        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            csvReader.readLine(); // Skipping header by calling readLine once.
            int lineNumber = 1;

            while ((line = csvReader.readLine()) != null) {
                lineNumber++;
                try {
                    Employee emp = getEmployee(line, lineNumber);

                    employeeMap.put(emp.getId(), emp);

                } catch (MalformedLineException | NumberFormatException e) {
                    errorMessages.add("Error at line " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        if (!errorMessages.isEmpty()) {
            System.out.println("Parsing Errors Consolidated:");
            for (String error : errorMessages) {
                System.out.println(error);
            }
        }

        return employeeMap;
    }

    public static Employee getEmployee(String line, int lineNumber) throws MalformedLineException {
        String[] fields = line.split(",");

        if (fields.length < 4) {
            throw new MalformedLineException(String.format(
                    "Expected at least 4 columns, got %d at line %d: %s", fields.length, lineNumber, line
            ));
        }

        Employee emp = new Employee();
        emp.setId(Integer.parseInt(fields[0].trim()));
        emp.setFirstName(fields[1].trim());
        emp.setLastName(fields[2].trim());
        emp.setSalary(Double.parseDouble(fields[3].trim()));

        if (fields.length > 4 && !fields[4].trim().isEmpty()) {
            emp.setManagerId(Integer.parseInt(fields[4].trim()));
        }
        return emp;
    }
}