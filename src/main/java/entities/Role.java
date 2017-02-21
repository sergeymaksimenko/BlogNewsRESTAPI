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

    Role getRole(Integer id) {
        Role choosenRole = null;
        for (Role role : values()) {
            if (id == role.getId())
                choosenRole = role;
        }
        return choosenRole;
    }
}
