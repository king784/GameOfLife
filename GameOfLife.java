import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

import java.util.Random;

public class GameOfLife
{
    static Random rand = new Random();

    // Grid stuff
    static int width = 10;
    static int height = 10;
    static int blockSize = 40;
    static Block[][] grid = new Block[width][height];
    static Block[][] next;

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
                sum += grid[x+i][y+j].active;
            }
        }
        sum -= grid[x][y].active;
        return sum;
    }

    // http://www.java-gaming.org/index.php?topic=24220.0
    public static void gameLoop()
    {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 2;
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
        // Count neighbours
        int sum = 0;
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(i == 0 || i == width - 1 || j == 0 || j == height - 1)
                {
                    next[i][j] = grid[i][j];
                }
                else
                {

                    int neighbours = countNeighbours(grid, i, j);
                    int state = grid[i][j].active;

                    if(state == 0 && neighbours == 3)
                    {
                        next[i][j].active = 1;
                    }
                    else if(state == 1 && (neighbours < 2 || neighbours > 3))
                    {
                        next[i][j].active = 0;
                    }
                    else
                    {
                        next[i][j].active = state;
                    }
                }
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

        frame.setBounds(50, 50, 700, 700);
        
        panel.setLayout(null);
        //Container cont = frame.getContentPane();
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(rand.nextInt(2) == 0)
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
        next = grid;

        frame.add(panel);
        frame.setVisible(true);

        gameLoop();
    }
}