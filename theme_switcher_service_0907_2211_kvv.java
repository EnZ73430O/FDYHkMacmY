// 代码生成时间: 2025-09-07 22:11:29
package com.example.theme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/theme")
public class ThemeSwitcherService {

    @Inject
    private ThemePersistenceService themePersistenceService; // Service to persist theme changes

    /**
     * GET method to retrieve the current theme.
     *
     * @return Response with the current theme.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentTheme() {
        try {
            Theme currentTheme = themePersistenceService.getCurrentTheme();
            return Response.ok(currentTheme).build();
        } catch (Exception e) {
            // Log and handle the error appropriately
            return Response.serverError().entity("Error retrieving theme: " + e.getMessage()).build();
        }
    }

    /**
     * POST method to switch the theme.
     *
     * @param themeName The name of the theme to switch to.
     * @return Response indicating success or failure.
     */
    @POST
    public Response switchTheme(@QueryParam("theme") String themeName) {
        try {
            themePersistenceService.saveTheme(themeName);
            return Response.ok("Theme switched to: " + themeName).build();
        } catch (Exception e) {
            // Log and handle the error appropriately
            return Response.serverError().entity("Error switching theme: " + e.getMessage()).build();
        }
    }
}

/**
 * Theme.java
 *
 * A simple POJO representing a theme.
 */
public class Theme {
    private String name;

    public Theme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * ThemePersistenceService.java
 *
 * Service class to handle persistence of theme changes.
 */
public interface ThemePersistenceService {
    Theme getCurrentTheme();
    void saveTheme(String themeName);
}

/**
 * InMemoryThemePersistenceService.java
 *
 * Implementation of ThemePersistenceService that saves the theme in-memory.
 */
public class InMemoryThemePersistenceService implements ThemePersistenceService {
    private String currentTheme;

    @Override
    public Theme getCurrentTheme() {
        return new Theme(currentTheme);
    }

    @Override
    public void saveTheme(String themeName) {
        currentTheme = themeName;
    }
}
