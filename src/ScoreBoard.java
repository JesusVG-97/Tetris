/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20902426a
 */
public class ScoreBoard extends javax.swing.JPanel {

    /**
     * Creates new form ScoreBoard
     */
    private int score;
    public ScoreBoard() {
        initComponents();
        score = 0;
       jLabel.setText("Score: 0");
    }
    public void incrementScore(){
        score++;
        jLabel.setText("Score: " + score);
    }
     public void reset() {
        score = 0;
        display();
    }
    public void display() {
        jLabel.setText("Score: " + score);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel = new javax.swing.JLabel();

        jLabel.setText("jLabel1");
        add(jLabel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel;
    // End of variables declaration//GEN-END:variables
}
