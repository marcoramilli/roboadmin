/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SkypeProperty.java
 *
 * Created on 1-feb-2010, 13.33.09
 */

package GUI;
import java.sql.*;
import configurator.patchConfigurator.Configurator;
import db.mySQL.MySQLDataBase;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import log.fileLogger.FileLogger;
/**
 *
 * @author Luca
 * Classe per la gestione delle proprietà di Skype
 */
public class SkypeProperty extends javax.swing.JFrame {

    /** Creates new form SkypeProperty */
    public SkypeProperty() {
        initComponents();
        inizializza();
        //IL BOTTONE DI AGGIUNTA DI UNA PROPRIETà è RESO INVISIBILE INQUANTO ANCHE SE SI PERMETTESSE L'AGGIUNTA DI UNA PROPERTY RA POI NON SAPREBBE
        //COME GESTIRLA PERTANTO è RESO INUSABILE
        jButtonNew.setVisible(false);
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
        skypepropertiesQuery = java.beans.Beans.isDesignTime() ? null : RoboAdminDBPUEntityManager.createQuery("SELECT s FROM Skypeproperties s");
        skypepropertiesList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(skypepropertiesQuery.getResultList());
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPaneldx = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSkypeProperties = new javax.swing.JTable();
        jPanelsx = new javax.swing.JPanel();
        jLabelProperty = new javax.swing.JLabel();
        jLabelValue = new javax.swing.JLabel();
        jTextFieldProperty = new javax.swing.JTextField();
        jTextFieldValue = new javax.swing.JTextField();
        jButtonNew = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonSubmit = new javax.swing.JButton();

        setTitle("Skype Property");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(250);

        jPaneldx.setLayout(new java.awt.BorderLayout());

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, skypepropertiesList, jTableSkypeProperties);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${skypepropertiesPK.property}"));
        columnBinding.setColumnName("Property");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${skypepropertiesPK.value}"));
        columnBinding.setColumnName("Value");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTableSkypeProperties);
        jTableSkypeProperties.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column=e.getColumn();
                ModificaDati(row,column);
            }
        });

        jPaneldx.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPaneldx);

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
                    .addGroup(jPanelsxLayout.createSequentialGroup()
                        .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelProperty)
                            .addComponent(jLabelValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldProperty, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldValue, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelsxLayout.createSequentialGroup()
                        .addComponent(jButtonSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jButtonDelete)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonNew)))
                .addContainerGap())
        );
        jPanelsxLayout.setVerticalGroup(
            jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelsxLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProperty)
                    .addComponent(jTextFieldProperty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelValue)
                    .addComponent(jTextFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                .addGroup(jPanelsxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNew)
                    .addComponent(jButtonDelete)
                    .addComponent(jButtonSubmit))
                .addGap(20, 20, 20))
        );

        jSplitPane1.setLeftComponent(jPanelsx);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
        // TODO add your handling code here:
        String NProperty=jTextFieldProperty.getText();
        String NValue=jTextFieldValue.getText();
        RendiPersistenteProprietà(NProperty,NValue);
        SkypePropertiesListOld.add(new Skypeproperties(NProperty, NValue));
}//GEN-LAST:event_jButtonNewActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
        String property=jTextFieldProperty.getText();
        String value=jTextFieldValue.getText();
        if (!(property.isEmpty() || value.isEmpty())){
            RimuoviRigaSkypeProperties(property,value);
        }
        int row=jTableSkypeProperties.getSelectedRow();
        if (row!=-1){
            RimuoviRigaSkypeProperties(row);
        } else{
            new ErrorForm("tupla non trovata");
        }
}//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        // TODO add your handling code here:
        Object [] dirtyRow=ModifiedRow.toArray();
        ModifiedRow.clear();
        for (int i=0; i<dirtyRow.length; i++){
            int row=Integer.parseInt(dirtyRow[i].toString());
            Object []Temp=new Object[jTableSkypeProperties.getColumnCount()];
            for (int j=0;j<jTableSkypeProperties.getColumnCount();j++){
                Temp[j]=jTableSkypeProperties.getValueAt(row, j);
            }
            String property=Temp[0].toString();
            String value=Temp[1].toString();
            try {
                Skypeproperties oldSkypeProperties=SkypePropertiesListOld.get(row);
                String oldProperty=oldSkypeProperties.getSkypepropertiesPK().getProperty();
                String oldValue=oldSkypeProperties.getSkypepropertiesPK().getValue();
                RendiPersistenteModifica(oldProperty,oldValue,property,value);
            } catch(NumberFormatException e){
                new ErrorForm(e.toString());
            }
        }
        SkypePropertiesListOld.clear();
        CopiaListaSkypeProperties(SkypePropertiesListOld, skypepropertiesList);
}//GEN-LAST:event_jButtonSubmitActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SkypeProperty().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager RoboAdminDBPUEntityManager;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JLabel jLabelProperty;
    private javax.swing.JLabel jLabelValue;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPaneldx;
    private javax.swing.JPanel jPanelsx;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableSkypeProperties;
    private javax.swing.JTextField jTextFieldProperty;
    private javax.swing.JTextField jTextFieldValue;
    private java.util.List<GUI.Skypeproperties> skypepropertiesList;
    private javax.persistence.Query skypepropertiesQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    //Vartiabili di supporto per la gestione
    private Vector ModifiedRow=new Vector();
    private java.util.List<GUI.Skypeproperties> SkypePropertiesListOld;
    private Configurator conf=new Configurator();
    private MySQLDataBase db=null;
    private FileLogger log=new FileLogger();



    //=========================================METODI PRIVATI DI INIZIALIZZAZIONE

    protected void inizializza(){
        SkypePropertiesListOld=new ArrayList<Skypeproperties>();
        CopiaListaSkypeProperties(SkypePropertiesListOld, skypepropertiesList);
        //prepara il db log sempre su file al momento
        db=new MySQLDataBase(conf, log);
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
    private void CopiaListaSkypeProperties(List<Skypeproperties> SkypePropertiesOld,List<Skypeproperties> SkypePropertiesNew){
         for (int i=0;i<SkypePropertiesNew.size();i++){
            SkypePropertiesOld.add(new Skypeproperties(SkypePropertiesNew.get(i).getSkypepropertiesPK().getProperty(),SkypePropertiesNew.get(i).getSkypepropertiesPK().getValue()));
        }
    }


    /**
    * Rende una nuova property persistente nel DB
    *
    * @param property la nuova propietà
    * @param value il valore della nuova proprietà
    * @return void
    */
    private void RendiPersistenteProprietà(String property, String value){
        //prepara la query da mandare al db
	String query ="INSERT INTO skypeproperties (Property, Value) VALUES ('" + property + "', '"+ value +"')";
        //esegue spedisce la query
        db.executeSqlUpdate(query);
        //AGGIUNGE LA RIGA DIRETTAMENTE ANCHE SULLA TABELLA
        Skypeproperties newSkypeProp = new Skypeproperties(property, value);
        skypepropertiesList.add(newSkypeProp);
        jTableSkypeProperties.repaint();
    }



     /**
    * Rimuove una tupla selezionata in base all'id inserito nel campo id dalla tabella e dal DB
    * @param property stringa contenente il nome della proprietà da rimuovere
    * @param value stringa contenente il valore associato alla proprietà da rimuovere
    * @return void
    */
    private void RimuoviRigaSkypeProperties(String property,String value) {
        //preparo la query di rimozione
	String query = "DELETE FROM skypeproperties WHERE Property= '" + property +"' AND Value= '" + value +"' ";
	//eseguo la query
        db.executeSqlUpdate(query);
        //RIMUOVE LA RIGA DALLA TABELLA
        Skypeproperties RProperty=new Skypeproperties(property,value);
        skypepropertiesList.remove(RProperty);
        jTableSkypeProperties.repaint();
    }
    /**
    * Rimuove una tupla selezionata nella tabella dal DB
    * @return void
    */
    private void RimuoviRigaSkypeProperties(int row){
        String property= (String) jTableSkypeProperties.getValueAt(row,0);
        String value=(String) jTableSkypeProperties.getValueAt(row, 1);
	//preparo la query
        String query = "DELETE FROM skypeproperties WHERE Property= '" + property +"' AND Value= '" + value +"' ";
	//eseguo la query
	db.executeSqlUpdate(query);
        //RIMUOVE LA RIGA DALLA TABELLA
        skypepropertiesList.remove(row);
        jTableSkypeProperties.repaint();
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
        String query = "UPDATE skypeproperties SET Property= '" +property+"', Value= '"+ value +"' WHERE Property='"+ oldProperty +"' AND Value='"+ oldValue +"' ";
        //eseguo la query
        db.executeSqlUpdate(query);

    }

}