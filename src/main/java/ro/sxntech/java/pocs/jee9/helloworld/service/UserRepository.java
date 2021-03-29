package ro.sxntech.java.pocs.jee9.helloworld.service;

import ro.sxntech.java.pocs.jee9.helloworld.service.entity.User;

import java.util.List;

public interface UserRepository {

    User create(User user);

    List<User> findAll();

    User findBy(String email);

    User update(User user);

    boolean delete(long id);
}
