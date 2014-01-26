/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Luca
 */
@Entity
@Table(name = "invalidstring", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Invalidstring.findAll", query = "SELECT i FROM Invalidstring i"), @NamedQuery(name = "Invalidstring.findByInvalidString", query = "SELECT i FROM Invalidstring i WHERE i.invalidString = :invalidString")})
public class Invalidstring implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "InvalidString")
    private String invalidString;

    public Invalidstring() {
    }

    public Invalidstring(String invalidString) {
        this.invalidString = invalidString;
    }

    public String getInvalidString() {
        return invalidString;
    }

    public void setInvalidString(String invalidString) {
        String oldInvalidString = this.invalidString;
        this.invalidString = invalidString;
        changeSupport.firePropertyChange("invalidString", oldInvalidString, invalidString);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invalidString != null ? invalidString.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invalidstring)) {
            return false;
        }
        Invalidstring other = (Invalidstring) object;
        if ((this.invalidString == null && other.invalidString != null) || (this.invalidString != null && !this.invalidString.equals(other.invalidString))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Invalidstring[invalidString=" + invalidString + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
