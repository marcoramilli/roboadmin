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
@Table(name = "roboadminproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Roboadminproperties.findAll", query = "SELECT r FROM Roboadminproperties r"), @NamedQuery(name = "Roboadminproperties.findByProperty", query = "SELECT r FROM Roboadminproperties r WHERE r.roboadminpropertiesPK.property = :property"), @NamedQuery(name = "Roboadminproperties.findByValue", query = "SELECT r FROM Roboadminproperties r WHERE r.roboadminpropertiesPK.value = :value")})
public class Roboadminproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RoboadminpropertiesPK roboadminpropertiesPK;

    public Roboadminproperties() {
    }

    public Roboadminproperties(RoboadminpropertiesPK roboadminpropertiesPK) {
        this.roboadminpropertiesPK = roboadminpropertiesPK;
    }

    public Roboadminproperties(String property, String value) {
        this.roboadminpropertiesPK = new RoboadminpropertiesPK(property, value);
    }

    public RoboadminpropertiesPK getRoboadminpropertiesPK() {
        return roboadminpropertiesPK;
    }

    public void setRoboadminpropertiesPK(RoboadminpropertiesPK roboadminpropertiesPK) {
        this.roboadminpropertiesPK = roboadminpropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roboadminpropertiesPK != null ? roboadminpropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roboadminproperties)) {
            return false;
        }
        Roboadminproperties other = (Roboadminproperties) object;
        if ((this.roboadminpropertiesPK == null && other.roboadminpropertiesPK != null) || (this.roboadminpropertiesPK != null && !this.roboadminpropertiesPK.equals(other.roboadminpropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Roboadminproperties[roboadminpropertiesPK=" + roboadminpropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
