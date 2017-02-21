package dao;

import entities.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostDAO implements IPostDAO {

    Map<Integer, Post> profsMap = new HashMap<Integer, Post>();

    DataSource datasource;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private SimpleJdbcInsert insertPost;
    private JdbcTemplate templPost;

    public void setDataSource(DataSource dataSource) {
        this.templPost = new JdbcTemplate(dataSource);
        this.insertPost = new SimpleJdbcInsert(dataSource)
                .withTableName("post");
    }



    @Override
    public Post createPost(Post post) {
        if (post != null) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            if (post.getId() != null)
                parameters.put("id", post.getId());
            if (post.getBody() != null)
                parameters.put("body", post.getBody());
            if (post.getUserCreatorId() != null)
                parameters.put("userCreatorId", post.getUserCreatorId());
            if (post.getDateCreated() != null)
                parameters.put("dateCreated", post.getDateCreated());
            else parameters.put("dateCreated", format.format(new Date()));
            insertPost.execute(parameters);

            return post;
        } else {
            return null;
        }
    }


    public Post getPost(Integer id) {
        System.out.println("id=" + id);
        if ((templPost
                .queryForInt("Select count(1) FROM post WHERE id = '" + id
                        + "'")) > 0) {
            Post post = (Post) templPost.queryForObject(
                    "SELECT * FROM post WHERE id = '" + id + "'",
                    new RowMapper<Post>() {
                        public Post mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            Post post = new Post();
                            post.setId(rs.getInt("id"));
                            post.setUserCreatorId(rs.getInt("userCreatorId"));
                            post.setBody(rs.getString("body"));
                            post.setDateCreated(rs.getDate("dateCreated"));
                            return post;
                        }
                    });

            return post;
        } else {
            return null;
        }
    }

    @Override
    public Post updatePost(Post post) {
        if (post != null && post.getId() != null) {
            Post oldPost = getPost(post.getId());
            System.out.println(oldPost);
            String sqlUpdate = String
                    .format("UPDATE post SET body = %s, userCreatorId = %s, dateCreated = %s WHERE id = %s",
                            "'" + post.getBody() + "'",
                            "'" + post.getUserCreatorId() + "'", ((post.getDateCreated() != null) ? "'" + post.getDateCreated() + "'" : "now()"),
                            "'" + post.getId() + "'");
            System.out.println(sqlUpdate);
            templPost.update(sqlUpdate);
            return oldPost;
        } else {
            return null;
        }
    }

    @Override
    public boolean removePost(Integer id) {
            if (templPost
                    .update("DELETE FROM post WHERE id = '" + id + "'") > 0) {
                return true;
            } else {
                return false;
            }
        }
}
