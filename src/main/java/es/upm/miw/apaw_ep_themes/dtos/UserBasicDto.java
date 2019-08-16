package es.upm.miw.apaw_ep_themes.dtos;

import es.upm.miw.apaw_ep_themes.documents.User;
import es.upm.miw.apaw_ep_themes.exceptions.BadRequestException;

public class UserBasicDto {

    private String id;

    private String nick;

    public UserBasicDto() {
        // Empty for framework
    }

    public UserBasicDto(User user) {
        this.id = user.getId();
        this.nick = user.getNick();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "UserBasicDto{" +
                "id='" + id + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
