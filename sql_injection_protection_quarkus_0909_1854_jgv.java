// 代码生成时间: 2025-09-09 18:54:37
package com.example.demo;

import io.quarkus.runtime.Quarkus;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/api")
public class SqlInjectionProtectionResource {

    @PersistenceContext
    EntityManager em;

    /**
     * Prevents SQL injection by using parameterized queries.
     *
     * @param userId The user ID to search for.
     * @return A list of users.
     */
    @GET
    @Path("/getUsers")
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<User> getUsersByUserId(@QueryParam("userId") String userId) {
        try {
            // Using parameterized queries to prevent SQL injection.
            String query = "SELECT * FROM users WHERE id = :userId";
            return em.createQuery(query, User.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            // Error handling
            throw new RuntimeException("Error occurred while fetching users.", e);
        }
    }

    // Assume User is an entity class representing the users table.
    // It should have annotations for JPA and fields to match the database columns.
}

// User entity class (simplified for brevity)
class User {
    private Long id;
    private String name;
    private String email;
    // Getters and setters...
}