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
@Table(name = "ircchannellist", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Ircchannellist.findAll", query = "SELECT i FROM Ircchannellist i"), @NamedQuery(name = "Ircchannellist.findByChannel", query = "SELECT i FROM Ircchannellist i WHERE i.channel = :channel")})
public class Ircchannellist implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Channel")
    private String channel;

    public Ircchannellist() {
    }

    public Ircchannellist(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        String oldChannel = this.channel;
        this.channel = channel;
        changeSupport.firePropertyChange("channel", oldChannel, channel);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (channel != null ? channel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ircchannellist)) {
            return false;
        }
        Ircchannellist other = (Ircchannellist) object;
        if ((this.channel == null && other.channel != null) || (this.channel != null && !this.channel.equals(other.channel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Ircchannellist[channel=" + channel + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
