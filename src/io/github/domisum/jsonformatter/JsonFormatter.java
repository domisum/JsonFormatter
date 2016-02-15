package io.github.domisum.jsonformatter;

import java.io.File;

import io.github.domisum.jsonformatter.file.FileSelector;
import io.github.domisum.jsonformatter.formatter.JsonFileFormatter;

public class JsonFormatter
{
	
	// -------
	// CONSTRUCTOR
	// -------
	public JsonFormatter(String[] args)
	{
		if(args.length != 1)
		{
			System.err.println("Invalid arguments: Use the file or directory path of the file(s) to be formatted");
			return;
		}
			
		File file = new File(args[0]);
		if(file.isFile())
			formatFile(file);
		else if(file.isDirectory())
			formatDirectory(file);
		
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
