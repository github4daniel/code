/*
 * Copyright (c) 2020 Promontory Interfinancial Network, LLC
 */
package com.sk.until;

import java.util.Map;

public class QueryMap
{
	private static final String DEFAULT_SUFFIX = "sql.xml";

	private final Map<String, String> queries;

	public QueryMap(
		Class<?> targetClass)
	{
		String modifiedClassName = targetClass.getName();
		queries = sqlLoader(modifiedClassName, DEFAULT_SUFFIX);
	}

	public static String classNameToPath(String className, String suffix)
	{
		return String.format(
			"/%s.%s",
			className.replace(".", "/").replace("$", "_"),
			suffix);
	}

	private Map<String, String> sqlLoader(
		String className,
		String suffix)
	{
		return QueryLoader.instance().load(classNameToPath(className, suffix));
	}

	public String get(String key)
	{
		if (!queries.containsKey(key))
		{
			throw new IllegalArgumentException(String.format(
				"Query named \"%s\" not found",
				key));
		}
		return queries.get(key);
	}
}