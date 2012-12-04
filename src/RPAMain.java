import javax.swing.JFrame;


// -------------------------------------------------------------------------
/**
 *  Shows the GUI for the Remote PC Access tool.
 *
 *  @author Ben Katz (bakatz)
 *  @version 2010.10.22
 */
public class RPAMain
{

    // ----------------------------------------------------------
    /**
     * Creates the GUI for the remote pc access tool.
     * @param args command line arguments, not used
     */
    public static void main( String[] args )
    {
        JFrame frame = new JFrame( "Remote PC Access Tool by Ben Katz" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add( new RPAPanel() );
        frame.pack();
        frame.setVisible( true );
    }

}
