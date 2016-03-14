package de.domisum.jsonformatter.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSelector
{
	
	// CONSTANTS
	private static final String FILE_EXTENSION = ".json";
	
	// PROPERTIES
	private File directory;
	
	
	// -------
	// CONSTRUCTOR
	// -------
	public FileSelector(File directory)
	{
		this.directory = directory;
	}
	
	
	// -------
	// GETTERS
	// -------
	public List<File> getFiles()
	{
		List<File> files = getFiles(directory);
		System.out.println();
		
		return files;
	}
	
	private List<File> getFiles(File parent)
	{
		System.out.println("Searching '" + parent.getAbsolutePath() + "' for files...");
		List<File> files = new ArrayList<File>();
		
		for(File file : parent.listFiles())
		{
			if(file.isFile())
			{
				if(!file.getAbsolutePath().endsWith(FILE_EXTENSION))
				{
					System.out.println("Skipped file '" + "': The file suffix does not fit");
					continue;
				}
				
				System.out.println("Found file '" + file.getAbsolutePath() + "'");
				files.add(file);
			}
			else if(file.isDirectory())
				files.addAll(getFiles(file));
		}
		
		return files;
	}
	
}
