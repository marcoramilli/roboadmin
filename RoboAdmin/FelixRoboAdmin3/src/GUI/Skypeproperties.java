/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Luca
 */
@Entity
@Table(name = "skypeproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Skypeproperties.findAll", query = "SELECT s FROM Skypeproperties s"), @NamedQuery(name = "Skypeproperties.findByProperty", query = "SELECT s FROM Skypeproperties s WHERE s.skypepropertiesPK.property = :property"), @NamedQuery(name = "Skypeproperties.findByValue", query = "SELECT s FROM Skypeproperties s WHERE s.skypepropertiesPK.value = :value")})
public class Skypeproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SkypepropertiesPK skypepropertiesPK;

    public Skypeproperties() {
    }

    public Skypeproperties(SkypepropertiesPK skypepropertiesPK) {
        this.skypepropertiesPK = skypepropertiesPK;
    }

    public Skypeproperties(String property, String value) {
        this.skypepropertiesPK = new SkypepropertiesPK(property, value);
    }

    public SkypepropertiesPK getSkypepropertiesPK() {
        return skypepropertiesPK;
    }

    public void setSkypepropertiesPK(SkypepropertiesPK skypepropertiesPK) {
        this.skypepropertiesPK = skypepropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skypepropertiesPK != null ? skypepropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Skypeproperties)) {
            return false;
        }
        Skypeproperties other = (Skypeproperties) object;
        if ((this.skypepropertiesPK == null && other.skypepropertiesPK != null) || (this.skypepropertiesPK != null && !this.skypepropertiesPK.equals(other.skypepropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Skypeproperties[skypepropertiesPK=" + skypepropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
