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
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt) {
                System.out.println(evt.getPoint());
                if(active == 1){
                    active = 0;
                }else{
                    active = 1;
                }
            }
        });
        setFocusable(true);
    }

    public Block()
    {
        x = 0;
        y = 0;
        size = 0;
        col = new Color(0, 0, 0);
        active = 0;
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt) {
                System.out.println(evt.getPoint());
                if(active == 1){
                    active = 0;
                }else{
                    active = 1;
                }
            }
        });
        setFocusable(true);
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