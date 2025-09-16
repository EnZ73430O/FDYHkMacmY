// 代码生成时间: 2025-09-17 06:35:50
package com.example.rest;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/formatter")
@RegisterForReflection
public class ApiResponseFormatter {

    // POST endpoint to format API responses
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response formatApiResponse(Map<String, Object> rawData) {
        try {
            // Validate if the rawData is not null or empty
            if (rawData == null || rawData.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        "{"error": "No data provided for formatting"}")
                        .type(MediaType.APPLICATION_JSON).build();
            }

            // Format the API response
            String formattedResponse = format(rawData);

            // Return the formatted response as JSON
            return Response.ok(formattedResponse).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            // Handle any unexpected errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    "{"error": "Internal server error"}")
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    // Helper method to format the API response
    private String format(Map<String, Object> data) {
        StringBuilder builder = new StringBuilder();
        builder.append("{"result": ");

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            builder.append(""").append(entry.getKey()).append("": ");
            Object value = entry.getValue();
            if (value instanceof String) {
                builder.append(""").append(value).append(""");
            } else {
                builder.append(value);
            }
            builder.append(", ");
        }

        // Remove the last comma and space
        if (builder.charAt(builder.length() - 2) == ',' && builder.charAt(builder.length() - 1) == ' ') {
            builder.delete(builder.length() - 2, builder.length());
        }

        builder.append("}
");
        return builder.toString();
    }
}
