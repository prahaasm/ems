package org.bigcompany.parser;

import org.bigcompany.exception.MalformedLineException;
import org.bigcompany.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeParserTest {
    private static final Integer LINE_NUMBER = 2;

    @Test
    void testValidEmployeeParsing() throws MalformedLineException {
        String line = "101,John,Doe,75000,100";

        Employee employee = EmployeeParser.getEmployee(line, LINE_NUMBER);

        assertEquals(101, employee.getId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(75000.0, employee.getSalary());
        assertEquals(100, employee.getManagerId());
    }

    @Test
    void testValidEmployeeParsingWithoutManager() throws MalformedLineException {
        String line = "102,Jane,Smith,85000";

        Employee employee = EmployeeParser.getEmployee(line, LINE_NUMBER);

        assertEquals(102, employee.getId());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals(85000.0, employee.getSalary());
        assertNull(employee.getManagerId());
    }

    @Test
    void testMalformedLineExceptionThrown() {
        String badLine = "103,MissingSalary";

        Exception exception = assertThrows(MalformedLineException.class, () -> EmployeeParser.getEmployee(badLine, LINE_NUMBER));

        String expectedMessage = "Expected at least 4 columns";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEmptyLine() {
        String emptyLine = "";

        Exception exception = assertThrows(MalformedLineException.class, () -> EmployeeParser.getEmployee(emptyLine, LINE_NUMBER));

        String expectedMessage = "Expected at least 4 columns";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLineWithExtraFields() throws MalformedLineException {
        String line = "104,Senthil,Nathan,60000,200,ExtraField";

        Employee employee = EmployeeParser.getEmployee(line, LINE_NUMBER);

        assertEquals(104, employee.getId());
        assertEquals("Mark", employee.getFirstName());
        assertEquals("Johnson", employee.getLastName());
        assertEquals(60000.0, employee.getSalary());
        assertEquals(200, employee.getManagerId());
    }

    @Test
    void testLineWithWhitespaceInFields() throws MalformedLineException {
        String line = "105,  Anna  ,  Durai  ,  95000  ,  300  ";

        Employee employee = EmployeeParser.getEmployee(line, LINE_NUMBER);

        assertEquals(105, employee.getId());
        assertEquals("Alice", employee.getFirstName());
        assertEquals("Brown", employee.getLastName());
        assertEquals(95000.0, employee.getSalary());
        assertEquals(300, employee.getManagerId());
    }

    @Test
    void testInvalidSalaryFormat() {
        String badLine = "106,Steve,Buckner,invalidSalary,400";

        Exception exception = assertThrows(NumberFormatException.class, () -> EmployeeParser.getEmployee(badLine, LINE_NUMBER));

        String expectedMessage = "For input string:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInvalidManagerIdFormat() {
        String badLine = "107,Jonty,Rhodes,50000,invalidManagerId";

        Exception exception = assertThrows(NumberFormatException.class, () -> EmployeeParser.getEmployee(badLine, LINE_NUMBER));

        String expectedMessage = "For input string:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
