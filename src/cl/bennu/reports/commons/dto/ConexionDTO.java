package cl.bennu.reports.commons.dto;

import cl.bennu.reports.commons.dto.base.BaseDTO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 14-11-13
 * Time: 11:22 AM
 */
public class ConexionDTO extends BaseDTO {

    private String name;
    private String url;
    private ControllerDTO controllerDTO;
    private String user;
    private String pass;

    private Boolean check;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ControllerDTO getControllerDTO() {
        return controllerDTO;
    }

    public void setControllerDTO(ControllerDTO controllerDTO) {
        this.controllerDTO = controllerDTO;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}