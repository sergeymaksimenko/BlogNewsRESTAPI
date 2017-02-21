package rest;

import dao.AuthenticationDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


public class Authentication {

    private AuthenticationDAO authenticationDAO;

    public AuthenticationDAO getAuthenticationDAO() {
        return authenticationDAO;
    }

    public void setAuthenticationDAO(AuthenticationDAO authenticationDAO) {
        this.authenticationDAO = authenticationDAO;
    }

    @Context
    private HttpHeaders requestHeaders;

    @POST
    @Produces("application/json")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {
            System.out.println(username + " " + password);
            Integer userId = authenticationDAO.authenticate(username, password);
            String token = issueToken(username);
            authenticationDAO.setToken(userId, token);

            return Response.ok(token).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String issueToken(String username) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        return token;
    }

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }
}
