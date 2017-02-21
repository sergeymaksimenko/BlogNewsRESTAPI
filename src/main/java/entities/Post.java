package entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "post")
public class Post {
    private Integer id;
    private String body;
    //private List<Comment> comments;
    private Integer userCreatorId;
    private Date dateCreated;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(Integer userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", userCreatorId=" + userCreatorId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
