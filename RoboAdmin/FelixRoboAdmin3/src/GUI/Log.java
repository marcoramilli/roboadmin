/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Luca
 */
@Entity
@Table(name = "Log", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"), @NamedQuery(name = "Log.findById", query = "SELECT l FROM Log l WHERE l.id = :id"), @NamedQuery(name = "Log.findBySender", query = "SELECT l FROM Log l WHERE l.sender = :sender"), @NamedQuery(name = "Log.findByDate", query = "SELECT l FROM Log l WHERE l.date = :date"), @NamedQuery(name = "Log.findByLog", query = "SELECT l FROM Log l WHERE l.log = :log")})
public class Log implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Sender")
    private String sender;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "log")
    private String log;

    public Log() {
    }

    public Log(Integer id) {
        this.id = id;
    }

    public Log(Integer id, String sender, Date date, String log) {
        this.id = id;
        this.sender = sender;
        this.date = date;
        this.log = log;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        String oldSender = this.sender;
        this.sender = sender;
        changeSupport.firePropertyChange("sender", oldSender, sender);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Date oldDate = this.date;
        this.date = date;
        changeSupport.firePropertyChange("date", oldDate, date);
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        String oldLog = this.log;
        this.log = log;
        changeSupport.firePropertyChange("log", oldLog, log);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Log[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
