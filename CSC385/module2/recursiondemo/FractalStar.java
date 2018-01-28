import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class FractalStar extends Frame
{
    private int theSize;
    
    public FractalStar(int size) {
    	addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		theSize = size;
		
		setSize( theSize + 20, theSize + 40 );
        show( );
    }

    public void paint( Graphics g )
    {
        setBackground( Color.gray );
        g.setColor( Color.white );
        drawSpace( g, theSize / 2 + 10, theSize / 2 + 30, theSize );
    }

    private void drawSpace( Graphics g, int xCenter, int yCenter, int boundingDim )
    {
        int side = boundingDim / 2;

        if( side < 1 )
            return;
        
        int left =   xCenter - side / 2;
        int top =    yCenter - side / 2;
        int right =  xCenter + side / 2;
        int bottom = yCenter + side / 2;

        drawSpace( g, left, top, boundingDim / 2 );
        drawSpace( g, left, bottom, boundingDim / 2 );
        drawSpace( g, right, top, boundingDim / 2 ); 
        drawSpace( g, right, bottom, boundingDim / 2 );

		// Uncomment this section to see the drawing in slow motion 

        try {
        	Thread.sleep( side * 1 );
        }
        catch(InterruptedException ie ) {
        	ie.printStackTrace();
        }
 
        g.fillRect( left, top, right - left, bottom - top );
    }

    // Simple test program
    // For simplicity, must terminate from console
    /*
    public static void main( String [ ] args )
    {
        Frame f = new FractalStar( );
        f.setSize( theSize + 20, theSize + 40 );
        f.show( );
    }
    */
}