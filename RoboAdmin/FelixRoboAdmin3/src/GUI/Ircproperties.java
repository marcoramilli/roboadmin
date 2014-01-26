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
@Table(name = "ircproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Ircproperties.findAll", query = "SELECT i FROM Ircproperties i"), @NamedQuery(name = "Ircproperties.findByProperty", query = "SELECT i FROM Ircproperties i WHERE i.ircpropertiesPK.property = :property"), @NamedQuery(name = "Ircproperties.findByValue", query = "SELECT i FROM Ircproperties i WHERE i.ircpropertiesPK.value = :value")})
public class Ircproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IrcpropertiesPK ircpropertiesPK;

    public Ircproperties() {
    }

    public Ircproperties(IrcpropertiesPK ircpropertiesPK) {
        this.ircpropertiesPK = ircpropertiesPK;
    }

    public Ircproperties(String property, String value) {
        this.ircpropertiesPK = new IrcpropertiesPK(property, value);
    }

    public IrcpropertiesPK getIrcpropertiesPK() {
        return ircpropertiesPK;
    }

    public void setIrcpropertiesPK(IrcpropertiesPK ircpropertiesPK) {
        this.ircpropertiesPK = ircpropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ircpropertiesPK != null ? ircpropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ircproperties)) {
            return false;
        }
        Ircproperties other = (Ircproperties) object;
        if ((this.ircpropertiesPK == null && other.ircpropertiesPK != null) || (this.ircpropertiesPK != null && !this.ircpropertiesPK.equals(other.ircpropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Ircproperties[ircpropertiesPK=" + ircpropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
