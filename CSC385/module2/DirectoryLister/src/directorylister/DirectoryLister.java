import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.util.stream.Stream;
import javax.swing.JOptionPane;

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
            try{
                if(basePath == null)
                    throw new Exception("You did not choose a directory,\nso I'm not going to do anything.");
                
                //create File object for base case.
                File baseFile = new File(basePath);
                
                //scan through base case File and add its contents to the table.
                enumerateDirectory(baseFile);
                
            }catch(NullPointerException npe){
                JOptionPane.showMessageDialog(null, 
                                              "The specified filename is invalid.", 
                                              "Error", 
                                              JOptionPane.ERROR_MESSAGE);
            }catch(Exception e){ 
                JOptionPane.showMessageDialog(null, 
                                              e.getMessage(), 
                                              "FYI", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
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
                              getSizeString(f.length()), // can alternatively use method folderSize, defined below.
                              "Folder", 
                              formattedDateString(f.lastModified()));
            
            //obtain children File/Folder array
            File[] children = f.listFiles();
            
            //create file stream 
            Stream<File> fileStream   = Stream.of(children)
                                              .sorted()
                                              .filter(n -> n.isFile());
            
            //create folder stream 
            Stream<File> folderStream = Stream.of(children)
                                              .sorted()
                                              .filter(n -> n.isDirectory());
            
            //recurse through Folders
            folderStream.forEach(n -> enumerateDirectory(n));
            
            //add current Folder's Files to gui table
            fileStream.forEach(n -> gui.updateListing(n.getAbsolutePath(), 
                                                      getSizeString(n.length()), 
                                                      "File", 
                                                      formattedDateString(n.lastModified())));
	}
        
        // BONUS
        /**
	 *	Recursive method to calculate the size of a folder as a sum of
	 *      the sizes of the files contained within the folder and sub-folders.
         * 
	 *	@param	f	folder to compute size of.
         *      @return         folder size in bytes.
	 */
        private long folderSize(File f){
            long sum = 0;
            
            //if f is a file
            if (f.isFile())
                
                //return file size
                return f.length();
            
            //else f is a folder
            else { 
                
                //iterate through folder contents
                for (File ff : f.listFiles()){
                    
                    //add content size to working sum
                    sum += folderSize(ff);
                }
                
                //return total folder size (as a sum of its file sizes)
                return sum;
            }
        }
	
	
	/**
	 *	Convert a size from bytes to kibibytes, rounded down, and return an appropriate descriptive string.
	 *	Example: 123456 bytes returns 120 KiB
	 *
	 *	@param size		size, in bytes
	 *	@return			size, in kibibytes (rounded down) + "KiB"
	 */
	private String getSizeString(long size)
	{
		long kibSize = size / 1024;
		
		return "" + kibSize + " KiB";
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