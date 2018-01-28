import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.util.stream.Stream;


/**
 * DirectoryLister class.
 * This class allows the user to recursively display the contents of a
 * selected directory in the file system.
 */
public class DirectoryLister
{
	
	// -----------------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------------
	
	/** GUI used to display results */
	private GUI gui; 
	
	/** base path of directory to be traversed */
	private String basePath;

	
	// -----------------------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------------------
	
	/**
	 *	Create a new DirectoryLister that uses the specified GUI.
	 */
	public DirectoryLister(GUI gui)
	{
		this.gui = gui;
	}
	
	
	// -----------------------------------------------------------------------
	// Methods
	// -----------------------------------------------------------------------
	
	/**
	 *	Allow user to select a directory for traversal.
	 */
	public void selectDirectory()
	{
		// clear results of any previous traversal
		gui.resetGUI();
		
		// allow user to select a directory from the file system
		basePath = gui.getAbsoluteDirectoryPath();
		
		// update the address label on the GUI
		gui.setAddressLabelText(basePath);
		
		// traverse the selected directory, and display the contents
		showDirectoryContents(basePath);
	}
	
	
	/**
	 *	Show the directory listing.
	 *	An error message is displayed if basePath does not represent a valid directory.
	 * 
	 *	@param	basePath		the absolute path of a directory in the file system.
	 */
	public void showDirectoryContents(String basePath)
	{
            //create File object for base case.
            File baseFile = new File(basePath);
            
            
            
            
            enumerateDirectory(baseFile);
	}
	
	
	/**
	 *	Recursive method to enumerate the contents of a directory.
	 *
	 *	@param	f	directory to enumerate
	 */
	private void enumerateDirectory(File f)
	{
            //Add current directory to gui table
            gui.updateListing(f.getAbsolutePath(), 
                              getSizeString(f.getTotalSpace()), 
                              "Folder", 
                              formattedDateString(f.lastModified()));
            
            //obtain children File/Folder array
            File[] children = f.listFiles();
            
            //create file stream because I don't feel like using for loops.
            Stream<File> fileStream   = Stream.of(children)
                                              .sorted()
                                              .filter(n -> n.isFile());
            
            //create folder stream because I want to learn more about streams/lamdas.
            Stream<File> folderStream = Stream.of(children)
                                              .sorted()
                                              .filter(n -> n.isDirectory());
            
            //funcitonal for loop to recurse through Folders
            folderStream.forEach(n -> enumerateDirectory(n));
            
            //functional for loop to add current Folder's Files to gui table
            fileStream.forEach(n -> gui.updateListing(n.getAbsolutePath(), 
                                                      getSizeString(n.length()), 
                                                      "File", 
                                                      formattedDateString(n.lastModified())));
	}
        
	
	
	/**
	 *	Convert a size from bytes to kilobytes, rounded down, and return an appropriate descriptive string.
	 *	Example: 123456 bytes returns 120 KB
	 *
	 *	@param size		size, in bytes
	 *	@return			size, in kilobytes (rounded down) + "KB"
	 */
	private String getSizeString(long size)
	{
		long kbSize = size / 1024;
		
		return "" + kbSize + " KB";
	}
	
	
	/**
	 *	Return a numeric time value as a formatted date string.
	 *
	 *	@param		time	numeric time value in milliseconds
	 *	@return		formatted string using the format "MM/dd/yyyy hh:mm:ss aaa"
	 *				Example: 01/15/2010 12:37:52 PM
	 */
	private String formattedDateString(long time)
	{
		// create Date object from numeric time
		Date d = new Date(time);
		
		// create formatter
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa");

		// return formatted date string
		return sdf.format(d);
	}
}