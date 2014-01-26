/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MySQLDBPropertiesPannel.java
 *
 * Created on 30-dic-2009, 18.01.16
 */

package GUI;
import db.mySQL.MySQLDataBase;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
/**
 *
 * @author Luca
 * Classe per la gestione del MySQLDB
 */
public class MySQLDBPropertiesPannel extends javax.swing.JPanel {

    /** Creates new form MySQLDBPropertiesPannel */
    public MySQLDBPropertiesPannel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        RoboAdminDBPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("RoboAdminDBPU").createEntityManager();
        mysqldbpropertiesQuery = java.beans.Beans.isDesignTime() ? null : RoboAdminDBPUEntityManager.createQuery("SELECT m FROM Mysqldbproperties m");
        mysqldbpropertiesList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(mysqldbpropertiesQuery.getResultList());
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelsx = new javax.swing.JPanel();
        jLabelProperty = new javax.swing.JLabel();
        jLabelValue = new javax.swing.JLabel();
        jTextFieldProperty = new javax.swing.JTextField();
        jTextFieldValue = new javax.swing.JTextField();
        jButtonNew = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonSubmit = new javax.swing.JButton();
        jPaneldx = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMySQLDBProperties = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(250);

        jLabelProperty.setText("Property:");

        jLabelValue.setText("Value:");

        jButtonNew.setText("New");
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelsxLayout = new javax.swing.GroupLayout(jPanelsx);
        jPanelsx.setLayout(jPanelsxLayout);
        jPanelsxLayout.setHorizontalGroup(
            jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelsxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelsxLayout.createSequentialGroup()
                        .addComponent(jButtonSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonNew)
                        .addContainerGap())
                    .addGroup(jPanelsxLayout.createSequentialGroup()
                        .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelProperty)
                            .addComponent(jLabelValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldValue, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldProperty, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                        .addContainerGap(26, Short.MAX_VALUE))))
        );
        jPanelsxLayout.setVerticalGroup(
            jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelsxLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProperty)
                    .addComponent(jTextFieldProperty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelValue)
                    .addComponent(jTextFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNew)
                    .addComponent(jButtonDelete)
                    .addComponent(jButtonSubmit))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanelsx);

        jPaneldx.setLayout(new java.awt.BorderLayout());

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mysqldbpropertiesList, jTableMySQLDBProperties);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mysqldbpropertiesPK.property}"));
        columnBinding.setColumnName("Property");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mysqldbpropertiesPK.value}"));
        columnBinding.setColumnName("Value");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTableMySQLDBProperties);
        jTableMySQLDBProperties.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column=e.getColumn();
                ModificaDati(row,column);
            }
        });

        jPaneldx.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPaneldx);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
        // TODO add your handling code here:
        String NProperty=jTextFieldProperty.getText();
        String NValue=jTextFieldValue.getText();
        RendiPersistenteProprietà(NProperty,NValue);
        mySQLDBPropertiesListOld.add(new Mysqldbproperties(NProperty, NValue));

    }//GEN-LAST:event_jButtonNewActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
       //l'unico campo fondamentale per la chiamata con campi è l'id gli altri possono essere null
        String property=jTextFieldProperty.getText();
        String value=jTextFieldValue.getText();
        if (!(property.isEmpty() || value.isEmpty())){
            RimuoviRigaMySQLDBPropProperties(property,value);
        }
        int row=jTableMySQLDBProperties.getSelectedRow();
        if (row!=-1){
            RimuoviRigaMySQLDBPropProperties(row);
        }
        else{
            new ErrorForm("tupla non trovata");
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        // TODO add your handling code here:
        Object [] dirtyRow=ModifiedRow.toArray();
        ModifiedRow.clear();
        for (int i=0; i<dirtyRow.length; i++){
            int row=Integer.parseInt(dirtyRow[i].toString());
            Object []Temp=new Object[jTableMySQLDBProperties.getColumnCount()];
            for (int j=0;j<jTableMySQLDBProperties.getColumnCount();j++){
              Temp[j]=jTableMySQLDBProperties.getValueAt(row, j);
            }
            String property=Temp[0].toString();
            String value=Temp[1].toString();
            try {
                Mysqldbproperties oldMySQLProperties=mySQLDBPropertiesListOld.get(row);
                String oldProperty=oldMySQLProperties.getMysqldbpropertiesPK().getProperty();
                String oldValue=oldMySQLProperties.getMysqldbpropertiesPK().getValue();
                RendiPersistenteModifica(oldProperty,oldValue,property,value);
            }
            catch(NumberFormatException e){
                new ErrorForm(e.toString());
            }
        }
        mySQLDBPropertiesListOld.clear();
        CopiaListaMysqldbproperties(mySQLDBPropertiesListOld, mysqldbpropertiesList);
    }//GEN-LAST:event_jButtonSubmitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager RoboAdminDBPUEntityManager;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JLabel jLabelProperty;
    private javax.swing.JLabel jLabelValue;
    private javax.swing.JPanel jPaneldx;
    private javax.swing.JPanel jPanelsx;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableMySQLDBProperties;
    private javax.swing.JTextField jTextFieldProperty;
    private javax.swing.JTextField jTextFieldValue;
    private java.util.List<GUI.Mysqldbproperties> mysqldbpropertiesList;
    private javax.persistence.Query mysqldbpropertiesQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    //Vartiabili di supporto per la gestione
    private Vector ModifiedRow=new Vector();
    private java.util.List<GUI.Mysqldbproperties> mySQLDBPropertiesListOld;
    private MySQLDataBase db=null;




    //=========================================METODI PRIVATI DI INIZIALIZZAZIONE

    protected void inizializza(MySQLDataBase dataBase){
        mySQLDBPropertiesListOld=new ArrayList<Mysqldbproperties>();
        CopiaListaMysqldbproperties(mySQLDBPropertiesListOld, mysqldbpropertiesList);
        //prepara il db log sempre su file al momento
        db=dataBase;
        //IL BOTTONE DI AGGIUNTA DI UNA PROPRIETà è RESO INVISIBILE INQUANTO ANCHE SE SI PERMETTESSE L'AGGIUNTA DI UNA PROPERTY RA POI NON SAPREBBE
        //COME GESTIRLA PERTANTO è RESO INUSABILE
        jButtonNew.setVisible(false);
    }


    //=========================================METODI PRIVATI PER LA GESTIONE DELLA TABELLA

    
    /**
    * Tiene traccia delle celle interesate da una modifica
    *
    * @param row riga
    * @param column colonna
    * @return void
    */
    private void ModificaDati(int row ,int column){
        //Invocato a ogni modifica della tabella
        //se la colonna modificata è -1 vuol dire che si sta aggiungendo togliendo una riga
        if (column!=-1){
            if (!ModifiedRow.contains(row))
            ModifiedRow.add(row);
        }
    }

       /**
    * Crea un lista con i dati della tabella di supporto al di andare a operare sulla giusta tubla anche se questa è stata modificata
    *
    * @param List<Accept> vecchi
    * @param List<Accept> nuovi
    * @return void
    */
    private void CopiaListaMysqldbproperties(List<Mysqldbproperties> mySQLDBPropertiesOld,List<Mysqldbproperties>mySQLDBPropertiesNew){
         for (int i=0;i<mySQLDBPropertiesNew.size();i++){
            mySQLDBPropertiesOld.add(new Mysqldbproperties(mySQLDBPropertiesNew.get(i).getMysqldbpropertiesPK().getProperty(),mySQLDBPropertiesNew.get(i).getMysqldbpropertiesPK().getValue()));
        }
    }



    /**
    * Rende una nuova property persistente nel DB
    * @param property la nuova propietà
    * @param value il valore della nuova proprietà
    * @return void
    */
    private void RendiPersistenteProprietà(String property, String value){
        //prepara la query da mandare al db
	String query ="INSERT INTO mysqldbproperties (Property, Value) VALUES ('" + property + "', '"+ value +"')";
        //esegue spedisce la query
        db.executeSqlUpdate(query);
        //AGGIUNGE LA RIGA DIRETTAMENTE ANCHE SULLA TABELLA
        Mysqldbproperties newRMySQLProp = new Mysqldbproperties(property, value);
        mysqldbpropertiesList.add(newRMySQLProp);
        jTableMySQLDBProperties.repaint();
    }


     /**
    * Rimuove una tupla selezionata in base all'id inserito nel campo id dalla tabella e dal DB
    * @param property stringa contenente il nome della proprietà da rimuovere
    * @param value stringa contenente il valore associato alla proprietà da rimuovere
    * @return void
    */
    private void RimuoviRigaMySQLDBPropProperties(String property,String value) {
        //preparo la query di rimozione
	String query = "DELETE FROM mysqldbproperties WHERE Property= '" + property +"' AND Value= '" + value +"' ";
	//eseguo la query
        db.executeSqlUpdate(query);
        //RIMUOVE LA RIGA DALLA TABELLA
        Mysqldbproperties RProperty=new Mysqldbproperties(property,value);
        mysqldbpropertiesList.remove(RProperty);
        jTableMySQLDBProperties.repaint();
    }
    /**
    * Rimuove una tupla selezionata nella tabella dal DB
    * @return void
    */
    private void RimuoviRigaMySQLDBPropProperties(int row){
        String property= (String) jTableMySQLDBProperties.getValueAt(row,0);
        String value= (String) jTableMySQLDBProperties.getValueAt(row, 1);
	//preparo la query
        String query = "DELETE FROM mysqldbproperties WHERE Property= '" + property +"' AND Value= '" + value +"' ";
	//eseguo la query
	db.executeSqlUpdate(query);
        //RIMUOVE LA RIGA DALLA TABELLA
        mysqldbpropertiesList.remove(row);
        jTableMySQLDBProperties.repaint();
    }


     /**
    * Rende le modifiche apportate alle tuple sulla tabella persistenti sul DB, la ricerca della tupla da modificare è scolta in base a oldProperty e
    * oldValue
    * @param oldProperty stringa contenente il valore precedente alla modifica della propietà
    * @param oldValue stringa contenente il valore precedente alla modifica del value
    * @param property stringa contenente il valore aggiornato della propietà
    * @param value stringa contente il valore aggiornato del value
    * @return void
    */
    private void RendiPersistenteModifica(String oldProperty, String oldValue, String property, String value) {
        String query = "UPDATE mysqldbproperties SET Property= '" +property+"', Value= '"+ value +"' WHERE Property='"+ oldProperty +"' AND Value='"+ oldValue +"' ";
        //eseguo la query
        db.executeSqlUpdate(query);

    }


}