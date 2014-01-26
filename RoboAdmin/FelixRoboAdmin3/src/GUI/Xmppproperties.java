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
@Table(name = "xmppproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Xmppproperties.findAll", query = "SELECT x FROM Xmppproperties x"), @NamedQuery(name = "Xmppproperties.findByProperty", query = "SELECT x FROM Xmppproperties x WHERE x.xmpppropertiesPK.property = :property"), @NamedQuery(name = "Xmppproperties.findByValue", query = "SELECT x FROM Xmppproperties x WHERE x.xmpppropertiesPK.value = :value")})
public class Xmppproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected XmpppropertiesPK xmpppropertiesPK;

    public Xmppproperties() {
    }

    public Xmppproperties(XmpppropertiesPK xmpppropertiesPK) {
        this.xmpppropertiesPK = xmpppropertiesPK;
    }

    public Xmppproperties(String property, String value) {
        this.xmpppropertiesPK = new XmpppropertiesPK(property, value);
    }

    public XmpppropertiesPK getXmpppropertiesPK() {
        return xmpppropertiesPK;
    }

    public void setXmpppropertiesPK(XmpppropertiesPK xmpppropertiesPK) {
        this.xmpppropertiesPK = xmpppropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (xmpppropertiesPK != null ? xmpppropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Xmppproperties)) {
            return false;
        }
        Xmppproperties other = (Xmppproperties) object;
        if ((this.xmpppropertiesPK == null && other.xmpppropertiesPK != null) || (this.xmpppropertiesPK != null && !this.xmpppropertiesPK.equals(other.xmpppropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Xmppproperties[xmpppropertiesPK=" + xmpppropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
