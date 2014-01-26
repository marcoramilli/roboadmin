/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Luca
 */
@Embeddable
public class AcceptPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @Column(name = "Protocol")
    private String protocol;

    public AcceptPK() {
    }

    public AcceptPK(String username, String protocol) {
        this.username = username;
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (protocol != null ? protocol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcceptPK)) {
            return false;
        }
        AcceptPK other = (AcceptPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.protocol == null && other.protocol != null) || (this.protocol != null && !this.protocol.equals(other.protocol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.AcceptPK[username=" + username + ", protocol=" + protocol + "]";
    }

}
