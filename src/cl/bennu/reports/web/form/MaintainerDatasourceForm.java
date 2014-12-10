package cl.bennu.reports.web.form;

import java.util.List;


public class MaintainerDatasourceForm extends BaseForm {

    private Long id;
    private String name;
    private String user;
    private String pass;
    private String url;
    private Long controllerId;
    private List controllerList;

    private List conexionList;

    private Long maintainerDatasourceId;


    public List getConexionList() {
        return conexionList;
    }

    public void setConexionList(List conexionList) {
        this.conexionList = conexionList;
    }

    public Long getMaintainerDatasourceId() {
        return maintainerDatasourceId;
    }

    public void setMaintainerDatasourceId(Long maintainerDatasourceId) {
        this.maintainerDatasourceId = maintainerDatasourceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getControllerId() {
        return controllerId;
    }

    public void setControllerId(Long controllerId) {
        this.controllerId = controllerId;
    }

    public List getControllerList() {
        return controllerList;
    }

    public void setControllerList(List controllerList) {
        this.controllerList = controllerList;
    }
}
