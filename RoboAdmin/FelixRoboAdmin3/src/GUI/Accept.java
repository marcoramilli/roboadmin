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
@Table(name = "Accept", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Accept.findAll", query = "SELECT a FROM Accept a"), @NamedQuery(name = "Accept.findByUsername", query = "SELECT a FROM Accept a WHERE a.acceptPK.username = :username"), @NamedQuery(name = "Accept.findByProtocol", query = "SELECT a FROM Accept a WHERE a.acceptPK.protocol = :protocol")})
public class Accept implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AcceptPK acceptPK;

    public Accept() {
    }

    public Accept(AcceptPK acceptPK) {
        this.acceptPK = acceptPK;
    }

    public Accept(String username, String protocol) {
        this.acceptPK = new AcceptPK(username, protocol);
    }

    public AcceptPK getAcceptPK() {
        return acceptPK;
    }

    public void setAcceptPK(AcceptPK acceptPK) {
        this.acceptPK = acceptPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acceptPK != null ? acceptPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accept)) {
            return false;
        }
        Accept other = (Accept) object;
        if ((this.acceptPK == null && other.acceptPK != null) || (this.acceptPK != null && !this.acceptPK.equals(other.acceptPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Accept[acceptPK=" + acceptPK + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
