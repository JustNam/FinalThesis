/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.entity;

import java.util.ArrayList;

/**
 *
 * @author nguyennam
 */
public class Results {
     private ArrayList<Long> adaptiveFunction;
     private ArrayList<Long> cost;
     private ArrayList<Long> comparativeValue;
     private ArrayList<Long> time;
     private ArrayList<int[]> methodChoice;

     
     public Results(ArrayList<Long> adaptiveFunction, ArrayList<Long> cost, ArrayList<Long> comparativeValue, ArrayList<Long> time, ArrayList<int[]> methodChoice){
         this.adaptiveFunction = adaptiveFunction;
         this.cost = cost;
         this.comparativeValue = comparativeValue;
         this.time = time;
         this.methodChoice = methodChoice;
    }
     
    public ArrayList<int[]> getMethodChoice() {
        return methodChoice;
    }

    public void setMethodChoice(ArrayList<int[]> methodChoice) {
        this.methodChoice = methodChoice;
    }

    public ArrayList<Long> getAdaptiveFunction() {
        return adaptiveFunction;
    }

    public void setAdaptiveFunction(ArrayList<Long> adaptiveFunction) {
        this.adaptiveFunction = adaptiveFunction;
    }

    public ArrayList<Long> getCost() {
        return cost;
    }

    public void setCost(ArrayList<Long> cost) {
        this.cost = cost;
    }

    public ArrayList<Long> getComparativeValue() {
        return comparativeValue;
    }

    public void setComparativeValue(ArrayList<Long> comparativeValue) {
        this.comparativeValue = comparativeValue;
    }

    public ArrayList<Long> getTime() {
        return time;
    }

    public void setTime(ArrayList<Long> time) {
        this.time = time;
    }
     
     
}
