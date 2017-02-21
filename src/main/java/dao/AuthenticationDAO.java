package dao;

import entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asu on 14.02.2017.
 */

public class AuthenticationDAO {

    private SimpleJdbcInsert insertToken;
    private JdbcTemplate templAuth;

    public void setDataSource(DataSource dataSource) {
        this.templAuth = new JdbcTemplate(dataSource);
        this.insertToken = new SimpleJdbcInsert(dataSource)
                .withTableName("token");
    }

    public SimpleJdbcInsert getInsertToken() {
        return insertToken;
    }

    public JdbcTemplate getTemplAuth() {
        return templAuth;
    }

    public int authenticate(String username, String password) throws Exception {
        User user = null;
        if ((templAuth
                .queryForInt("Select count(1) FROM users WHERE user = '" + username
                        + "'")) > 0) {
            user = (User) templAuth.queryForObject("SELECT * FROM users WHERE user = '" + username + "'",
                    new RowMapper<User>() {
                        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                            User user = new User();
                            user.setId(rs.getInt("id"));
                            user.setIdrole(rs.getInt("idrole"));
                            user.setUser(rs.getString("user"));
                            user.setPassword(rs.getString("password"));
                            return user;
                        }
                    });
            if (!user.getPassword().equals(password))
                throw new Exception("username or password incorrect");

        }
        return user.getId();
    }

    public void setToken(Integer userId, String token) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (userId != null)
            parameters.put("userId", userId);
        if (token != null)
            parameters.put("token", token);
        insertToken.execute(parameters);
    }

    public Integer getRoleFromToken(String token) {

        System.out.println(templAuth.getDataSource().toString());

        return templAuth.queryForInt("SELECT idrole FROM users,token WHERE (token.token = '" + token + "' && token.userId = users.id)");
    }


}
