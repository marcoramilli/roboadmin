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
@Table(name = "mysqldbproperties", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Mysqldbproperties.findAll", query = "SELECT m FROM Mysqldbproperties m"), @NamedQuery(name = "Mysqldbproperties.findByProperty", query = "SELECT m FROM Mysqldbproperties m WHERE m.mysqldbpropertiesPK.property = :property"), @NamedQuery(name = "Mysqldbproperties.findByValue", query = "SELECT m FROM Mysqldbproperties m WHERE m.mysqldbpropertiesPK.value = :value")})
public class Mysqldbproperties implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MysqldbpropertiesPK mysqldbpropertiesPK;

    public Mysqldbproperties() {
    }

    public Mysqldbproperties(MysqldbpropertiesPK mysqldbpropertiesPK) {
        this.mysqldbpropertiesPK = mysqldbpropertiesPK;
    }

    public Mysqldbproperties(String property, String value) {
        this.mysqldbpropertiesPK = new MysqldbpropertiesPK(property, value);
    }

    public MysqldbpropertiesPK getMysqldbpropertiesPK() {
        return mysqldbpropertiesPK;
    }

    public void setMysqldbpropertiesPK(MysqldbpropertiesPK mysqldbpropertiesPK) {
        this.mysqldbpropertiesPK = mysqldbpropertiesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mysqldbpropertiesPK != null ? mysqldbpropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mysqldbproperties)) {
            return false;
        }
        Mysqldbproperties other = (Mysqldbproperties) object;
        if ((this.mysqldbpropertiesPK == null && other.mysqldbpropertiesPK != null) || (this.mysqldbpropertiesPK != null && !this.mysqldbpropertiesPK.equals(other.mysqldbpropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Mysqldbproperties[mysqldbpropertiesPK=" + mysqldbpropertiesPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
