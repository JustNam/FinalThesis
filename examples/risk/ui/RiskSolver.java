/*
 * To change this license header, choose License Headers in Fitness Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.ui;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import risk.builder.FitnessBuilder;
import risk.controller.FitnessController;
import risk.controller.ProjectController;
import risk.controller.RiskController;
import risk.entity.Fitness;
import risk.entity.Projects;
import risk.entity.Results;
import risk.entity.Risks;

/**
 *
 * @author nguyennam
 */
public class RiskSolver extends javax.swing.JPanel {

    ArrayList<Fitness> fitnesses;
    ArrayList<Projects> projects;
    ArrayList<Risks> risks;
    Results results;
    
    /**
     * Creates new form RiskSolver
     */
    public RiskSolver() {
        initComponents();
        updateTable();
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
        costCb.setSelectedIndex(0);
        diffCb.setSelectedIndex(0);
        financialCb.setSelectedIndex(0);
        methodCb.setSelectedIndex(0);
        priorityCb.setSelectedIndex(0);
        projectsCb.setSelectedIndex(0);
        riskCb.setSelectedIndex(0);
        riskLevelCb.setSelectedIndex(0);
        timeCb.setSelectedIndex(0);
    }
    

    public boolean checkNumberData() {
        String typeError = new String();
        ArrayList<String> typeErrorArr = new ArrayList<>();

        // Check each field
        try {
            int value = Integer.valueOf(noEvaluationField.getText());
        } catch (Exception e) {
            typeErrorArr.add("number of evaluations field");
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
        return checkNumberData();
    }
    public void setFitnessOnTable(Fitness fitness){

        idLb.setText(String.valueOf(fitness.getId()));
        costCb.setSelectedIndex(fitness.getCost());
        diffCb.setSelectedIndex(fitness.getDiff());
        financialCb.setSelectedIndex(fitness.getFinancialImpact());
        methodCb.setSelectedIndex(fitness.getMethod());
        priorityCb.setSelectedIndex(fitness.getPriority());
        projectsCb.setSelectedItem(fitness.getProjects().getName());
        riskCb.setSelectedIndex(fitness.getRisk());
        riskLevelCb.setSelectedIndex(fitness.getRiskLevel());
        timeCb.setSelectedIndex(fitness.getTime());
    }
    public Fitness getFitnessFromTable(){
        Fitness fitness = new FitnessBuilder()
                .addId(Integer.valueOf(idLb.getText()))
                .addProjects(projects.get(projectsCb.getSelectedIndex()))
                .addRisk(riskCb.getSelectedIndex())
                .addMethod(methodCb.getSelectedIndex())
                .addFinancialImpact(financialCb.getSelectedIndex())
                .addRiskLevel(riskLevelCb.getSelectedIndex())
                .addCost(costCb.getSelectedIndex())
                .addDiff(diffCb.getSelectedIndex())
                .addPriority(priorityCb.getSelectedIndex())
                .addTime(timeCb.getSelectedIndex())
                .build();
        return fitness;
    }
    public void updateResultTable(Results result){
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        ArrayList<Long> adaptiveFunction = result.getAdaptiveFunction();
        ArrayList<Long> cost = result.getCost();
        ArrayList<Long> comparativeValue = result.getComparativeValue();
        ArrayList<Long> time = result.getTime();
        
        for (int i = 0; i < result.getAdaptiveFunction().size(); i++) {
            model.addRow(new Object[] {
                i+1, adaptiveFunction.get(i), cost.get(i), comparativeValue.get(i)
            });
        }
    }
    public void updateTable(){
        DefaultTableModel model = (DefaultTableModel) fitnessTable.getModel();
        model.setRowCount(0);
        fitnesses = FitnessController.index();
        risks = RiskController.index();
        for (int i = 0; i < fitnesses.size(); i++) {
            Fitness tempFitness = (Fitness) fitnesses.get(i);
            model.addRow(new Object[] {
                tempFitness.getId(), 
                tempFitness.getRisk(), 
                tempFitness.getMethod(), 
                tempFitness.getFinancialImpact(), 
                tempFitness.getRiskLevel(), 
                tempFitness.getCost(), 
                tempFitness.getDiff(), 
                tempFitness.getPriority(), 
                tempFitness.getTime(), 
                tempFitness.getProjects().getName()
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fitnessTable = new javax.swing.JTable();
        newBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        riskCb = new javax.swing.JComboBox<>();
        financialCb = new javax.swing.JComboBox<>();
        methodCb = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        riskLevelCb = new javax.swing.JComboBox<>();
        priorityCb = new javax.swing.JComboBox<>();
        diffCb = new javax.swing.JComboBox<>();
        costCb = new javax.swing.JComboBox<>();
        timeCb = new javax.swing.JComboBox<>();
        projectsCb = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        idLb = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        runBtn = new javax.swing.JButton();
        fitnessesCb = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        projectsExecutionCb = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        algorithmsCb = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        noEvaluationField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        jPanel1.setPreferredSize(new java.awt.Dimension(755, 528));
        jPanel1.setRequestFocusEnabled(false);

        fitnessTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Risk", "Response", "Financial Impact", "Risk Level", "Cost", "Difficulty", "Priority", "Time", "Project"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        fitnessTable.setShowGrid(false);
        fitnessTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fitnessTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(fitnessTable);

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

        String[] riskFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            riskFitnessArr[i] = String.valueOf(i);
        }
        riskCb.setModel(new javax.swing.DefaultComboBoxModel<>(riskFitnessArr));
        riskCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riskCbActionPerformed(evt);
            }
        });

        String[] financialFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            financialFitnessArr[i] = String.valueOf(i);
        }
        financialCb.setModel(new javax.swing.DefaultComboBoxModel<>(financialFitnessArr));

        String[] methodFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            methodFitnessArr[i] = String.valueOf(i);
        }
        methodCb.setModel(new javax.swing.DefaultComboBoxModel<>(methodFitnessArr));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel2.setText("Configurations:");

        String[] riskLevelFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            riskLevelFitnessArr[i] = String.valueOf(i);
        }
        riskLevelCb.setModel(new javax.swing.DefaultComboBoxModel<>(riskLevelFitnessArr));

        String[] priorityFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            priorityFitnessArr[i] = String.valueOf(i);
        }
        priorityCb.setModel(new javax.swing.DefaultComboBoxModel<>(priorityFitnessArr));

        String[] diffFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            diffFitnessArr[i] = String.valueOf(i);
        }
        diffCb.setModel(new javax.swing.DefaultComboBoxModel<>(diffFitnessArr));

        String[] costFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            costFitnessArr[i] = String.valueOf(i);
        }
        costCb.setModel(new javax.swing.DefaultComboBoxModel<>(costFitnessArr));

        String[] timeFitnessArr = new String[101];
        for (int i = 0; i < 101; i++) {
            timeFitnessArr[i] = String.valueOf(i);
        }
        timeCb.setModel(new javax.swing.DefaultComboBoxModel<>(timeFitnessArr));

        projects = ProjectController.index();
        String[] projectArray = new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {
            projectArray[i] = projects.get(i).getName();
        }
        String[] tempArray = new String[]{"",""};
        projectsCb.setModel(new javax.swing.DefaultComboBoxModel<>(projectArray));

        jLabel1.setText("ID");

        jLabel3.setText("Risk");

        jLabel4.setText("Project");

        jLabel5.setText("Method");

        jLabel6.setText("Impact");

        jLabel7.setText("RiskLevel");

        jLabel8.setText("Cost");

        jLabel9.setText("Difficulty");

        jLabel10.setText("Priority");

        jLabel11.setText("Time");

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Adaptive Function", "Cost", "Comparative Value"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        resultTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(resultTable);

        runBtn.setText("Run");
        runBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                runBtnMouseReleased(evt);
            }
        });

        fitnesses = FitnessController.index();
        String[] fitnessArr = new String[fitnesses.size()];
        for (int i = 0; i < fitnesses.size(); i++) {
            fitnessArr[i] = String.valueOf(fitnesses.get(i).getId());
        }
        String[] tempArray1 = new String[]{"",""};
        fitnessesCb.setModel(new javax.swing.DefaultComboBoxModel<>(fitnessArr));

        jLabel12.setText("Project:");

        jLabel13.setText("Configuration:");

        projects = ProjectController.index();
        String[] projectArray2 = new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {
            projectArray2[i] = projects.get(i).getName();
        }
        String[] tempArray2 = new String[]{"",""};
        projectsExecutionCb.setModel(new javax.swing.DefaultComboBoxModel<>(projectArray2));

        jLabel14.setText("Algorithm:");

        algorithmsCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NSGAII", "DENSEA", "FastPGA", "PESA2", "SPEA2" }));

        jLabel15.setText("Number of evaluations:");

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel16.setText("Parameters of solutions:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(algorithmsCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(runBtn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(projectsExecutionCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(fitnessesCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(noEvaluationField, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(newBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(updateBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteBtn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(riskCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(methodCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(financialCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(riskLevelCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(idLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(costCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(diffCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(priorityCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timeCb, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 170, Short.MAX_VALUE))
                                    .addComponent(projectsCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fitnessesCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(noEvaluationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(projectsExecutionCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(runBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(algorithmsCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(financialCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(riskLevelCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(methodCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(riskCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(idLb, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(costCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diffCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(priorityCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(timeCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(projectsCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    private void fitnessTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fitnessTableMouseClicked
        // TODO add your handling code here:

        int row = fitnessTable.rowAtPoint(evt.getPoint());
        DefaultTableModel model = (DefaultTableModel) fitnessTable.getModel();
        int id = (int) model.getValueAt(row, 0);
        Fitness fitness = (Fitness) FitnessController.show(id);
        setFitnessOnTable(fitness);
        editStatus();
    }//GEN-LAST:event_fitnessTableMouseClicked

    private void newBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseReleased
        // TODO add your handling code here:
        Fitness fitness = new FitnessBuilder()
                .addProjects(projects.get(projectsCb.getSelectedIndex()))
                .addRisk(riskCb.getSelectedIndex())
                .addMethod(methodCb.getSelectedIndex())
                .addFinancialImpact(financialCb.getSelectedIndex())
                .addRiskLevel(riskLevelCb.getSelectedIndex())
                .addCost(costCb.getSelectedIndex())
                .addDiff(diffCb.getSelectedIndex())
                .addPriority(priorityCb.getSelectedIndex())
                .addTime(timeCb.getSelectedIndex())
                .build();
        FitnessController.create(fitness);
        updateTable();
    }//GEN-LAST:event_newBtnMouseReleased

    private void updateBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseReleased
        // TODO add your handling code here:
        FitnessController.update(getFitnessFromTable());
        updateTable();
    }//GEN-LAST:event_updateBtnMouseReleased

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseReleased
        // TODO add your handling code here:
        FitnessController.delete(Integer.valueOf(idLb.getText()));
        updateTable();
        resetFields();
    }//GEN-LAST:event_deleteBtnMouseReleased

    private void clearBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBtnMouseReleased
        // TODO add your handling code here:
        resetFields();
    }//GEN-LAST:event_clearBtnMouseReleased

    private void riskCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riskCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_riskCbActionPerformed

    private void runBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_runBtnMouseReleased
        // TODO add your handling code here:
        risk.RiskSolver riskSolver = new risk.RiskSolver();
        risk.RiskSolver.session = Main.factory.openSession();
        if (validateData()){
            results = riskSolver.evaluate(
                    projects.get(projectsExecutionCb.getSelectedIndex()).getId(), 
                    fitnesses.get(fitnessesCb.getSelectedIndex()).getId(), 
                    (String) algorithmsCb.getSelectedItem(), 
                    Integer.valueOf(noEvaluationField.getText()));
            updateResultTable(results);
        }
    }//GEN-LAST:event_runBtnMouseReleased

    private void resultTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultTableMouseClicked
        // TODO add your handling code here:
        if (!results.getAdaptiveFunction().isEmpty()) {
            int row = resultTable.rowAtPoint(evt.getPoint());
            
            // Show solution detail
            int[] methodChoice = results.getMethodChoice().get(row);
            int noRisks = methodChoice.length;
            JFrame frame = new JFrame("Solution detail");
            String message = "The risk codes and corresponding response codes:\n";
            for (int i = 0; i < noRisks - 1; i++) {
                if ((i + 1) % 5 == 0) {
                    message += "\n";
                }
                message += risks.get(i).getCode() + " - " + methodChoice[i] + ", ";
            }
            message += risks.get(noRisks - 1).getCode() + " - " + methodChoice[noRisks - 1];
            JOptionPane.showMessageDialog(frame,
                    message,
                    "Solution detail",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_resultTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> algorithmsCb;
    private javax.swing.JButton clearBtn;
    private javax.swing.JComboBox<String> costCb;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JComboBox<String> diffCb;
    private javax.swing.JComboBox<String> financialCb;
    private javax.swing.JTable fitnessTable;
    private javax.swing.JComboBox<String> fitnessesCb;
    private javax.swing.JLabel idLb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox<String> methodCb;
    private javax.swing.JButton newBtn;
    private javax.swing.JTextField noEvaluationField;
    private javax.swing.JComboBox<String> priorityCb;
    private javax.swing.JComboBox<String> projectsCb;
    private javax.swing.JComboBox<String> projectsExecutionCb;
    private javax.swing.JTable resultTable;
    private javax.swing.JComboBox<String> riskCb;
    private javax.swing.JComboBox<String> riskLevelCb;
    private javax.swing.JButton runBtn;
    private javax.swing.JComboBox<String> timeCb;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
