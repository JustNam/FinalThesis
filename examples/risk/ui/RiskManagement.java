/*
 * To change this license header, choose License Headers in Risk Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.ui;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import risk.builder.RiskBuilder;
import risk.controller.ProjectController;
import risk.controller.RiskController;
import risk.controller.RiskTypeController;
import risk.entity.Projects;
import risk.entity.RiskTypes;
import risk.entity.Risks;

/**
 *
 * @author nguyennam
 */
public class RiskManagement extends javax.swing.JPanel {
    
    private ArrayList<Risks> risks;
    private ArrayList<Projects> projects;
    private ArrayList<RiskTypes> riskTypes;
    /**
     * Creates new form RiskManagement
     */
    public RiskManagement() {
        initComponents();
        updateTable();
        startStatus();
    }

    public void startStatus(){
        newBtn.setEnabled(true);
        deleteBtn.setEnabled(false);
        updateBtn.setEnabled(false);
    }
    public void editStatus(){
        newBtn.setEnabled(false);
        deleteBtn.setEnabled(true);
        updateBtn.setEnabled(true);
    }
    
    public void resetFields(){
        idLb.setText("");
        nameField.setText("");
        descriptionTxt.setText("");
        codeField.setText("");
        financialField.setText("");
        projectsCb.setSelectedIndex(0);
        probabilityCb.setSelectedIndex(0);
        riskLevelCb.setSelectedIndex(0);
        riskTable.clearSelection();
        riskLevelCb.setSelectedIndex(0);
        startStatus();
    }
    public boolean checkNumberData() {
        String typeError = new String();
        ArrayList<String> typeErrorArr = new ArrayList<>();
        
        // Check each field
        try {
            int value = Integer.valueOf(financialField.getText());
        } catch (Exception e) {
            typeErrorArr.add("financial impact field");
        }
        
        // Create error message
        if (typeErrorArr.size()>0){
            String firstError = typeErrorArr.get(0);
            // Capitalize
            firstError = firstError.substring(0, 1).toUpperCase() + firstError.substring(1);
            typeError += firstError;
            for (int i = 1; i < typeErrorArr.size() - 1; i++) {
                typeError += ", " + typeErrorArr.get(i);
            }
            typeError += " must be numeric!";
            
            JOptionPane.showMessageDialog(new JFrame(),
            typeError,"Warning",
            JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
        
    public boolean validateData(){
        String error = new String();
        ArrayList<String> errorArr = new ArrayList<>();
        if (codeField.getText().equals("")){
            errorArr.add("code field");
        }
        if (nameField.getText().equals("")){
            errorArr.add("name field");
        }
        
        if (financialField.getText().equals("")){
            errorArr.add("financial impact field");
        }
        
        if (errorArr.size() > 0){
            error = "Please fill the ";
            for (int i = 0; i < errorArr.size() - 1; i++) {
                error += errorArr.get(i) + ", ";
            }
            error += errorArr.get(errorArr.size()-1)+ "!";
            
            JOptionPane.showMessageDialog(new JFrame(),
            error,"Warning",
            JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return checkNumberData();
        }
    }
    public void setRiskOnTable(Risks risk){
        idLb.setText(Integer.toString(risk.getId()));
        codeField.setText(risk.getCode());
        nameField.setText(risk.getName());
        descriptionTxt.setText(risk.getDescription());
        financialField.setText(risk.getFinancialImpact().toString());
        riskLevelCb.setSelectedItem(risk.getRiskLevel());
        riskTypeCb.setSelectedItem(risk.getRiskTypes().getName());
        probabilityCb.setSelectedItem(risk.getProbability().toString());
        projectsCb.setSelectedItem(risk.getProjects().getName());
    }
    public Risks getRiskFromTable(){
        Risks risk = new RiskBuilder()
                .addId(Integer.valueOf(idLb.getText()))
                .addCode(codeField.getText())
                .addDescription(descriptionTxt.getText())
                .addName(nameField.getText())
                .addFinancialImpact(Integer.valueOf(financialField.getText()))
                .addRiskLevel(riskLevelCb.getSelectedItem().toString())
                .addRiskType(riskTypes.get(riskLevelCb.getSelectedIndex()))
                .addProject(projects.get(projectsCb.getSelectedIndex()))
                .addProbability(probabilityCb.getSelectedIndex())
                .build();
        return risk;
    }
    
    public void updateTable(){
        DefaultTableModel model = (DefaultTableModel) riskTable.getModel(); 
        model.setRowCount(0);
        risks = RiskController.index();
        for (int i = 0; i < risks.size(); i++) {
            Risks tempRisk = (Risks) risks.get(i);
            model.addRow(new Object[] {
                tempRisk.getId(),
                tempRisk.getCode(),
                tempRisk.getName(),
                tempRisk.getDescription(),
                tempRisk.getRiskLevel(),
                String.valueOf(tempRisk.getFinancialImpact()),
                String.valueOf(tempRisk.getProbability()),
                tempRisk.getProjects().getName()
            });
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        riskTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        codeField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        descriptionTxt = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        idLb = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        riskLevelCb = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        financialField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        projectsCb = new javax.swing.JComboBox<>();
        probabilityCb = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        riskTypeCb = new javax.swing.JComboBox<>();
        newBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();

        riskTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        riskTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Code", "Name", "Description", "Risk Level", "Financial Impact", "Probability","Project"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        riskTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                riskTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(riskTable);

        jLabel5.setText("Code:");

        jLabel6.setText("Name:");

        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Description:");

        descriptionTxt.setColumns(20);
        descriptionTxt.setRows(5);
        jScrollPane5.setViewportView(descriptionTxt);

        jLabel1.setText("ID:");

        idLb.setText(" ");

        jLabel2.setText("Risk Level:");

        riskLevelCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Extreme", "High", "Medium", "Low" }));

        jLabel3.setText("Probability:");

        jLabel4.setText("Financial Impact:");

        jLabel8.setText("Project:");

        projects = ProjectController.index();
        String[] projectArray = new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {
            projectArray[i] = projects.get(i).getName();
        }
        String[] tempArray = new String[]{"",""};
        projectsCb.setModel(new javax.swing.DefaultComboBoxModel<>(projectArray));

        String[] stringArr = new String[101];
        for (int i = 0; i < 101; i++) {
            stringArr[i] = String.valueOf(i);
        }
        probabilityCb.setModel(new javax.swing.DefaultComboBoxModel<>(stringArr));

        jLabel9.setText("Risk Type:");

        riskTypes = RiskTypeController.index();
        String[] riskTypeArr = new String[riskTypes.size()];
        for (int i = 0; i < riskTypes.size(); i++) {
            riskTypeArr[i] = riskTypes.get(i).getName();
        }
        String[] tempArray2 = new String[]{"",""};
        riskTypeCb.setModel(new javax.swing.DefaultComboBoxModel<>(riskTypeArr));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(riskTypeCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(idLb, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(projectsCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(riskLevelCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nameField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codeField, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addComponent(jLabel7)
                        .addGap(36, 36, 36)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addComponent(financialField))
                    .addComponent(probabilityCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLb)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(riskLevelCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(financialField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(riskTypeCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(projectsCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(probabilityCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        newBtn.setText("Add");
        newBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                newBtnMouseReleased(evt);
            }
        });

        updateBtn.setText("Update");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                updateBtnMouseReleased(evt);
            }
        });
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                deleteBtnMouseReleased(evt);
            }
        });

        clearBtn.setText("Clear fields");
        clearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                clearBtnMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(newBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearBtn)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void riskTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_riskTableMouseClicked
        // TODO add your handling code here:

        int row = riskTable.rowAtPoint(evt.getPoint());
        DefaultTableModel model = (DefaultTableModel) riskTable.getModel();
        int id = (int) model.getValueAt(row, 0);
        Risks risk = (Risks) RiskController.show(id);
        setRiskOnTable(risk);
        editStatus();
    }//GEN-LAST:event_riskTableMouseClicked

    private void newBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseReleased
        // TODO add your handling code here:
        if(validateData()){
            Risks risk = new RiskBuilder()
            .addCode(codeField.getText())
            .addDescription(descriptionTxt.getText())
            .addName(nameField.getText())
            .addFinancialImpact(Integer.valueOf(financialField.getText()))
            .addProject(projects.get(projectsCb.getSelectedIndex()))
            .addRiskLevel(riskLevelCb.getSelectedItem().toString())
            .addProbability(probabilityCb.getSelectedIndex())
            .addRiskType(riskTypes.get(riskLevelCb.getSelectedIndex()))
            .build();
            RiskController.create(risk);
            updateTable();
        }
    }//GEN-LAST:event_newBtnMouseReleased

    private void updateBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseReleased
        // TODO add your handling code here:
        if(validateData()){
            RiskController.update(getRiskFromTable());
            updateTable();
        }
    }//GEN-LAST:event_updateBtnMouseReleased

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseReleased
        // TODO add your handling code here:
        RiskController.delete(Integer.valueOf(idLb.getText()));
        updateTable();
        resetFields();
    }//GEN-LAST:event_deleteBtnMouseReleased

    private void clearBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBtnMouseReleased
        // TODO add your handling code here:
        resetFields();
    }//GEN-LAST:event_clearBtnMouseReleased

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearBtn;
    private javax.swing.JTextField codeField;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextArea descriptionTxt;
    private javax.swing.JTextField financialField;
    private javax.swing.JLabel idLb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton newBtn;
    private javax.swing.JComboBox<String> probabilityCb;
    private javax.swing.JComboBox<String> projectsCb;
    private javax.swing.JComboBox<String> riskLevelCb;
    private javax.swing.JTable riskTable;
    private javax.swing.JComboBox<String> riskTypeCb;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
