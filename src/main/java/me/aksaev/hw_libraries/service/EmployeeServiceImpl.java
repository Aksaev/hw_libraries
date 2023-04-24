package me.aksaev.hw_libraries.service;

import me.aksaev.hw_libraries.exception.EmployeeAlreadyAddedException;
import me.aksaev.hw_libraries.exception.EmployeeNotFoundException;
import me.aksaev.hw_libraries.exception.EmployeeStorageIsFullException;
import me.aksaev.hw_libraries.exception.InvalidInputException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import me.aksaev.hw_libraries.model.Employee;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final int LIMIT = 10;
    private final List<Employee> employees = new ArrayList<>();

    @Override
    public Employee add(String name, String surname, int department, int salary) {
        validateInput(name, surname);

        Employee employee = new Employee(name, surname, department, salary);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    @Override
    public Employee remove(String name, String surname, int department, int salary) {
        validateInput(name, surname);

        Employee employee = new Employee(name, surname, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee);
        return employee;
    }

    @Override
    public Employee find(String name, String surname, int department, int salary) {
        validateInput(name, surname);

        Employee employee = new Employee(name, surname, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }


    @Override
    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

    private void validateInput(String name, String surname) {
        if (!(isAlpha(name) && isAlpha(surname))) {
            throw new InvalidInputException();
        }
    }


}
