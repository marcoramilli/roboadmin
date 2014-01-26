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
@Table(name = "msnpproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Msnpproperties.findAll", query = "SELECT m FROM Msnpproperties m"), @NamedQuery(name = "Msnpproperties.findByProperty", query = "SELECT m FROM Msnpproperties m WHERE m.msnppropertiesPK.property = :property"), @NamedQuery(name = "Msnpproperties.findByValue", query = "SELECT m FROM Msnpproperties m WHERE m.msnppropertiesPK.value = :value")})
public class Msnpproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MsnppropertiesPK msnppropertiesPK;

    public Msnpproperties() {
    }

    public Msnpproperties(MsnppropertiesPK msnppropertiesPK) {
        this.msnppropertiesPK = msnppropertiesPK;
    }

    public Msnpproperties(String property, String value) {
        this.msnppropertiesPK = new MsnppropertiesPK(property, value);
    }

    public MsnppropertiesPK getMsnppropertiesPK() {
        return msnppropertiesPK;
    }

    public void setMsnppropertiesPK(MsnppropertiesPK msnppropertiesPK) {
        this.msnppropertiesPK = msnppropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msnppropertiesPK != null ? msnppropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Msnpproperties)) {
            return false;
        }
        Msnpproperties other = (Msnpproperties) object;
        if ((this.msnppropertiesPK == null && other.msnppropertiesPK != null) || (this.msnppropertiesPK != null && !this.msnppropertiesPK.equals(other.msnppropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Msnpproperties[msnppropertiesPK=" + msnppropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
