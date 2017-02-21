package dao;

import entities.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDAO implements ICommentDAO {

    private SimpleJdbcInsert insertComment;
    private JdbcTemplate templComment;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public void setDataSource(DataSource dataSource) {
        this.templComment = new JdbcTemplate(dataSource);
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("comment");
    }

    @Override
    public Comment createComment(Comment comment) {
        if (comment != null) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            if (comment.getId() != null)
                parameters.put("id", comment.getId());
            if (comment.getIdPost() != null)
                parameters.put("idPost", comment.getIdPost());
            if (comment.getBody() != null)
                parameters.put("body", comment.getBody());
            if (comment.getUserCreatorId() != null)
                parameters.put("userIdCreator", comment.getUserCreatorId());
            if (comment.getDateCreated() != null)
                parameters.put("dateCreated", comment.getDateCreated());
            else parameters.put("dateCreated", format.format(new Date()));
            insertComment.execute(parameters);

            return comment;
        } else {
            return null;
        }
    }

    @Override
    public List<Comment> getComments(Integer id) {
        System.out.println("id=" + id);
        if ((templComment
                .queryForInt("Select count(1) FROM comment WHERE idPost = '" + id
                        + "'")) > 0) {

            List<Comment> comments = templComment.query(
                    "SELECT * FROM comment WHERE idPost = '" + id + "'",
                    new RowMapper<Comment>() {
                        public Comment mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            Comment comment = new Comment();
                            System.out.println(rowNum);
                            comment.setId(rs.getInt("id"));
                            comment.setBody(rs.getString("body"));
                            comment.setDateCreated(rs.getDate("dateCreated"));
                            comment.setUserCreatorId(rs.getInt("userIdCreator"));
                            comment.setIdPost(rs.getInt("idPost"));
                            return comment;
                        }
                    });
            return comments;
        } else {
            return null;
        }
    }

    @Override
    public Comment updateComment(Integer id, Comment comment) {
        if (comment != null && comment.getId() != null) {
            Comment oldComment = getCommentById(id);
            String sqlUpdate = String
                    .format("UPDATE comment SET body = %s, userIdCreator = %s, dateCreated = %s,idPost = %s WHERE id = %s",
                            "'" + comment.getBody() + "'",
                            "'" + comment.getUserCreatorId() + "'", ((comment.getDateCreated() != null) ? "'" + comment.getDateCreated() + "'" : "now()"), "'" + comment.getIdPost() + "'",
                            "'" + id + "'");
            System.out.println(sqlUpdate);
            templComment.update(sqlUpdate);
            return oldComment;
        } else {
            return null;
        }
    }

    @Override
    public boolean removeComment(Integer id) {
        return false;
    }

    private Comment getCommentById(Integer id) {
        if ((templComment
                .queryForInt("Select count(1) FROM comment WHERE id = '" + id
                        + "'")) > 0) {
            Comment comment = (Comment) templComment.queryForObject(
                    "SELECT * FROM comment WHERE id = '" + id + "'",
                    new RowMapper<Comment>() {
                        public Comment mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            Comment comment = new Comment();
                            comment.setId(rs.getInt("id"));
                            comment.setIdPost(rs.getInt("idPost"));
                            comment.setUserCreatorId(rs.getInt("userIdCreator"));
                            comment.setBody(rs.getString("body"));
                            comment.setDateCreated(rs.getDate("dateCreated"));
                            return comment;
                        }
                    });
            return comment;
        } else {
            return null;
        }
    }

}