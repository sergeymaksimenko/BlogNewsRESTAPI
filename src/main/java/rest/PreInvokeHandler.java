package rest;


import dao.AuthenticationDAO;
import entities.Role;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import responce.ResponseCreator;
import rest.exceptions.Error;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class PreInvokeHandler implements RequestHandler {


    private boolean validate(String token, String method) {
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\jv\\BlogNewsRESTAPI\\src\\main\\resources\\META-INF\\spring\\dao.xml");
        AuthenticationDAO authenticationDAO = (AuthenticationDAO) context.getBean("authenticationDAO");

        Integer roleId = authenticationDAO.getRoleFromToken(token);
        Role choosenRole = null;
        for (Role role : Role.values()) {
            if (roleId == role.getId())
                choosenRole = role;
        }
        System.out.println(choosenRole);
        if (choosenRole == Role.ADMIN && method.toString().equals("DELETE"))
            return true;

        if ((method.toString().equals("POST") || method.toString().equals("PUT") && (choosenRole == Role.ADMIN || choosenRole == Role.USER)))
            return true;

        if (method.toString().equals("GET"))
            return true;
        return false;
    }

    public Response handleRequest(Message message, ClassResourceInfo arg1) {
        Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message
                .get(Message.PROTOCOL_HEADERS));

        if (headers.get("token") != null && validate(headers.get("token").get(0), (String) message.get(Message.HTTP_REQUEST_METHOD))) {
            System.out.println(headers.get("token"));
            System.out.println(message.get(Message.HTTP_REQUEST_METHOD));
            return null;
        } else {
            return ResponseCreator.error(401, Error.NOT_AUTHORIZED.getCode(), headers.get("version").get(0));
        }

    }
}
