// 代码生成时间: 2025-08-24 21:17:49
package com.example.hashvaluecalculator;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Path("/hash")
public class HashValueCalculator {

    @Inject
    HashService hashService;

    @GET
    @Path("/calculate")
    public Response calculateHash(@QueryParam("input") String input) {
        if (input == null || input.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Input must not be empty").build();
        }
        try {
            String hashValue = hashService.calculateHash(input);
            return Response.ok(hashValue).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error calculating hash: " + e.getMessage()).build();
        }
    }
}

class HashService {
    public String calculateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}