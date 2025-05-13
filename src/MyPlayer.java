import java.awt.*;

public class MyPlayer {
    public Chip[][] gameBoard;
    public int[] columns;

    public MyPlayer() {
        columns = new int[10];

        /***
         * This code will run just once, when the game opens.
         * Add your code here.
         */


        for (int i=0; i<4; i++){
            for (int x=0; x<4; x++){
                for (int y=0; y<4; y++){
                    if(x<=i && y<=x && i!=0){
                        System.out.println(i +""+ x +""+ y);
                    }
                }
            }
        }

        int a = 2;
        int b = 2;
        int c = 2;
        System.out.println();

        for (int x=a; x<4; x++){
            for (int y=b; y<4; y++){
                for (int z=c; z<4; z++){
                    if(y<=x && z<=y && x!=0){
                        System.out.println(x +""+ y +""+ z);
                    }
                }
            }
        }


        //array list:
        //for (Item x : inventory){
        //  x.printInfo();
        //}

    }

    public Point move(Chip[][] pBoard) {

        System.out.println("MyPlayer Move");

        gameBoard = pBoard;
        int column = 0;
        int row = 0;

        row = 1;
        column = 1;

        /***
         * This code will run each time the "MyPlayer" button is pressed.
         * Add your code to return the row and the column of the chip you want to take.
         * You'll be returning a data type called Point which consists of two integers.
         */

        Point myMove = new Point(row, column);
        return myMove;
    }

}
