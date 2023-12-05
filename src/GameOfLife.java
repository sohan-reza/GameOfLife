
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Scanner;

public class GameOfLife{

    private static ImageIcon playIcon =  new ImageIcon("icon/play.png");
    private static ImageIcon pauseIcon =  new ImageIcon("icon/pause.png");
    private boolean[] isClickedArray;
    private static boolean isPlay = false;

    private static int window_width = 700;
    private static int window_height = 740;
    private static int square = 10;
    private static int cols =  20; //(window_width/square);
    private static int rows = (window_height/square);
    public GameOfLife() {

    }

    public void draw() throws InterruptedException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Game Of Life");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(window_width, window_height);
        jFrame.setIconImage(new ImageIcon("icon/icon.png").getImage());
        jFrame.setLocationRelativeTo(null);

        //120 extra
        isClickedArray = new boolean[(rows*cols)];


        JPanel panel = new JPanel() {
            int startX = 0;
            int startY = 0;


            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < isClickedArray.length; i++) {
                    if((i%cols) == 0) {
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
                JOptionPane.showMessageDialog(panel, index);
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

        JButton startButton = new JButton();
        startButton.setIcon(new ImageIcon("icon/play.png"));
        startButton.setContentAreaFilled(false);
        startButton.setPreferredSize(new Dimension(50,30));
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        JButton stepButton = new JButton();
        stepButton.setIcon(new ImageIcon("icon/forward.png"));
        stepButton.setContentAreaFilled(false);
        stepButton.setPreferredSize(new Dimension(50,30));
        stepButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton zoomButton = new JButton();
        zoomButton.setIcon(new ImageIcon("icon/zoom-in.png"));
        zoomButton.setContentAreaFilled(false);
        zoomButton.setPreferredSize(new Dimension(50,30));
        zoomButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton zoomOutButton = new JButton();
        zoomOutButton.setIcon(new ImageIcon("icon/zoom-out.png"));
        zoomOutButton.setContentAreaFilled(false);
        zoomOutButton.setPreferredSize(new Dimension(50,30));
        zoomOutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        zoomOutButton.setEnabled(false);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        stepButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                stepButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                stepButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        zoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                zoomButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                zoomButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });

        zoomOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                zoomOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                zoomOutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });




        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isPlay) {
                    startButton.setIcon(playIcon);
                    stepButton.setEnabled(true);

                    if(square>10 && square<30) {
                        zoomButton.setEnabled(true);
                        zoomOutButton.setEnabled(true);
                    }else if(square==10) {
                        zoomButton.setEnabled(true);
                    }else if(square==30) {
                        zoomOutButton.setEnabled(true);
                    }



                }else{
                    startButton.setIcon(pauseIcon);
                    stepButton.setEnabled(false);
                    zoomButton.setEnabled(false);
                    zoomOutButton.setEnabled(false);
                }
                isPlay = !isPlay;
            }
        });

        //zoom-in the grid on click
        zoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                square += 5;

                if(square==30) {
                    zoomButton.setEnabled(false);
                }

                if(square>10) {
                    zoomOutButton.setEnabled(true);
                }

                panel.repaint();
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                square -=5;
                if(square==10) {
                    zoomOutButton.setEnabled(false);
                }

                if(square<30) {
                    zoomButton.setEnabled(true);
                }

                panel.repaint();
            }
        });

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isClickedArray = play(isClickedArray);
                panel.repaint();
            }
        });

        startButton.setFocusable(false);
        stepButton.setFocusable(false);
        zoomButton.setFocusable(false);
        zoomOutButton.setFocusable(false);


        topBar.add(startButton);
        topBar.add(stepButton);
        topBar.add(zoomButton);
        topBar.add(zoomOutButton);

        jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension windowSize = jFrame.getSize();
                cols = windowSize.width/square + 5;
                rows = windowSize.height/square + 5;
                isClickedArray = new boolean[(rows*cols)];



                panel.revalidate();
            }
        });


        //add component's to  the frame
        jFrame.add(topBar, BorderLayout.NORTH);
        jFrame.add(panel);
        jFrame.setVisible(true);


//        while(true) {
//            Thread.sleep(500);
//            //for(int i=0; i<10; i++) {
//            // int a = new Scanner(System.in).nextInt();
//            if(isPlay) {
//                isPlay = !isPlay;
//                isClickedArray = play(isClickedArray);
//                panel.repaint();
//            }
//        }
        //  }

    }

    public static boolean[] play(boolean[] board) {
        boolean[] tmp = new boolean[board.length];
        Arrays.fill(tmp, false);

        for(int i=0; i<board.length; i++) {


            int[][] directions = new int[][] {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};


            int currentRow = (int) Math.floor((double) i/cols);
            int currentCol = i%cols;

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
                tmp[i] = false;
            }else if(board[i] && (liveCellCount == 2 || liveCellCount == 3)) {
                tmp[i] = true;
            }else if(!board[i] && liveCellCount == 3) {
                //live in next gen
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

