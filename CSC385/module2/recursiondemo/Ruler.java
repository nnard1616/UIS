import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;

public class Ruler extends Frame
{
    private int theSize;
    
    
    public Ruler(int size) {
    	addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		theSize = size;
		
		setSize( theSize + 20, 200 );
        show( );
    }
    

    public void paint( Graphics g )
    {
        drawRuler( g, 10, theSize - 1 + 10, 8 );
    }

    private void drawRuler( Graphics g, int left, int right, int level)
    {
        if( level < 1 )
            return;
        
        int mid = ( left + right ) / 2;

		// Uncomment this section to see the drawing in slow motion 
        try {
        	Thread.sleep(100);
        }
        catch(InterruptedException e) { }

        g.drawLine( mid, 80, mid, 80 - level * 5 );

        drawRuler( g, left, mid - 1, level- 1 );
        drawRuler( g, mid + 1, right, level - 1 );
    }
}