package rest;

import dao.ICommentDAO;
import entities.Comment;
import responce.ResponseCreator;
import rest.exceptions.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


public class CommentServiceJSON {


    private ICommentDAO commentDAO;


    public ICommentDAO getCommentDAO() {
        return commentDAO;
    }

    public void setCommentDAO(ICommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createComment(Comment comment) {
        System.out.println("POST-Comment");
        Comment creComment = commentDAO.createComment(comment);
        if (creComment != null) {
            return ResponseCreator.success(getHeaderVersion(), creComment);
        } else {
            return ResponseCreator.error(500, rest.exceptions.Error.SERVER_ERROR.getCode(),
                    getHeaderVersion());
        }
    }

    @GET
    @Path(value = "/{id}")
    public Response getComments(@PathParam("id") Integer id) {
        System.out.println("comment " + id);
        List<Comment> comments = commentDAO.getComments(id);
        if (comments != null) {
            System.out.println(comments);
            GenericEntity<List<Comment>> list = new GenericEntity<List<Comment>>(comments) {
            };
            return Response.ok(list).build();
        } else {
            return ResponseCreator.error(404, rest.exceptions.Error.NOT_FOUND.getCode(),
                    getHeaderVersion());
        }
    }

    @PUT
    @Path(value = "/{idc}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("idc") Integer idc, Comment comment) {
        Comment updComment = commentDAO.updateComment(idc, comment);
        if (updComment != null) {
            return ResponseCreator.success(getHeaderVersion(), updComment);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),
                    getHeaderVersion());
        }
    }


}
