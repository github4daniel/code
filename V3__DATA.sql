INSERT INTO configuration (cancel_percent_rate, sell_percent_rate, arr_rates) VALUES
	(0.008, 0.07, '[0.01, 0.02, 0.03]');
	

INSERT INTO tranche (accttype, symbol, code, amt) VALUES
	('INDIVIDUAL', 'SQQQ', 'T253501', 30000),
	('INDIVIDUAL', 'TQQQ', 'T111501', 12000),
	('IRA', 'SQQQ', 'T253501', 30000),
	('IRA', 'TQQQ', 'T111501', 12000),
	('INDIVIDUAL', 'TQQQ', 'T172001', 18000),
	('IRA', 'TQQQ', 'T172001', 18000);

INSERT INTO `tranche` (`accttype`, `symbol`, `code`, `amt`) VALUES
	('INDIVIDUAL', 'SQQQ', 'T-13-1102', 10000),
	('IRA', 'SQQQ', 'T-13-1102', 15000),
	('INDIVIDUAL', 'SQQQ', 'T060702', 10000),
	('IRA', 'SQQQ', 'T060702', 20000);
