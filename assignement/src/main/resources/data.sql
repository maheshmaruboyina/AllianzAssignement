
INSERT INTO USERS (USERNAME, PASSWORD, STATUS) VALUES ('mahesh', '$2y$12$E1H7nMYLocsUaIQJEZydvOwhi0kKw4HIQ8AzCf78oGpsUgXfbnjXC', 'active');
INSERT INTO USERS (USERNAME, PASSWORD, STATUS) VALUES ('allianz', '$2y$12$mBOydZTwyijX0I7QfC18ee2QqpxnJamjdKhHvIi1Uc19LVOz4ZCdS', 'active');

INSERT INTO EMPLOYEE (ID, EMPLOYEE_NAME, EMPLOYEE_AGE, EMPLOYEE_SALARY) VALUES (LPAD(employee_seq.NEXTVAL, 10,'0'),'David', 46, '90000');
INSERT INTO EMPLOYEE (ID, EMPLOYEE_NAME, EMPLOYEE_AGE, EMPLOYEE_SALARY) VALUES (LPAD(employee_seq.NEXTVAL, 10,'0'),'Warner', 32, '105000');
INSERT INTO EMPLOYEE (ID, EMPLOYEE_NAME, EMPLOYEE_AGE, EMPLOYEE_SALARY) VALUES (LPAD(employee_seq.NEXTVAL, 10,'0'),'Mike', 45, '13000');

