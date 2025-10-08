// 代码生成时间: 2025-10-08 17:37:48
package com.example;

import io.quarkus.hibernate.orm.PersistenceUnit;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class SqlInjectionPreventionService {

    // Injecting an EntityManager from the default persistence unit
    @Inject
    @PersistenceUnit
    private EntityManager entityManager;

    /**<ol>
     * Retrieves a list of users from the database using a parameterized query to prevent SQL injection.
     * 
     * @param username The username to search for.
     * @return A list of users that match the provided username.
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<User> findUsersByUsername(String username) {
        // Using a parameterized query to prevent SQL injection
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE u.username = :username",
            User.class
        )
        .setParameter("username", username)
        .getResultList();
    }

    /**<ol>
     * Adds a user to the database using a parameterized query to prevent SQL injection.
     * 
     * @param user The user to add.
     * @return The added user with generated ID.
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }
}

/**
 * A simple user entity.
 */
@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    // Standard getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

/**
 * A REST endpoint that uses the SqlInjectionPreventionService.
 */
@Path("/users")
public class UserResource {
    @Inject
    SqlInjectionPreventionService service;

    @GET
    @Path("/search/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> searchUsers(@PathParam("username") String username) {
        try {
            return service.findUsersByUsername(username);
        } catch (Exception e) {
            // Proper error handling should be implemented
            throw new WebApplicationException("An error occurred while searching users", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        try {
            User addedUser = service.addUser(user);
            return Response.ok(addedUser).status(Status.CREATED).build();
        } catch (Exception e) {
            // Proper error handling should be implemented
            throw new WebApplicationException("An error occurred while adding a user", Status.INTERNAL_SERVER_ERROR);
        }
    }
}