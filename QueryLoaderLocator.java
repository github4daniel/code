/*
 * Copyright (c) 2020 Promontory Interfinancial Network, LLC
 */
package com.sk.until;

import java.lang.reflect.Method;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

public class QueryLoaderLocator implements SqlLocator
{
	@Override
	public String locate(
		Class<?> sqlObjectType,
		Method method,
		ConfigRegistry config)
	{
		QueryMap queries = new QueryMap(sqlObjectType);
		return queries.get(method.getName());
	}
}
