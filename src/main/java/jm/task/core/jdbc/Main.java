package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Boba", "Abob", (byte) 21);
        userService.saveUser("Ivan", "Ivanov", (byte) 22);
        userService.saveUser("Vovan", "Vovanov", (byte) 23);
        userService.saveUser("Frodo", "Baggins", (byte) 24);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
