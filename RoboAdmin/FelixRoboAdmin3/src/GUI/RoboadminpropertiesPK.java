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
public class RoboadminpropertiesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Property")
    private String property;
    @Basic(optional = false)
    @Column(name = "Value")
    private String value;

    public RoboadminpropertiesPK() {
    }

    public RoboadminpropertiesPK(String property, String value) {
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (property != null ? property.hashCode() : 0);
        hash += (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoboadminpropertiesPK)) {
            return false;
        }
        RoboadminpropertiesPK other = (RoboadminpropertiesPK) object;
        if ((this.property == null && other.property != null) || (this.property != null && !this.property.equals(other.property))) {
            return false;
        }
        if ((this.value == null && other.value != null) || (this.value != null && !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.RoboadminpropertiesPK[property=" + property + ", value=" + value + "]";
    }

}
