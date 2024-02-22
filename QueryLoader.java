/*
 * Copyright (c) 2020 Promontory Interfinancial Network, LLC
 */
package com.sk.until;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * <code>QueryLoader</code> is a registry for sets of queries so
 * that multiple copies of the same queries aren't loaded into memory.
 * This implementation loads properties files filled with query name to
 * SQL mappings.  This class is thread safe.
 */
public class QueryLoader
{

	/**
	 * The Singleton instance of this class.
	 */
	private static final QueryLoader instance = new QueryLoader();

	/**
	 * Matches .xml file extensions.
	 */
	private static final Pattern dotXml = Pattern.compile(".+\\.[xX][mM][lL]");

	/**
	 * Return an instance of this class.
	 *
	 * @return The Singleton instance.
	 */
	public static QueryLoader instance()
	{
		return instance;
	}

	/**
	 * Maps query set names to Maps of their queries.
	 */
	private final Map<String, Map<String, String>> queries = new HashMap<>();

	/**
	 * QueryLoader constructor.
	 */
	protected QueryLoader()
	{
		super();
	}

	/**
	 * Loads a Map of query names to SQL values.  The Maps are cached so a
	 * subsequent request to load queries from the same path will return
	 * the cached Map.  The properties file to load can be in either
	 * line-oriented or XML format.  XML formatted properties files must use a
	 * <code>.xml</code> file extension.
	 *
	 * @param path The path that the ClassLoader will use to find the file.
	 *             This is <strong>not</strong> a file system path.  If you had a jarred
	 *             Queries.properties file in the com.yourcorp.app.jdbc package you would
	 *             pass "/com/yourcorp/app/jdbc/Queries.properties" to this method.
	 * @return Map of query names to SQL values
	 * @throws IOException                                if a file access error occurs
	 * @throws IllegalArgumentException                   if the ClassLoader can't find a file at
	 *                                                    the given path.
	 * @throws java.util.InvalidPropertiesFormatException if the XML properties file is
	 *                                                    invalid
	 * @see java.util.Properties
	 */
	public synchronized Map<String, String> load(String path)
	{
		return this.queries.computeIfAbsent(path, this::loadQueries);
	}

	/**
	 * Loads a set of named queries into a Map object.  This implementation
	 * reads a properties file at the given path.  The properties file can be
	 * in either line-oriented or XML format.  XML formatted properties files
	 * must use a <code>.xml</code> file extension.
	 *
	 * @param path The path that the ClassLoader will use to find the file.
	 * @return Map of query names to SQL values
	 * @throws IOException                                if a file access error occurs
	 * @throws IllegalArgumentException                   if the ClassLoader can't find a file at
	 *                                                    the given path.
	 * @throws java.util.InvalidPropertiesFormatException if the XML properties file is
	 *                                                    invalid
	 * @see java.util.Properties
	 * @since DbUtils 1.1
	 */
	protected Map<String, String> loadQueries(String path)
	{
		// Findbugs flags getClass().getResource as a bad practice; maybe we should change the API?
		InputStream in = getClass().getResourceAsStream(path);

		if (in == null)
		{
			throw new IllegalArgumentException(path + " not found.");
		}

		Properties props = new Properties();
		try
		{
			if (dotXml.matcher(path).matches())
			{
				props.loadFromXML(in);
			}
			else
			{
				props.load(in);
			}
		}catch (IOException ex)
		{

		}
		finally
		{
			try
			{
				in.close();
			}catch (IOException ex)
			{

			}
		}

		// Copy to HashMap for better performance

		@SuppressWarnings({
			"rawtypes",
			"unchecked"}) // load() always creates <String,String> entries
			HashMap<String, String> hashMap = new HashMap(props);
		return hashMap;
	}

	/**
	 * Removes the queries for the given path from the cache.
	 *
	 * @param path The path that the queries were loaded from.
	 */
	public synchronized void unload(String path)
	{
		this.queries.remove(path);
	}
}