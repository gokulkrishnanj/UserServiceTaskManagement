package com.example.userServiceTaskManagement.Exceptions;

public class TasksNotFoundException extends RuntimeException {
    public TasksNotFoundException(String messgae) {
        super(messgae);
    }
}
