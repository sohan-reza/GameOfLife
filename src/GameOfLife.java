
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Scanner;

public class GameOfLife{
    private boolean[] isClickedArray;
    private static boolean isPlay = false;

    private static int square = 40;
    private static int cols=15;
    public GameOfLife() {

    }

    public void draw() throws InterruptedException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Game Of Life");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600, 630);
        jFrame.setLocationRelativeTo(null);



        isClickedArray = new boolean[105];



        JPanel panel = new JPanel() {
            int startX = 0;
            int startY = 0;


            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < isClickedArray.length; i++) {
                    if((i%15) == 0) {
                        int row=i/cols;
                        startX = 0;
                        startY = square*row;
                    }


                    g.setColor(Color.BLACK);
                    g.drawRect(startX + (i % cols) * square, startY, square, square);


                    if (isClickedArray[i]) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }


                    g.fillRect(startX + (i%cols * square )+1, startY+1, square-1, square-1);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = ((e.getX()/square))+((e.getY()/square)*cols);

                if (index >= 0 && index < isClickedArray.length) {
                    isClickedArray[index] = !isClickedArray[index];
                    panel.repaint();
                }
            }
        });


        //Menubar panel
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.GRAY);
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton startButton = new JButton("Start");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isPlay) {
                    startButton.setText("Start");
                }else{
                    startButton.setText("Stop");
                }
                isPlay = !isPlay;

            }
        });
        startButton.setFocusable(false);
        topBar.add(startButton);



        //add component's to  the frame
        jFrame.add(topBar, BorderLayout.NORTH);
        jFrame.add(panel);
        jFrame.setVisible(true);


        while(true) {
            Thread.sleep(500);
            //for(int i=0; i<10; i++) {
            // int a = new Scanner(System.in).nextInt();
            if(isPlay) {
                isPlay = !isPlay;
                isClickedArray = play(isClickedArray);
                panel.repaint();
            }
        }
        //  }

    }

    public static boolean[] play(boolean[] board) {
        boolean[] tmp = new boolean[board.length];
        Arrays.fill(tmp, false);

        for(int i=0; i<board.length; i++) {


            int[][] directions = new int[][] {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};

            int center = i;
            int right = center+1;
            int left = center-1;

            int top = i-cols;
            int top_left = top-1;
            int top_right = top+1;

            int bottom = i+cols;
            int bottom_left = bottom-1;
            int bottom_right = bottom+1;

//                System.out.println(top_left+" "+top+" "+top_right);
//                System.out.println(left+" "+center+" "+right);
//                System.out.println(bottom_left+" "+bottom+" "+bottom_right);

            int currentRow = (int) Math.floor((double) i/15);
            int currentCol = i%15;

            if((currentRow>0 && currentRow<(board.length/cols)-1) && (currentCol > 0 && currentCol < cols-1)) {
                directions[0][0] = i-cols-1;
                directions[0][1] = i-cols;
                directions[0][2] = i-cols+1;

                directions[1][0] = i-1;
                //directions[1][1] = i;
                directions[1][2] = i+1;

                directions[2][0] = i+cols-1;
                directions[2][1] = i+cols;
                directions[2][2] = i+cols+1;
            }else {
                if(currentRow == 0 && currentCol == 0) {
                    //left-top
                    //directions[1][1] = i;
                    directions[1][2] = i+1;
                    directions[2][1] = i+cols;
                    directions[2][2] = i+cols+1;
                }else if(currentRow == 0 && currentCol == cols-1) {
                    //right top
                    //directions[1][1] = i;
                    directions[1][0] = i-1;
                    directions[2][0] = i+cols;
                    directions[2][1] = i+cols-1;
                }else if(currentRow == (board.length/cols)-1 && currentCol == 0) {
                    //left bottom
                    //directions[1][1] = i;
                    directions[1][2] = i+1;
                    directions[0][1] = i-cols;
                    directions[0][2] = i-cols+1;
                }else if(currentRow == (board.length/cols)-1 && currentCol == cols-1) {
                    //right bottom
                    //directions[1][1] = i;
                    directions[1][0] = i-1;
                    directions[0][1] = i-cols;
                    directions[0][0] = i-cols-1;
                }else{
                    if(currentRow==0) {
                        directions[1][0] = i-1;
                        //directions[1][1] = i;
                        directions[1][2] = i+1;

                        directions[2][0] = i+cols-1;
                        directions[2][1] = i+cols;
                        directions[2][2] = i+cols+1;
                    }

                    if(currentRow==(board.length/cols)-1) {
                        directions[0][0] = i-cols-1;
                        directions[0][1] = i-cols;
                        directions[0][2] = i-cols+1;

                        directions[1][0] = i-1;
                        //directions[1][1] = i;
                        directions[1][2] = i+1;
                    }

                    if(currentCol==0) {

                        directions[0][1] = i-cols;
                        directions[0][2] = i-cols+1;


                        //directions[1][1] = i;
                        directions[1][2] = i+1;


                        directions[2][1] = i+cols;
                        directions[2][2] = i+cols+1;
                    }

                    if(currentCol==cols-1) {
                        directions[0][0] = i-cols-1;
                        directions[0][1] = i-cols;


                        directions[1][0] = i-1;
                        //directions[1][1] = i;


                        directions[2][0] = i+cols-1;
                        directions[2][1] = i+cols;

                    }
                }
            }

            int liveCellCount=0;
            for(int dx=0; dx<3; dx++) {
                for(int dy=0; dy<3; dy++) {
                    if(directions[dx][dy] > -1) {
                        if (board[directions[dx][dy]]) {
                            liveCellCount++;
                        }
                    }
                }
            }

            //Any live cell less than two live neighbour will die in next generation
            //Any live cell with more than three live neighbours dies, as if by overpopulation.

            //Any live cell with two or three live neighbours lives on to the next generation.
            //Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

            if(board[i] && (liveCellCount < 2 || liveCellCount > 3 )) {
                System.out.println(i + " will die");
                tmp[i] = false;
            }else if(board[i] && (liveCellCount == 2 || liveCellCount == 3)) {
                tmp[i] = true;
            }else if(!board[i] && liveCellCount == 3) {
                //live in next gen
                System.out.println(i + " will live");
                tmp[i] = true;
            }

        }

        return  tmp;
    }

    public static void main(String[] args) throws InterruptedException {
        GameOfLife obj = new GameOfLife();
        obj.draw();


    }
}

