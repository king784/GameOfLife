import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.util.Random;

public class GameOfLife
{
    static Random rand = new Random();

    // Grid stuff
    static int width = 60;
    static int height = 60;
    static int blockSize = 10;
    static Block[][] grid = new Block[width][height];
    // static Block[][] next;

    // Create grid blocks and add them to frame
    static JPanel panel = new JPanel()
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    grid[i][j].Draw(g);
                }
            }
        }
    };

    static boolean gameRunning = true;

    public static Color randomColor()
    {
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return new Color(r,g,b);
    }

    public void paint(Graphics g)
    {
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                grid[i][j].Draw(g);
            }
        }
    }

    public static int countNeighbours(Block[][] grid, int x, int y)
    {
        int sum = 0;
        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                int col = (x + i + width) % width;
                int row = (y + j + height) % height;
                sum += grid[col][row].active;
            }
        }
        sum -= grid[x][y].active;
        return sum;
    }

    // http://www.java-gaming.org/index.php?topic=24220.0
    public static void gameLoop()
    {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 10;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 

        while(gameRunning)
        {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
            
            // update the game logic
            Block[][] next = new Block[width][height];
            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    next[i][j] = new Block();
                    next[i][j].active = grid[i][j].active;
                }
            }
            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    int state = grid[i][j].active;
                    int neighbours = countNeighbours(grid, i, j);

                    if(state == 1 && neighbours < 2)
                    {
                        next[i][j].active = 0;
                    }
                    else if(state == 1 && neighbours > 3)
                    {
                        next[i][j].active = 0;
                    }
                    else if(state == 0 && neighbours == 3)
                    {
                        next[i][j].active = 1;
                    }
                    else
                    {
                        next[i][j].active = state;
                    }
                }
            }
            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    grid[i][j].active = next[i][j].active;
                }
            }
            
            // draw everyting
            panel.repaint();

            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give 
            // us our final value to wait for
            // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
            try
            {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rect = new Rectangle(screenSize.width / 2 - screenSize.width
            / 4, screenSize.height / 2 - screenSize.height / 4,
            screenSize.width / 2, screenSize.height / 2);
        //frame.setBounds(0, 0, width*blockSize, height*blockSize);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setResizable(false);
        frame.pack();
        frame.setLocation(rect.x, rect.y);

        //Container cont = frame.getContentPane();
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                // Percentage chance because nextDouble returns value between 0.0 and 1.0, so less than 0.1 means almost 10%
                if(rand.nextDouble() < 0.10)
                {
                    Block block = new Block((i+(i*blockSize)), j + (j*blockSize), blockSize, randomColor(), 1);
                    grid[i][j] = block;
                }
                else
                {
                    Block block = new Block((i+(i*blockSize)), j + (j*blockSize), blockSize, randomColor(), 0);
                    grid[i][j] = block;
                }
            }
        }

        frame.add(panel);
        frame.setVisible(true);

        gameLoop();
    }
}