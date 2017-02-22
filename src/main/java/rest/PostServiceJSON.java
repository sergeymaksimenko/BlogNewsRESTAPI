package rest;

import dao.ICommentDAO;
import dao.IPostDAO;
import entities.Post;
import responce.ResponseCreator;
import rest.exceptions.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PostServiceJSON {

    // link to our dao object
    private IPostDAO postDAO;
    private ICommentDAO commentDAO;

    // for postDAO bean property injection
    public IPostDAO getPostDAO() {
        return postDAO;
    }

    public ICommentDAO getCommentDAO() {
        return commentDAO;
    }

    public void setPostDAO(IPostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public void setCommentDAO(ICommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    // for retrieving request headers from context
    // an injectable interface that provides access to HTTP header information.
    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }

    // get by id service
    @GET
    @Path(value = "/{id}")
    public Response getPost(@PathParam("id") Integer id) {
        Post post = postDAO.getPost(id);
        if (post != null) {
            return ResponseCreator.success(getHeaderVersion(), post);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(),
                    getHeaderVersion());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(Post post) {
        Post crePost = postDAO.createPost(post);
        if (crePost != null) {
            return ResponseCreator.success(getHeaderVersion(), crePost);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),
                    getHeaderVersion());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePost(Post post) {
        Post updPost = postDAO.updatePost(post);
        if (updPost != null) {
            return ResponseCreator.success(getHeaderVersion(), updPost);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),
                    getHeaderVersion());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removePost(@PathParam("id") Integer id) {
        if (postDAO.removePost(id)) {
            return ResponseCreator.success(getHeaderVersion(), "removed");
        } else {
            return ResponseCreator.success(getHeaderVersion(), "no such id");
        }
    }
}