package com.sk.until;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.IntStream;

import org.jdbi.v3.core.ConnectionException;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.SqlObjects;

public class DBUtil {

	private String dbUrl;
	private String userName;
	private String password;

	public DBUtil(String dbUrl, String userName, String password) {
		this.dbUrl = dbUrl;
		this.userName = userName;
		this.password = password;
	}

	public Jdbi getJbdi() {
		return Jdbi.create(this::retryConnection).installPlugin(new SqlObjectPlugin())
				.configure(
						SqlObjects.class,
						c -> c.setSqlLocator(new QueryLoaderLocator()));
	}

	public Connection retryConnection() {
		return IntStream.range(0, 5).mapToObj(attempt -> {
			Connection connection = null;
			try {
				connection = DriverManager.getConnection(dbUrl, userName, password);
			} catch (SQLException e) {
				System.err.println(e);
			}
			return connection;
		}).filter(Objects::nonNull).findFirst().orElseThrow(() -> {
			return new ConnectionException(
					new SQLException("Could not establish DB connection after multiple retries"));
		});
	}

}
