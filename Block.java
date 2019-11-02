import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

// Blocks for the grid
public class Block extends JPanel
{
    public int x;
    public int y;
    public int size;
    public Color col;
    public int active;

    public Block(int newX, int newY, int newSize, Color newCol, int newActive)
    {
        x = newX;
        y = newY;
        size = newSize;
        col = newCol;
        active = newActive;
    }

    public void Draw(Graphics g)
    {
        if(this.active == 1)
        {
            g.setColor(this.col);
        }
        else
        {
            Color black = new Color(0, 0, 0);
            g.setColor(black);
        }
        
        g.fillRect(this.x, this.y, this.size, this.size);
        
    }
}