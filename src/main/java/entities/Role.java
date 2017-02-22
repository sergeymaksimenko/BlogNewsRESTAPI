package entities;

/**
 * Created by asu on 19.02.2017.
 */
public enum Role {
    ADMIN(1),
    USER(2);

    Integer id;

    Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
