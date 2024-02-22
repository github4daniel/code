CREATE TABLE IF NOT EXISTS configuration
  (
     cancel_percent_rate DOUBLE DEFAULT NULL,
     inverse_buy         TINYINT(1) NOT NULL,
     arr_rates           VARCHAR(80) NOT NULL
  );
  
CREATE TABLE IF NOT EXISTS daily_position
  (
     symbol   VARCHAR(10) NOT NULL,
     quantity INT(11) NOT NULL,
     accttype VARCHAR(20) NOT NULL,
     PRIMARY KEY (symbol, accttype)
  );
  
CREATE TABLE IF NOT EXISTS tranche
  (
     accttype VARCHAR(20) NOT NULL,
     symbol   VARCHAR(20) NOT NULL,
     code     VARCHAR(20) NOT NULL,
     amt      DOUBLE NOT NULL
  );
  
CREATE TABLE IF NOT EXISTS initial_position
  (
     symbol   VARCHAR(10) NOT NULL,
     accttype VARCHAR(20) NOT NULL,
     quantity INT(11) NOT NULL,
     PRIMARY KEY (symbol, accttype)
  );
  
CREATE TABLE IF NOT EXISTS operation
  (
     symbol    VARCHAR(10) NOT NULL,
     buyprice  DOUBLE NOT NULL,
     cashamt   DOUBLE NOT NULL,
     accttype  VARCHAR(20) NOT NULL,
     rank 	   INT(11) NOT NULL,
     PRIMARY KEY (symbol, buyprice, accttype)
  );

CREATE TABLE IF NOT EXISTS stock_account
  (
     acctid      VARCHAR(20) DEFAULT NULL,
     acctidkey   VARCHAR(50) DEFAULT NULL,
     accttype    VARCHAR(20) DEFAULT NULL,
     description VARCHAR(30) DEFAULT NULL
  );
  
CREATE TABLE IF NOT EXISTS close_price
  (
     symbol    VARCHAR(10) NOT NULL,
	 close_price  DOUBLE NOT NULL
  );