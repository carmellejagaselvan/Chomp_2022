//Chomp Version 2.1

//Complete rewrite to change the board to a 2 dimensional array
//Kendall Chun 3/21/2021
//Graphics update 9/30/22, Charlotte Killiam

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.ArrayList;


//Keyboard and Mouse
import java.awt.event.*;

//*******************************************************************************
// Class Definition Section

public class Chomp implements Runnable, MouseListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 800;

    final int xOffset = 200;
    final int yOffset = 100;
    final int chipWidth = 50;
    final int chipBorder = 4;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public JPanel buttons;

    public BufferStrategy bufferStrategy;

    public Chip[][] board;
    public boolean gameOver = false;

    //sounds
    public SoundFile chipTaken;
    public SoundFile youLose;

    //buttons
    public JButton newGame, threeBoard, randomBoard, computerPlayer, myChomp;

    //players
    public RandomPlayer randomPlayer;
    public MyPlayer aiPlayer;
    public ArrayList<Board> LoserBoards = new ArrayList<Board>();


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        Chomp myApp = new Chomp();   //creates a new instance of the game
        new Thread(myApp).start();                 //creates a threads & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public Chomp() {

        LoserBoards.add(new Board(1,0,0));
        System.out.println(LoserBoards.get(0).boards[0]);


        setUpGraphics();

        board = new Chip[10][10];
        for (int r = 0; r < board[0].length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = new Chip(r, c, xOffset, yOffset, chipWidth);
            }
        }

        //sounds
        // chipTaken = new SoundFile("takeChip.wav");
        youLose = new SoundFile("sound1.wav");

        //players
        randomPlayer = new RandomPlayer();
        aiPlayer = new MyPlayer();

        for (int a=0; a<4; a++){
            for (int b=0; b<4; b++){
                for (int c=0; c<4; c++){
                    if(b<=a && c<=b && a!=0){
                        oneMove(a,b,c);
                    }
                }
            }
        }
        printLosingBoards();

        //oneMove(1,1,1);


    }//

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    public void updateBoard(int rowParameter, int columnParameter) {
        int row = rowParameter;
        int col = columnParameter;

        if (row >= 0 && col >= 0 && board[row][col].isAlive) {
            //chip is alive
            //fix board
            for (int r = 0; r < board[0].length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (r >= row && c >= col) {
                        if (row == 0 && col == 0 && !gameOver) {
                            gameOver = true;
                            youLose.play();
                        }
                        board[r][c].isAlive = false;
                    }
                }
            }
        }
    }

    public void oneMove(int a, int b, int c){
        System.out.println("Boards in one move: ");
        boolean isLosingBoard = true;
        if (c>0){
            for (int i=1; i<=c; i++){
                System.out.println("Given board: " + a + "" + b + "" + c);
                System.out.println("Board in one move: " + a +""+ b +""+ (c-i));
                for(int f=0; f<LoserBoards.size(); f++) {
                    if (a == LoserBoards.get(f).boards[0] && b == LoserBoards.get(f).boards[1] && (c - i) == LoserBoards.get(f).boards[2]) {
                        //boolean turn on
                        isLosingBoard = false;
                        System.out.println("hooray!");
                        System.out.println("(3,"+(a+1-i)+")");

                    }
                }
            }
        }
        if (b>0){
            for (int i=1; i<4; i++){
                if ((b-i)>c && (b-i)>=0){
                    System.out.println("Given board: " + a + "" + b + "" + c);
                    System.out.println("Board in one move: " + a +""+ (b-i) +""+ c);
                    for(int f=0; f<LoserBoards.size(); f++) {
                        if (a == LoserBoards.get(f).boards[0] && (b - i) == LoserBoards.get(f).boards[1] && c == LoserBoards.get(f).boards[2]) {
                            isLosingBoard = false;
                            System.out.println("hooray!");
                            System.out.println("(2,"+(a+1-i)+")");

                        }
                    }
                }
                if ((b-i)<=c && (b-i)>=0){
                    System.out.println("Given board: " + a + "" + b + "" + c);
                    System.out.println("Board in one move: " + a +""+ (b-i) +""+ (b-i));
                    for(int f=0; f<LoserBoards.size(); f++) {
                        if (a == LoserBoards.get(f).boards[0] && (b - i) == LoserBoards.get(f).boards[1] && (b - i) == LoserBoards.get(f).boards[2]) {
                            isLosingBoard = false;
                            System.out.println("hooray!");
                            System.out.println("(2,"+(a+1-i)+")");

                        }
                    }
                }
            }
        }

        if (a>0){
            for (int i=1; i<4; i++){
                if ((a-i)>c && (a-i)>b && (a-i)>=0) {
                    System.out.println("Given board: " + a + "" + b + "" + c);

                    System.out.println("Board in one move: " + (a - i) + "" + b + "" + c);
                    for (int f = 0; f < LoserBoards.size(); f++){
                        if ((a - i) == LoserBoards.get(f).boards[0] & b == LoserBoards.get(f).boards[1] & c == LoserBoards.get(f).boards[2]) {
                            isLosingBoard = false;

                            System.out.println("hooray!");
                            System.out.println("(1,"+(a+1-i)+")");


                        }
                    }

                }
                if ((a-i)>c && (a-i)<=b && (a-i)>=0){
                    System.out.println("Given board: " + a + "" + b + "" + c);

                    System.out.println("Board in one move: " + (a-i) + "" + (a-i) + "" + c);
                    for(int f=0; f<LoserBoards.size(); f++) {
                        if ((a - i) == LoserBoards.get(f).boards[0] & (a - i) == LoserBoards.get(f).boards[1] & c == LoserBoards.get(f).boards[2]) {
                            isLosingBoard = false;

                            System.out.println("hooray!");
                            System.out.println("(1,"+(a+1-i)+")");

                        }
                    }
                }
                if ((a-i)<=c && (a-i)<=b && (a-i)>=0){
                    System.out.println("Given board: " + a + "" + b + "" + c);

                    System.out.println("Board in one move: " + (a-i) + "" + (a-i) + "" + (a-i));
                    for(int f=0; f<LoserBoards.size(); f++) {
                        if ((a - i) == LoserBoards.get(f).boards[0] & (a - i) == LoserBoards.get(f).boards[1] & (a - i) == LoserBoards.get(f).boards[2]) {
                            isLosingBoard = false;
                            System.out.println("hooray!");
                            System.out.println("(1,"+(a+1-i)+")");

                        }
                    }
                }
            }
        }
        if (isLosingBoard == true){
            LoserBoards.add(new Board(a,b,c));
        }

    }

    public void printLosingBoards(){
        for(int f=0; f<LoserBoards.size(); f++){
            System.out.println(LoserBoards.get(f).boards[0] +""+ LoserBoards.get(f).boards[1]+ ""+LoserBoards.get(f).boards[2]);
        }
        System.out.println(LoserBoards.size());
    }



    public void run() {

        //for the moment we will loop things forever.
        while (true) {
            render();  // paint the graphics
            pause(50); // sleep for 10 ms
        }
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Put all your code for drawing things on the screen here
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Optima", Font.BOLD, 70));
        g.drawString("CHOMP", 320, 75);
        Color gray = Color.decode("#CED3DC");
        g.setColor(gray);
        g.fillRect(xOffset, yOffset, 500, 500);
        g.setColor(Color.BLACK);
        g.drawRect(xOffset, yOffset, 500, 500);

        //draw border
        g.setStroke(new BasicStroke(5));
        g.drawRect(xOffset - 10, yOffset - 10, 500 + 20, 500 + 20);

        if(!gameOver) {
            g.setStroke(new BasicStroke(2));
            //draw Grid
            for (int r = 0; r < board[0].length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    g.drawRect(board[r][c].xpos, board[r][c].ypos, chipWidth, chipWidth);
                }
            }

            //draw Chips
            for (int r = 0; r < board[0].length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c].isAlive) {
                        Color green = Color.decode("#5C946E");
                        g.setColor(green);
                        g.fillOval(board[r][c].xpos + chipBorder, board[r][c].ypos + chipBorder, chipWidth - 2 * chipBorder, chipWidth - 2 * chipBorder);
                        g.setColor(Color.BLACK);
                        g.drawOval(board[r][c].xpos + chipBorder, board[r][c].ypos + chipBorder, chipWidth - 2 * chipBorder, chipWidth - 2 * chipBorder);
                        //g.drawRect(board[r][c].xpos,board[r][c].ypos,chipWidth,chipWidth);
                        //g.drawString(r+"", board[r][c].xpos + chipBorder, board[r][c].ypos + chipBorder+40 );

                    }
                }
            }

            //draw poison chip
            if (board[0][0].isAlive) {
//                g.setColor(Color.BLUE);
                Color yellow = Color.decode("#FFDC5E");
                g.setColor(yellow);
                g.fillOval(board[0][0].xpos + chipBorder, board[0][0].ypos + chipBorder, chipWidth - 2 * chipBorder, chipWidth - 2 * chipBorder);
                g.setColor(Color.BLACK);
                g.drawOval(board[0][0].xpos + chipBorder, board[0][0].ypos + chipBorder, chipWidth - 2 * chipBorder, chipWidth - 2 * chipBorder);
                //g.drawRect(board[r][c].xpos,board[r][c].ypos,chipWidth,chipWidth);
            }
        }
        
        if (gameOver) {
            g.setColor(Color.blue);
            g.setFont(new Font("TimesRoman", Font.BOLD, 70));
            g.setFont(new Font("Optima", Font.BOLD, 70));
            g.drawString("GAME OVER ", 235, 300);

        }

        //leave these two lines of code as the last lines of the render( ) method
        g.dispose();
        bufferStrategy.show();
    }

    private void setUpGraphics() {
        frame = new JFrame("Chomp");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH - 100, HEIGHT - 100));  //sizes the JPanel
        panel.setLayout(new BorderLayout());   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addMouseListener(this);
        panel.add(canvas, BorderLayout.CENTER);  // adds the canvas to the panel.

        setupButtons();

        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,5));
        buttons.add(newGame);
        buttons.add(randomBoard);
        buttons.add(threeBoard);
        buttons.add(computerPlayer);
        buttons.add(myChomp);
        panel.add(buttons, BorderLayout.SOUTH);


        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(true);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
        frame.setLocationRelativeTo(null);

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    public void setupButtons(){
        //create buttons
        newGame = new JButton("New Game");
        newGame.setFont(new Font("Optima", Font.BOLD, 25));
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int r = 0; r < board[0].length; r++) {
                    for (int c = 0; c < board[0].length; c++) {
                        board[r][c].isAlive = true;
                    }
                }
                gameOver = false;
            }
        });


        //newGame.setPreferredSize(new Dimension(150,50));
        randomBoard = new JButton("Random Board");
        randomBoard.setFont(new Font("Optima", Font.BOLD, 25));
        randomBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //reset game
                for (int r = 0; r < board[0].length; r++) {
                    for (int c = 0; c < board[0].length; c++) {
                        board[r][c].isAlive = true;
                    }
                }
                gameOver = false;

                //generate random numbers and create new board
                int randomCol;
                int randomRow = (int) (Math.random() * 8 + 2);

                do {
                    randomCol = (int) (Math.random() * 8 + 2);
                } while (randomRow == randomCol);

                updateBoard(randomRow,0);
                updateBoard(0,randomCol);

            }
        });

        threeBoard = new JButton("3x3 Board");
        threeBoard.setFont(new Font("Optima", Font.BOLD, 25));
        threeBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //make 3x3
                //reset game
                for (int r = 0; r < board[0].length; r++) {
                    for (int c = 0; c < board[0].length; c++) {
                        board[r][c].isAlive = true;
                    }
                }
                gameOver = false;

                //make 3x3

                updateBoard(3,0);
                updateBoard(0,3);


             /*
                for (int r = 9; r >= 3; r--) {
                    for (int c = 0; c < board[0].length; c++) {
                        board[r][c].isAlive = false;
                    }
                }
                for (int c = 3; c < board[0].length; c++) {
                    for (int r = 7; r < 10; r++) {
                        board[r][c].isAlive = false;
                    }
                }

              */
            }
        });

        computerPlayer = new JButton("Random Player");
        computerPlayer.setFont(new Font("Optima", Font.BOLD, 25));
        computerPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    Point theMove = randomPlayer.move(board);
                    int row = (int) theMove.getX();
                    int col = (int) theMove.getY();

                    if (row >= 0 && col >= 0 && board[row][col].isAlive) {
                        updateBoard(row, col);
                    }

                }
            }
        });


        // randomBoard.setPreferredSize(new Dimension(200,50));
        myChomp = new JButton("My Player");
        myChomp.setFont(new Font("Optima", Font.BOLD, 25));
        myChomp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    Point theMove = aiPlayer.move(board);
                    int row = (int) theMove.getX();
                    int col = (int) theMove.getY();

                    if (row >= 0 && col >= 0 && board[row][col].isAlive) {
                        //chip is alive
                        //fix board
                        updateBoard(row, col);
                    }


                }
            }
        });
        //myChomp.setPreferredSize(new Dimension(150,50));

    }

    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int row = -1;
        int col = -1;

        for (int r = 0; r < board[0].length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].rec.contains(e.getX(), e.getY())) {
                    System.out.println("Row: " + r + "   Column: " + c);
                    row = r;
                    col = c;
                }
            }
        }

        if (row >= 0 && col >= 0 && board[row][col].isAlive) {
            //chip is alive
            //fix board
            updateBoard(row, col);

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}