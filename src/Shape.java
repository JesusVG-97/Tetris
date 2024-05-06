
import java.awt.Graphics;
import java.util.Random;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20902426a
 */
public class Shape {
    private Tetrominoes pieceShape;
    private int[][] coords;
    public static final int [][][] coordsTable = {
        {{ 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 }},
        {{ 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 }},
        {{ 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 }},
        {{ 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 }}, 
        {{ -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 }}, 
        {{ 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 }},  
        {{ -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 }}, 
        {{ 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 }}
    };
    public Shape(Tetrominoes t){
        pieceShape = t;
        coords = new int [4][2];
        for (int i = 0; i <= 3; i++){
            for (int xy = 0; xy <=1; xy++){
                coords[i][xy] = coordsTable[pieceShape.ordinal()][i][xy];
            }
        }
        
        
    }
    public Shape(){
        getRandomShape();
        
    }
    public void getRandomShape(){
        Random random = new Random();
        int r = random.nextInt(7)+1;
        //(int) (Math.random()*7)+1;
        pieceShape = Tetrominoes.values()[r];
        coords = new int [4][2];
        for (int i = 0; i <= 3; i++){
            for (int xy = 0; xy <=1; xy++){
                coords[i][xy] = coordsTable[pieceShape.ordinal()][i][xy];
            }
        }
    }
    private void setX(int point, int x){
        coords[point][0]= x;
    }
    
    private void setY(int point, int y){
        coords[point][1] = y;
    }
    public int getX(int point){
        return coords[point][0];
    }
     public int getY(int point){
        return coords[point][1];
    }
    public Tetrominoes getShape(){
        return pieceShape;
    }
    public void setRandomShape() {       
        pieceShape = new Shape().getShape();
        coords = coordsTable[pieceShape.ordinal()];
    }
    public int minX(){
        int minX = getX(0);
        for (int i = 0; i < 4; i++){
          if (minX > getX(i)){
            minX = getX(i);
        }
    }
       return minX;
    }
     public Shape rotateRight(){
         Shape rotatedShape = new Shape(pieceShape);
         
         if (pieceShape == Tetrominoes.SquareShape){
             return rotatedShape;
         }
            for (int i = 0; i <= 3;i++){
                int x = getX(i);
                int y = getY(i);
                rotatedShape.setX(i, y);
                rotatedShape.setY(i, -x);
            }
            return rotatedShape;
        }
    public int minY(){
        int minY = getY(0);
        for (int i = 0; i < 4; i++){
          if (minY > getY(i)){
            minY = getY(i);
        }
    }
       return minY;
    }
     public int maxX() {
        int maxX = getX(0);
        for (int i = 1; i < coords.length; i++) {
            if (getX(i) > maxX) {
                maxX = getX(i);
            } 
        }
        return maxX;
    }
    public int maxY() {
        int maxY = getY(0);
        for (int i = 1; i < coords.length; i++) {
            if (getY(i) > maxY) {
                maxY = getY(i);
            } 
        }
        return maxY;
    }
     public static Shape getRandom() {
        return new Shape();
    }
      public void draw(Graphics g, int row, int col, int squareWidth, int squareHeight) {
        for (int point = 0; point <= 3; point++) {
            Util.drawSquare(g, row + coords[point][1],
                    col + coords[point][0], pieceShape ,
                    squareWidth, squareHeight);
        }
    }
}
