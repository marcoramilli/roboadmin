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
@Table(name = "oscarproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Oscarproperties.findAll", query = "SELECT o FROM Oscarproperties o"), @NamedQuery(name = "Oscarproperties.findByProperty", query = "SELECT o FROM Oscarproperties o WHERE o.oscarpropertiesPK.property = :property"), @NamedQuery(name = "Oscarproperties.findByValue", query = "SELECT o FROM Oscarproperties o WHERE o.oscarpropertiesPK.value = :value")})
public class Oscarproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OscarpropertiesPK oscarpropertiesPK;

    public Oscarproperties() {
    }

    public Oscarproperties(OscarpropertiesPK oscarpropertiesPK) {
        this.oscarpropertiesPK = oscarpropertiesPK;
    }

    public Oscarproperties(String property, String value) {
        this.oscarpropertiesPK = new OscarpropertiesPK(property, value);
    }

    public OscarpropertiesPK getOscarpropertiesPK() {
        return oscarpropertiesPK;
    }

    public void setOscarpropertiesPK(OscarpropertiesPK oscarpropertiesPK) {
        this.oscarpropertiesPK = oscarpropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oscarpropertiesPK != null ? oscarpropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oscarproperties)) {
            return false;
        }
        Oscarproperties other = (Oscarproperties) object;
        if ((this.oscarpropertiesPK == null && other.oscarpropertiesPK != null) || (this.oscarpropertiesPK != null && !this.oscarpropertiesPK.equals(other.oscarpropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Oscarproperties[oscarpropertiesPK=" + oscarpropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
