// 代码生成时间: 2025-09-09 13:31:09
// DatabaseConnectionPoolManager.java
// 此类负责管理数据库连接池
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class DatabaseConnectionPoolManager {

    // 注入Quarkus提供的DataSource
    private DataSource dataSource;

    public DatabaseConnectionPoolManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 在应用启动时初始化数据库连接池
    public void onStart(@Observes StartupEvent event) {
        try (Connection connection = dataSource.getConnection()) {
            // 测试连接池是否正常工作
            System.out.println("连接池已启动，当前连接: " + connection);
        } catch (SQLException e) {
            // 错误处理
            System.err.println("数据库连接池启动失败: " + e.getMessage());
        }
    }

    // 获取数据库连接
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            // 错误处理
            System.err.println("获取数据库连接失败: " + e.getMessage());
            return null;
        }
    }

    // 关闭数据库连接
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // 错误处理
                System.err.println("关闭数据库连接失败: " + e.getMessage());
            }
        }
    }

}
