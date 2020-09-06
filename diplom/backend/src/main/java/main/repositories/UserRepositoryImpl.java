package main.repositories;

import main.model.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Users> getAllUsers() {
        return entityManager.createQuery("from Users", Users.class).getResultList();
    }

    @Override
    public Users getUserById(int id) {
        return entityManager.createQuery("from Users where id='" + id +"'", Users.class).getSingleResult();
    }
}
