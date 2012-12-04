import javax.swing.JTextArea;
import javax.swing.JScrollPane;


// -------------------------------------------------------------------------
/**
 *  A scrollable textArea representing the RPA log. contains information about emails received.
 *
 *  @author Ben Katz (bakatz)
 *  @version 2010.10.31
 */
public class RPALog
    extends JScrollPane
{
    /**
	 * Appeasing the compiler. This class won't be serialized anyway.
	 */
	private static final long serialVersionUID = 7849715121226840423L;
	private JTextArea textArea;
    // ----------------------------------------------------------
    /**
     * Create a new ChessGameLog object.
     */
    public RPALog()
    {
        super(new JTextArea("GUI initialized.", 5, 40),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.setAutoscrolls( true );
        textArea = ((JTextArea)this.getViewport().getView());
    }

    // ----------------------------------------------------------
    /**
     * Adds a new line of text to the log.
     * @param s the line of text to add
     */
    public void addToLog(String s)
    {
        textArea.setText( textArea.getText() + "\n" +
            new java.util.Date()+ " - " + s );
        textArea.setCaretPosition(textArea.getText().length()); //auto scroll
    }

    // ----------------------------------------------------------
    /**
     * Gets the most recent statement added to the log.
     * @return String the most recent log
     */
    public String getLastLog()
    {
        int indexOfLastNewLine = textArea.getText().lastIndexOf( "\n" );
        if(indexOfLastNewLine < 0)
        {
            return textArea.getText();
        }
        return textArea.getText().substring( indexOfLastNewLine );
    }
}
