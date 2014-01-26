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
@Table(name = "yahooproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Yahooproperties.findAll", query = "SELECT y FROM Yahooproperties y"), @NamedQuery(name = "Yahooproperties.findByProperty", query = "SELECT y FROM Yahooproperties y WHERE y.yahoopropertiesPK.property = :property"), @NamedQuery(name = "Yahooproperties.findByValue", query = "SELECT y FROM Yahooproperties y WHERE y.yahoopropertiesPK.value = :value")})
public class Yahooproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected YahoopropertiesPK yahoopropertiesPK;

    public Yahooproperties() {
    }

    public Yahooproperties(YahoopropertiesPK yahoopropertiesPK) {
        this.yahoopropertiesPK = yahoopropertiesPK;
    }

    public Yahooproperties(String property, String value) {
        this.yahoopropertiesPK = new YahoopropertiesPK(property, value);
    }

    public YahoopropertiesPK getYahoopropertiesPK() {
        return yahoopropertiesPK;
    }

    public void setYahoopropertiesPK(YahoopropertiesPK yahoopropertiesPK) {
        this.yahoopropertiesPK = yahoopropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (yahoopropertiesPK != null ? yahoopropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Yahooproperties)) {
            return false;
        }
        Yahooproperties other = (Yahooproperties) object;
        if ((this.yahoopropertiesPK == null && other.yahoopropertiesPK != null) || (this.yahoopropertiesPK != null && !this.yahoopropertiesPK.equals(other.yahoopropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Yahooproperties[yahoopropertiesPK=" + yahoopropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
