package dao;

import entities.Comment;

import java.util.List;

/**
 * Created by asu on 10.02.2017.
 */
public interface ICommentDAO {
    Comment createComment(Comment comment);
    List<Comment> getComments(Integer id);
    Comment updateComment(Integer id,Comment comment);
    boolean removeComment(Integer id);
}
