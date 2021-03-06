/*
 * To change this license header, choose License Headers in Method Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.ui;

import java.util.ArrayList;
import static javafx.scene.input.KeyCode.I;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import risk.builder.MethodBuilder;
import risk.controller.MethodController;
import risk.controller.RiskController;
import risk.entity.Methods;
import risk.entity.Risks;

/**
 *
 * @author nguyennam
 */
public class MethodManagement extends javax.swing.JPanel {
    
    private ArrayList<Methods> methods;
    private ArrayList<Risks> risks;
    /**
     * Creates new form MethodManagement
     */
    public MethodManagement() {
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
        idLabel.setText("");
        nameField.setText("");
        descriptionTxt.setText("");
        codeField.setText("");  
        startStatus();
        costField.setText("");
        timeField.setText("");
        diffCb.setSelectedIndex(0);
        efficiencyCb.setSelectedIndex(0);
        methodTable.clearSelection();
    }

    public boolean checkNumberData() {
        String typeError = new String();
        ArrayList<String> typeErrorArr = new ArrayList<>();

        // Check each field
        try {
            int value = Integer.valueOf(costField.getText());
        } catch (Exception e) {
            typeErrorArr.add("cost field");
        }
        // Check each field
        System.out.println("risk.ui.MethodManagement.checkNumberData()");
        try {
            int value = Integer.valueOf(timeField.getText());
        } catch (Exception e) {
            typeErrorArr.add("time field");
        }

        // Create error message
        if (typeErrorArr.size() > 0) {
            String firstError = typeErrorArr.get(0);
            // Capitalize
            firstError = firstError.substring(0, 1).toUpperCase() + firstError.substring(1);
            typeError += firstError;
            for (int i = 1; i < typeErrorArr.size(); i++) {
                typeError += ", " + typeErrorArr.get(i);
            }
            typeError += " must be numeric!";

            JOptionPane.showMessageDialog(new JFrame(),
                    typeError, "Warning",
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
        if (costField.getText().equals("")){
            errorArr.add("cost field");
        }
        if (timeField.getText().equals("")){
            errorArr.add("time field");
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
    public void setMethodOnTable(Methods method){
        idLabel.setText(Integer.toString(method.getId()));
        codeField.setText(method.getCode());
        nameField.setText(method.getName());
        descriptionTxt.setText(method.getDescription());
        costField.setText(String.valueOf(method.getCost()));
        timeField.setText(String.valueOf(method.getCost()));
        diffCb.setSelectedItem(String.valueOf(method.getDiff()));
        timeField.setText(String.valueOf(method.getTime()));
        efficiencyCb.setSelectedItem(String.valueOf(method.getEfficiency()));

    }
    public Methods getMethodFromTable(){
        //need to fix
        Methods method = new MethodBuilder()
                .addId(Integer.valueOf(idLabel.getText()))
                .addCode(codeField.getText())
                .addDescription(descriptionTxt.getText())
                .addName(nameField.getText())
                .addCost(Integer.valueOf(costField.getText()))
                .addTime(Integer.valueOf(timeField.getText()))
                .addDiff(diffCb.getSelectedIndex())
                .addEfficiency(efficiencyCb.getSelectedIndex())
                .addRisk(risks.get(risksCb.getSelectedIndex()))
                .build();
        return method;
    }
    
    public void updateTable(){
        DefaultTableModel model = (DefaultTableModel) methodTable.getModel();
        model.setRowCount(0);
        methods = MethodController.index();
        
        for (int i = 0; i < methods.size(); i++) {
            Methods tempMethod = (Methods) methods.get(i);
            model.addRow(new Object[] {
                tempMethod.getId(), 
                tempMethod.getCode(), 
                tempMethod.getName(), 
                tempMethod.getDescription(), 
                tempMethod.getCost(),
                tempMethod.getDiff(), 
                tempMethod.getTime(), 
                tempMethod.getPriority(), 
                tempMethod.getEfficiency(), 
                tempMethod.getRisks().getName()
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
        methodTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        codeField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        descriptionTxt = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        costField = new javax.swing.JTextField();
        timeField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        efficiencyCb = new javax.swing.JComboBox<>();
        diffCb = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        risksCb = new javax.swing.JComboBox<>();
        newBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();

        jPanel2.setPreferredSize(new java.awt.Dimension(755, 528));

        methodTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        methodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Code", "Name", "Description", "Cost", "Difficulty", "Time", "Priority", "Efficiency", "Risk"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        methodTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                methodTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(methodTable);

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

        idLabel.setText(" ");

        jLabel2.setText("Cost:");

        jLabel3.setText("Difficulty:");

        jLabel4.setText("Time (hour):");

        costField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costFieldActionPerformed(evt);
            }
        });

        timeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeFieldActionPerformed(evt);
            }
        });

        jLabel8.setText("Efficiency:");

        String[] stringArr = new String[101];
        for (int i = 0; i < 101; i++) {
            stringArr[i] = String.valueOf(i);
        }
        efficiencyCb.setModel(new javax.swing.DefaultComboBoxModel<>(stringArr));

        String[] stringArr1 = new String[11];
        for (int i = 0; i < 10; i++) {
            stringArr1[i] = String.valueOf(i);
        }
        diffCb.setModel(new javax.swing.DefaultComboBoxModel<>(stringArr1));

        jLabel9.setText("Risk:");

        risks = RiskController.index();
        String[] riskArray = new String[risks.size()];
        for (int i = 0; i < risks.size(); i++){
            riskArray[i] = risks.get(i).getName();
        }
        String[] tempArray = new String[]{"",""};
        risksCb.setModel(new javax.swing.DefaultComboBoxModel<>(riskArray));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(79, 79, 79)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(costField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                    .addComponent(codeField, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(38, 38, 38))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(diffCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(23, 23, 23)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(efficiencyCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(risksCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(costField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(efficiencyCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diffCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(risksCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void methodTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_methodTableMouseClicked
        // TODO add your handling code here:

        int row = methodTable.rowAtPoint(evt.getPoint());
        DefaultTableModel model = (DefaultTableModel) methodTable.getModel();
        int id = (int) model.getValueAt(row, 0);
        Methods method = (Methods) MethodController.show(id);
        setMethodOnTable(method);
        editStatus();
    }//GEN-LAST:event_methodTableMouseClicked

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void newBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseReleased
        // TODO add your handling code here:
        if(validateData()){
            //need to fix userId
            Methods method = new MethodBuilder()
            .addCode(codeField.getText())
            .addDescription(descriptionTxt.getText())
            .addName(nameField.getText())
            .addCode(codeField.getText())
            .addTime(Integer.valueOf(timeField.getText()))
            .addDiff(diffCb.getSelectedIndex())
            .addEfficiency(efficiencyCb.getSelectedIndex())
            .addRisk(risks.get(risksCb.getSelectedIndex()))
            .build();
            MethodController.create(method);
            updateTable();
        }
    }//GEN-LAST:event_newBtnMouseReleased

    private void updateBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseReleased
        // TODO add your handling code here:
        if(validateData()){
            MethodController.update(getMethodFromTable());
            updateTable();
        }
    }//GEN-LAST:event_updateBtnMouseReleased

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseReleased
        // TODO add your handling code here:
        MethodController.delete(Integer.valueOf(idLabel.getText()));
        updateTable();
        resetFields();
    }//GEN-LAST:event_deleteBtnMouseReleased

    private void clearBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBtnMouseReleased
        // TODO add your handling code here:
        resetFields();
    }//GEN-LAST:event_clearBtnMouseReleased

    private void costFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costFieldActionPerformed

    private void timeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearBtn;
    private javax.swing.JTextField codeField;
    private javax.swing.JTextField costField;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextArea descriptionTxt;
    private javax.swing.JComboBox<String> diffCb;
    private javax.swing.JComboBox<String> efficiencyCb;
    private javax.swing.JLabel idLabel;
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
    private javax.swing.JTable methodTable;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton newBtn;
    private javax.swing.JComboBox<String> risksCb;
    private javax.swing.JTextField timeField;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
