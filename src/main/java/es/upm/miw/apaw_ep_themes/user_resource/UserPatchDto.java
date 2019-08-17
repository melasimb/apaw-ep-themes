package es.upm.miw.apaw_ep_themes.user_resource;

import es.upm.miw.apaw_ep_themes.exceptions.BadRequestException;

public class UserPatchDto {

    private String path;

    private String newValue;

    public UserPatchDto() {
        // empty for framework
    }

    public UserPatchDto(String path, String newValue) {
        this.path = path;
        this.newValue = newValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void validate() {
        if (this.path == null || this.path.isEmpty()) {
            throw new BadRequestException("Incomplete UserPatchDto");
        }
    }

    @Override
    public String toString() {
        return "UserPatchDto{" +
                "path='" + path + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }

}
