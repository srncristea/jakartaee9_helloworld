package ro.sxntech.java.pocs.jee9.helloworld.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.sxntech.java.pocs.jee9.helloworld.service.JakartaEE9_Exception;
import ro.sxntech.java.pocs.jee9.helloworld.service.entity.User;
import ro.sxntech.java.pocs.jee9.helloworld.service.UserRepository;

import java.util.List;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserDao implements UserRepository {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public User create(User user) {
        try {
            entityManager.persist(user);
            log.debug("persisted user: " + user);
        } catch (Exception x) {
            log.error("Error when persist user: " + user, x);
            throw new JakartaEE9_Exception(x);
        }

        return user;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        try{
            final var sqlQuery = "SELECT u FROM User u";
            final var users = entityManager.createQuery(sqlQuery, User.class).getResultList();
            log.debug("Number of users found: {}", users.size());

            return users;
        }catch (Exception x){
            log.error("Error retrieve all users: ", x);
            throw new JakartaEE9_Exception(x);
        }
    }

    @Override
    public User findBy(String email) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return true;
    }
}
