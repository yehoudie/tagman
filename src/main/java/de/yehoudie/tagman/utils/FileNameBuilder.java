/**
 * 
 */
package de.yehoudie.tagman.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.tagman.types.DataFormatParameter;
import de.yehoudie.utils.files.FileUtil;

/**
 * @author yehoudie
 *
 */
public class FileNameBuilder
{

	public static String buildNewFileName(String format_string, TagData tag_data)
	{
		int format_string_size = format_string.length();
		StringBuilder file_name = new StringBuilder();
		
		for ( int i = 0; i < format_string_size; i++)
		{
			char c = format_string.charAt(i);
			if ( c == DataFormatParameter.PREFIX && i < format_string_size-1 )
			{
				char next_c = format_string.charAt(i+1);
				DataFormatParameter param = DataFormatParameter.forChar(next_c);
				if ( param != null )
				{
					if ( param == DataFormatParameter.ALBUM )
					{
						file_name.append(tag_data.getAlbum() );
					}
					else if ( param == DataFormatParameter.INTERPRET )
					{
						file_name.append(tag_data.getInterpret() );
					}
					else if ( param == DataFormatParameter.TITLE )
					{
						file_name.append(tag_data.getTitle() );
					}
					else if ( param == DataFormatParameter.TRACK_NUMBER )
					{
						if ( tag_data.getTitleNumber() < 10 ) file_name.append(0);
						file_name.append(tag_data.getTitleNumber());
					}
					i++;
				}
			}
			else
			{
				file_name.append(c);
			}
			
		}
		
		return file_name.toString();
	}

	public static HashMap<DataFormatParameter, String> getFileNameData(String format_string, String file_name)
	{
		File file = new File(file_name);
		file_name = FileUtil.getName(file);

		int format_string_size = format_string.length();
		ArrayList<String> parts = new ArrayList<>();
		StringBuilder part = new StringBuilder();
		
		for ( int i = 0; i < format_string_size; i++)
		{
			char c = format_string.charAt(i);
			
			if ( c == DataFormatParameter.PREFIX && i < format_string_size-1 )
			{
				char next_c = format_string.charAt(i+1);
				DataFormatParameter param = DataFormatParameter.forChar(next_c);
				if ( param != null )
				{
					if ( parts.size() > 0 && part.length()==0)
					{
						throw new WrongFormatException("The format parameters are not properly separated.");
					}
					parts.add(part.toString());
					parts.add(param.toString());
					
					part.setLength(0);
					i++;
				}
			}
			else
			{	
				part.append(c);
			}
		}
		
		if ( parts.size() > 0 )
		{
			return parse(file_name, parts);
		}
		
		return null;
	}

	private static HashMap<DataFormatParameter, String> parse(String file_name, ArrayList<String> parts)
	{
		int start_i = 0;
		int end_i = 0;
		int parts_size = parts.size();
		HashMap<DataFormatParameter, String> values = new HashMap<>();
		
		for ( int i = 0; i < parts_size; i++ )
		{
			String part = parts.get(i);
			
			DataFormatParameter param = DataFormatParameter.forString(part);
			if ( param != null )
			{
				start_i = end_i;
				if ( i < parts_size-1 )
				{
					String next_part = parts.get(i+1);
					end_i = file_name.indexOf(next_part, start_i);
				}
				else
				{
					end_i = file_name.length();
				}
				String value = file_name.substring(start_i, end_i);
				values.put(param, value);
			}
			else
			{
				start_i = file_name.indexOf(part, end_i);
				end_i = start_i + part.length();
			}
		}
		
		return values;
	}
}
