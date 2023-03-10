package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void createUser(User user) {
        entityManager.persist(user);
    }

    public User findByUsername(String username) {
        return readListUsers().stream().filter(user -> Objects.equals(user.getUsername(), username)).findAny().orElse (null);
    }

    public List<User> readListUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    public User show(long id) {
        return readListUsers().stream().filter(user -> user.getId() == id).findAny().orElse (null);
    }

    public void update(User updatedUser) {
        User userToBeUpdated = show(updatedUser.getId());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setPassword(updatedUser.getPassword());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        entityManager.merge(userToBeUpdated);
    }

    public void delete(long id) {
        entityManager.remove(show(id));
    }
}
