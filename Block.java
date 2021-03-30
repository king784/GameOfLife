import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

// Blocks for the grid
public class Block extends JPanel
{
    public int x;
    public int y;
    public int gridX;
    public int gridY;
    public int size;
    public Color col;
    public int active;

    public Block(int newX, int newY, int newSize, Color newCol, int newActive, int newGridX, int newGridY)
    {
        x = newX;
        y = newY;
        size = newSize;
        col = newCol;
        active = newActive;
        gridX = newGridX;
        gridY = newGridY;
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if(active == 1){
                    active = 0;
                }else{
                    active = 1;
                }
            }
        });
    }

    public Block()
    {
        x = 0;
        y = 0;
        gridX = 0;
        gridY = 0;
        size = 0;
        col = new Color(0, 0, 0);
        active = 0;
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