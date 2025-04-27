package org.bigcompany.parser;

import org.bigcompany.exception.MalformedLineException;
import org.bigcompany.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeParserTest {

    @Test
    void testValidEmployeeParsing() throws MalformedLineException {
        String line = "101,John,Doe,75000,100";
        int lineNumber = 2;

        Employee employee = EmployeeParser.getEmployee(line, lineNumber);

        assertEquals(101, employee.getId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(75000.0, employee.getSalary());
        assertEquals(100, employee.getManagerId());
    }

    @Test
    void testValidEmployeeParsingWithoutManager() throws MalformedLineException {
        String line = "102,Jane,Smith,85000";
        int lineNumber = 3;

        Employee employee = EmployeeParser.getEmployee(line, lineNumber);

        assertEquals(102, employee.getId());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals(85000.0, employee.getSalary());
        assertNull(employee.getManagerId());
    }

    @Test
    void testMalformedLineExceptionThrown() {
        String badLine = "103,MissingSalary";
        int lineNumber = 4;

        Exception exception = assertThrows(MalformedLineException.class, () -> EmployeeParser.getEmployee(badLine, lineNumber));

        String expectedMessage = "Expected at least 4 columns";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
