package cl.bennu.reports.web.form;

import org.apache.struts.action.ActionForm;

/**
 * Created by _Camilo on 20-06-2014.
 */
public class BaseForm extends ActionForm {

    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
