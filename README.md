Assumptions:
Single CEO: One record with no manager (ManagerId is null).
CSV Format: The first line is a header, and columns are fixed: id, firstName, lastName, salary, managerId.
Valid Data: CSV contains valid data types for each column.

Run Configuration:
Environment variables can be set for below variables. It will be defaulted to the values in example if none is specified or if there is a parsing issue
1. File path with name: FILE_PATH
    Example FILE_PATH="src/main/resources/test2.csv"
2. MANAGER's min salary in percentage: MANAGER_MIN_AVG_SALARY_IN_PERCENTAGE
    Example MANAGER_MIN_AVG_SALARY_IN_PERCENTAGE=20
3. MANAGER's min salary in percentage: MANAGER_MAX_AVG_SALARY_IN_PERCENTAGE
   Example MANAGER_MAX_AVG_SALARY_IN_PERCENTAGE=50
4. Reporting Level Threshold: REPORTING_LEVEL_THRESHOLD
   Example REPORTING_LEVEL_THRESHOLD=4
