DROP TABLE STEP IF EXISTS;
DROP TABLE MANUAL IF EXISTS;
DROP TABLE ACCOUNT IF EXISTS ;
DROP TABLE IMAGE IF EXISTS;

CREATE TABLE IF NOT EXISTS ACCOUNT(
	account_id varchar(36) PRIMARY KEY, 
	account_name varchar(50),
	password varchar(255),
	email varchar(40) UNIQUE,
	roles varchar(50),
	introduction varchar(255),
	icon blob
);

CREATE TABLE IF NOT EXISTS MANUAL(
	manual_id varchar(36) PRIMARY KEY,
	title varchar(20),
	account_id varchar(36),
	FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id),
	thumbnail blob
);

CREATE TABLE IF NOT EXISTS STEP(
	manual_id varchar(36),
	FOREIGN KEY (manual_id) REFERENCES MANUAL(manual_id),
	step_num integer,
	PRIMARY KEY(manual_id, step_num),
	title varchar(20),
	detail varchar(255),
	picture blob
);

CREATE TABLE IF NOT EXISTS IMAGE(
	id integer PRIMARY KEY,
	image blob
);