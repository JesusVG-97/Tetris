
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20902426a
 */
public class Board extends JPanel { 
    private  MyKeyAdapter keyAdapter;
    private Timer timer;
    private int deltaTime;
     public static final Color[] COLORS = {
            new Color(24, 12, 22), 
            new Color(204, 102, 102), 
            new Color(102, 204, 102), 
            new Color(102, 102, 204), 
            new Color(204, 204, 102), 
            new Color(204, 102, 204), 
            new Color(102, 204, 204), 
            new Color(218, 170, 0)};
    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    private static final int INITAL_ROW = -2;
    private Tetrominoes[][] board;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;    
    public ScoreBoard scoreboard;
    private boolean isStarted = false;
    private boolean isPaused = false;
    public JDialog jDialog;
    public JDialog jDialog2;
    public void setScoreBoard(ScoreBoard scoreBoard){
        this.scoreboard = scoreBoard;
    }
    public Board(){
      super();
      board = new Tetrominoes[NUM_ROWS][NUM_COLS];
      for (int row = 0; row < NUM_ROWS; row++){
          for (int col = 0; col < NUM_COLS; col++){
              board [row][col] = Tetrominoes.NoShape;
          }
        }
      deltaTime = 1000;
      currentShape = new Shape();
      currentRow = INITAL_ROW;
      currentCol = NUM_COLS /2;
       
      timer = new Timer (deltaTime, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent arg0) {
              mainLoop();
          }
      });
      
      keyAdapter = new MyKeyAdapter();
      addKeyListener(keyAdapter);
      setFocusable(true);
      
  
    }
     public void mainLoop(){
            moveDown();       
        }
     
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintBoard(g2d);
        paintShape(g2d);
    }
    private void paintBoard(Graphics2D g2d){
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                drawSquare(g2d, row, col, board[row][col]);
            }
        }
    }
    private void paintShape(Graphics2D g2d){
        
        for (int i = 0; i < 4; i++){
           
             drawSquare(g2d, currentRow + currentShape.getY(i), currentCol + currentShape.getX(i), currentShape.getShape());
             
            
        }
       
    }
    private int squareWidth(){
        return getWidth() / NUM_COLS;
    }
    private int squareHeight(){
        return getHeight() / NUM_ROWS;
    }
    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = COLORS[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    } 

   

   
    class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
           switch (e.getKeyCode()){
               case KeyEvent.VK_LEFT:
                   if(canMove(currentShape, currentCol - 1)){
                       moveLeft();
                   }
                   break;
                case KeyEvent.VK_RIGHT:  
                    if(canMove(currentShape, currentCol + 1)){
                       moveRight();
                    }
                   break;
                case KeyEvent.VK_UP: 
                    rotateCurrentShape();
                  
                   break;
                case KeyEvent.VK_DOWN: 
                    moveDown();
                   break;
                default: 
                    break;
                
            } 
            repaint();
        }       

        
    }              
        private void moveLeft() {
         currentCol --;
        }
        private void moveRight(){
            currentCol++;
        }
              
        private void moveDown() {
            if (!collisions(currentRow+1)){
                  currentRow++;
            } else {           
                makeCollision();               
                detetectFullLine();               
            }
            repaint();
            
        }
        private void detetectFullLine() {
            int counter;
            for(int row = 0; row < NUM_ROWS; row ++){
                counter = 0;
                for (int col = 0; col < NUM_COLS; col++){
                    if (board[row][col] != Tetrominoes.NoShape){
                        counter++;
                    }
                }
                if (counter == NUM_COLS){
                    //System.out.println("Full line " + row);
                    deleteLine(row);
                    scoreboard.incrementScore();
                }
            }
        }
        private void deleteLine(int rowToDelete) {
            for (int row = rowToDelete; row > 1; row--){
                for (int col = 0; col < NUM_COLS; col++){
                    board[row][col] = board[row-1][col];
                }
            }
            for (int col = 0; col < NUM_COLS;col++){
                board[0][col] = Tetrominoes.NoShape;
            }
        }
        private boolean canMove(Shape shape, int newCol){
            if (newCol + shape.minX() < 0){
                return false;
            }
            if (newCol + shape.maxX() > NUM_COLS - 1){
                return false;
            }
            for (int i =0; i <= 3; i++){
                int row = currentRow + shape.getY(i);
                int col = newCol + shape.getX(i);
                if (row >= 0){
                    if (board[row][col] != Tetrominoes.NoShape){
                        return false;
                    }
                }
            }
           return true;
        }
        private boolean collisions(int newRow) {
            if (newRow + currentShape.maxY() >= NUM_ROWS){
              return true;
            } else {
                for (int i = 0; i <= 3; i++){
                    int row = newRow + currentShape.getY(i);
                    int col = currentCol + currentShape.getX(i);
                    if (row >=0){
                        if (board[row][col] != Tetrominoes.NoShape){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
          private void rotateCurrentShape() {
            Shape rotatedShape = currentShape.rotateRight();
            if (canMove(rotatedShape, currentCol)){
                currentShape = rotatedShape;
                
            }
          }
          private void makeCollision() {
            if (!movePieceToBoard()) {
                makeGameOver();
                
            } else {
                currentShape = new Shape();
                currentRow = INITAL_ROW;
                currentCol = NUM_COLS/2;
            }
    }
         private boolean movePieceToBoard() {
            int x = 0;
            int y = 0;
            for(int i = 0; i <= 3; i++){
                x = currentRow + currentShape.getY(i);
                y = currentCol + currentShape.getX(i);
                if (x < 0 ){
                    return false;
                } else {
                    board[x][y] = currentShape.getShape();
                }
            }
            return true;
         }
         private void makeGameOver() {
             timer.stop();
             removeKeyListener(keyAdapter);
             
        }    
       
        
    public void removeLine(int lineToRemove) {
        for (int row = lineToRemove; row > 0; row--) {
            for (int col = 0; col < NUM_COLS; col++) {
                board[row][col] = board[row - 1][col];
            }
        }
        for (int col = 0; col < NUM_COLS; col++) {
            board[0][col] = Tetrominoes.NoShape;
        }
    }
    public void setScoreboard(ScoreBoard scoreboard) {
        this.scoreboard = scoreboard;
    }
  
     public void initGame() {
        initValues();
        scoreboard.reset();
        currentShape.getRandomShape();
        removeKeyListener(keyAdapter);
        addKeyListener(keyAdapter);
        timer.start();
    }
    public void initValues() {      
        setFocusable(true);
        cleanBoard();        
        currentShape = new Shape();
        currentRow = INITAL_ROW;
        currentCol = NUM_COLS / 2;
        
    }
    public void cleanBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                board[row][col] = Tetrominoes.NoShape;
            }
        }
    }
    public void start(){
        isStarted = true;
        cleanBoard();
        initGame();
    }
    public void pause(){
        if (timer.isRunning()){
            timer.stop();
            
        } else {
            timer.start();
            
        }
    }
    public void deltaTime(int deltaTime){
       
        switch (deltaTime){
            case 1: 
                timer.setDelay(1000); 
                break;
            case 2: 
                timer.setDelay(500);
                break;
            case 3:
                timer.setDelay(250);
                break;
            default:
                break;
        }
    }
    public void gameOver(){
     
        jDialog2.setVisible(true);
      }
    
}


