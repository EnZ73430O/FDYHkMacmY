// 代码生成时间: 2025-09-19 14:21:24
package com.example.accesscontrol;

import io.quarkus.security.Authenticated;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/access")
public class AccessControlService {

    // 模拟用户服务，实际项目中可能使用数据库或其他服务来验证用户
    // 注入用户服务
    @Inject
    UserServiceImpl userService;

    /**
     * 公开端点，不需要任何权限即可访问
     * @return 响应消息
     */
    @GET
    @Path("/public")
    @Produces(MediaType.TEXT_PLAIN)
    public Response publicEndpoint() {
        return Response.ok("Public endpoint accessible without authentication.").build();
    }

    /**
     * 受保护的端点，只有具有USER角色的用户才能访问
     * @return 响应消息
     */
    @GET
    @Path("/protected")
    @Authenticated
    @RolesAllowed("USER")
    @Produces(MediaType.TEXT_PLAIN)
    public Response protectedEndpoint() {
        return Response.ok("Protected endpoint accessible with USER role.").build();
    }

    /**
     * 受保护的端点，只有具有ADMIN角色的用户才能访问
     * @return 响应消息
     */
    @GET
    @Path("/admin")
    @Authenticated
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    public Response adminEndpoint() {
        return Response.ok("Admin endpoint accessible with ADMIN role.").build();
    }
}

// 用户服务接口
public interface UserService {
    boolean hasRole(String username, String role);
}

// 用户服务实现
public class UserServiceImpl implements UserService {
    // 这里使用一个简单的HashMap来模拟用户角色存储
    private Map<String, Set<String>> userRoles = new HashMap<>();

    public UserServiceImpl() {
        // 初始化一些用户和角色
        userRoles.put("user1", new HashSet<>(Arrays.asList("USER")));
        userRoles.put("admin1", new HashSet<>(Arrays.asList("ADMIN", "USER")));
    }

    @Override
    public boolean hasRole(String username, String role) {
        Set<String> roles = userRoles.get(username);
        if (roles != null) {
            return roles.contains(role);
        }
        return false;
    }
}