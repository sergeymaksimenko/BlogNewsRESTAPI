package entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "comment")
public class Comment {
    private Integer id;
    private Integer idPost;
    private Integer userCreatorId;
    private String body;
    private Date dateCreated;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Integer getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(Integer userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
