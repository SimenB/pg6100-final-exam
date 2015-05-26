package no.nith.pg6100;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

@Model
public class Controller {
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml";
    }
}
