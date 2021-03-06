package de.domisum.jsonformatter;

import java.io.File;

import de.domisum.jsonformatter.file.FileSelector;
import de.domisum.jsonformatter.formatter.JsonFileFormatter;

public class JsonFormatter
{
	
	// -------
	// CONSTRUCTOR
	// -------
	public JsonFormatter(String[] args)
	{
		if(args.length == 0)
		{
			System.err.println("Invalid arguments: Use the file or directory paths of the files to be formatted");
			return;
		}
		
		for(String arg : args)
		{
			System.out.println("Formatting '" + arg + "' ...");
			File file = new File(arg);
			if(file.isFile())
				formatFile(file);
			else if(file.isDirectory())
				formatDirectory(file);
			
			System.out.println("Formatting '" + arg + "' done \n\n\n");
		}
		
		System.out.println("Done");
	}
	
	public static void main(String[] args)
	{
		new JsonFormatter(args);
	}
	
	
	// -------
	// FORMATTING
	// -------
	private void formatFile(File file)
	{
		new JsonFileFormatter(file);
	}
	
	private void formatDirectory(File file)
	{
		FileSelector fileSelector = new FileSelector(file);
		
		for(File f : fileSelector.getFiles())
			new JsonFileFormatter(f);
	}
	
}
