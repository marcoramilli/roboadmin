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
@Table(name = "MP", catalog = "RoboAdminDB", schema = "")
@NamedQueries({@NamedQuery(name = "Mp.findAll", query = "SELECT m FROM Mp m"), @NamedQuery(name = "Mp.findById", query = "SELECT m FROM Mp m WHERE m.id = :id"), @NamedQuery(name = "Mp.findByNome", query = "SELECT m FROM Mp m WHERE m.nome = :nome"), @NamedQuery(name = "Mp.findByIntegrità", query = "SELECT m FROM Mp m WHERE m.integrità = :integrità"), @NamedQuery(name = "Mp.findByRiservatezza", query = "SELECT m FROM Mp m WHERE m.riservatezza = :riservatezza")})
public class Mp implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "integrità")
    private char integrità;
    @Basic(optional = false)
    @Column(name = "riservatezza")
    private char riservatezza;

    public Mp() {
    }

    public Mp(Integer id) {
        this.id = id;
    }

    public Mp(Integer id, String nome, char integrità, char riservatezza) {
        this.id = id;
        this.nome = nome;
        this.integrità = integrità;
        this.riservatezza = riservatezza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        String oldNome = this.nome;
        this.nome = nome;
        changeSupport.firePropertyChange("nome", oldNome, nome);
    }

    public char getIntegrità() {
        return integrità;
    }

    public void setIntegrità(char integrità) {
        char oldIntegrità = this.integrità;
        this.integrità = integrità;
        changeSupport.firePropertyChange("integrità", oldIntegrità, integrità);
    }

    public char getRiservatezza() {
        return riservatezza;
    }

    public void setRiservatezza(char riservatezza) {
        char oldRiservatezza = this.riservatezza;
        this.riservatezza = riservatezza;
        changeSupport.firePropertyChange("riservatezza", oldRiservatezza, riservatezza);
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
        if (!(object instanceof Mp)) {
            return false;
        }
        Mp other = (Mp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Mp[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
