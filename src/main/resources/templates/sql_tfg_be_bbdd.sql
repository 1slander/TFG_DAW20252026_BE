CREATE DATABASE `tfg_be_bbdd`;
USE `tfg_be_bbdd`;

-- --------------------------------------------------------
-- 1. USERS TABLE (ADMIN, EMPLOYEE)
-- --------------------------------------------------------

CREATE TABLE USERS ( 
    ID_USER INT auto_increment PRIMARY KEY,
    FIRST_NAME VARCHAR(100) NOT NULL,
    LAST_NAME VARCHAR(250) NOT NULL,
    EMAIL VARCHAR(250) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    IS_ACTIVE BOOLEAN,
    CREATED_AT DATETIME,
    UPDATED_AT DATETIME
);

-- --------------------------------------------------------
-- 2. RESTAURANT TABLE (Menu)
-- --------------------------------------------------------

CREATE TABLE RESTAURANTS (
    ID_RESTAURANT VARCHAR(255) PRIMARY KEY,
    RESTAURANT_NAME VARCHAR(255) NOT NULL,
    ADDRESS VARCHAR(255),
    COUNTRY VARCHAR(100),
    PHONE VARCHAR(20),
    CAPACITY INT,
    TOTAL_TABLES INT
);


-- --------------------------------------------------------
-- 3. EMPLOYEES TABLE (Menu)
-- --------------------------------------------------------

CREATE TABLE EMPLOYEES (
	ID_USER INT PRIMARY KEY,
    ID_RESTAURANT VARCHAR(255) NULL,
    ID_SHIFT INT,
    ID_EMPLOYEE VARCHAR(255),
	EMPLOYEE_TYPE ENUM('TEAM_LEADER','ASSISTANT_MANAGER','MANAGER','EMPLOYEE') DEFAULT ('EMPLOYEE'),
    HOURLY_WAGE DOUBLE,
    HIRE_DATE DATE,
    
    FOREIGN KEY (ID_USER) REFERENCES USERS(ID_USER)
    ON DELETE CASCADE,
    FOREIGN KEY (ID_RESTAURANT) REFERENCES RESTAURANTS(ID_RESTAURANT)
    ON DELETE SET NULL,
    FOREIGN KEY (ID_SHIFT) REFERENCES SHIFTS(ID_SHIFT)
    
);
-- --------------------------------------------------------
-- 4. SHIFTS TABLE (Menu)
-- --------------------------------------------------------

CREATE TABLE SHIFTS (
	ID_SHIFT INT auto_increment PRIMARY KEY,
    ASSIGN_SHIFT VARCHAR(255)
);

-- --------------------------------------------------------
-- 5. ADMINS TABLE (Menu)
-- --------------------------------------------------------

CREATE TABLE ADMINS (
	ID_USER INT PRIMARY KEY,
	ID_RESTAURANT VARCHAR(255) NULL,
    CAN_CREATE_ADMINS BOOLEAN,
    
    FOREIGN KEY (ID_USER) REFERENCES USERS(ID_USER)
    ON DELETE CASCADE,
    FOREIGN KEY (ID_RESTAURANT) REFERENCES RESTAURANTS(ID_RESTAURANT)
    ON DELETE SET NULL
);

-- --------------------------------------------------------
-- 6. TABLES TABLE
-- --------------------------------------------------------

CREATE TABLE TABLES (
    ID_TABLE INT AUTO_INCREMENT PRIMARY KEY,
    TABLE_NUMBER INT NOT NULL,
    TABLE_CAPACITY INT NOT NULL,
    STATUS ENUM('BOOKED','NOT_BOOKED','CONFIRMED','PENDING') NOT NULL, 
    ID_RESTAURANT VARCHAR(255) NOT NULL, 
    FOREIGN KEY (ID_RESTAURANT) REFERENCES RESTAURANTS(ID_RESTAURANT)
);

-- --------------------------------------------------------
-- 7. TABLA ASIGNACION_EMPLEADO (Gestión de turnos/mesas)
-- --------------------------------------------------------

CREATE TABLE TABLE_ASSIGNMENT (
    ID_ASSIGMENT INT AUTO_INCREMENT PRIMARY KEY,
    ID_TABLE INT NOT NULL,
    ID_EMPLOYEE INT NOT NULL,
    START_TIME DATETIME NOT NULL,
    END_TIME DATETIME,
    FOREIGN KEY (ID_TABLE) REFERENCES TABLES(ID_TABLE),
    FOREIGN KEY (ID_EMPLOYEE) REFERENCES EMPLOYEES(ID_USER)
);