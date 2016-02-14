package io.github.domisum.jsonformatter.formatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JsonFileFormatter
{
	
	// CONSTANTS
	private static final int SPACES_PER_DEPTH = 0;
	private static final int TABS_PER_DEPTH = 1;
	
	// PROPERTIES
	private File file;
	
	// STATUS
	private String json;
	private String error = null;
	
	private int depth = 0;
	
	
	// -------
	// CONSTRUCTOR
	// -------
	public JsonFileFormatter(File file)
	{
		this.file = file;
		
		System.out.println("Formatting file '" + file.getAbsolutePath() + "'...");
		
		loadFile();
		formatJson();
		saveFile();
	}
	
	
	// -------
	// FORMATTING
	// -------
	public void loadFile()
	{
		try
		{
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\\Z");
			
			if(!scanner.hasNext())
			{
				scanner.close();
				return;
			}
			
			json = scanner.next();
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		if(json == null)
			error = "The file could not be loaded";
	}
	
	private void formatJson()
	{
		if(error != null)
			return;
			
		// clean up everything
		json = json.replace("\n", "");
		
		boolean quotationOpen = false;
		for(int i = 0; i < json.length(); i++)
		{
			char c = json.charAt(i);
			char cBefore = '\u0000';
			if(i > 0)
				cBefore = json.charAt(i - 1);
				
			if(c == '"' && cBefore != '\\')
				quotationOpen = !quotationOpen;
			else if(c == ' ' || c == '\u0009') // space and tab
			{
				if(quotationOpen)
					continue;
					
				json = new StringBuilder(json).deleteCharAt(i).toString();
				i--;
			}
		}
		
		char lastC = '\u0000';
		for(int i = 0; i < json.length(); i++)
		{
			char c = json.charAt(i);
			char nextC = '\u0000';
			if(i + 1 < json.length())
				nextC = json.charAt(i + 1);
				
			if(c == '{' || c == '[')
			{
				if(lastC == ' ')
				{
					String newLine = getNewLine();
					json = addString(json, i - 1, newLine);
					i += newLine.length();
				}
				
				depth++;
				json = addString(json, i, getNewLine());
			}
			else if(c == '}' || c == ']')
			{
				depth--;
				String newLine = getNewLine();
				json = addString(json, i - 1, newLine);
				i += newLine.length();
			}
			else if(c == ':')
				json = addString(json, i, " ");
			else if(c == ',')
			{
				if(nextC != '{' && nextC != '[')
					json = addString(json, i, getNewLine());
				else
					json = addString(json, i, " ");
			}
			
			lastC = c;
		}
	}
	
	private void saveFile()
	{
		if(error != null)
		{
			System.out.println("An error occured while processing this file: " + error);
			return;
		}
		
		try
		{
			FileWriter fw = new FileWriter(file);
			fw.write(json);
			fw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	// -------
	// STRING
	// -------
	private static String addString(String base, int afterIndex, String toAdd)
	{
		return base.substring(0, afterIndex + 1) + toAdd + base.substring(afterIndex + 1);
	}
	
	private static String repeat(String string, int times)
	{
		String repeated = "";
		
		for(int i = 0; i < times; i++)
			repeated += string;
			
		return repeated;
	}
	
	
	private String getNewLine()
	{
		return "\n" + repeat(" ", SPACES_PER_DEPTH * depth) + repeat("\u0009", TABS_PER_DEPTH * depth);
	}
	
}
