package main.repositories;

import main.model.Users;

import java.util.List;

public interface UserRepository {
    List<Users> getAllUsers();
    Users getUserById(int id);
}
