DROP TABLE IF EXISTS exchange_rate CASCADE;

CREATE TABLE exchange_rate (
	id SERIAL PRIMARY KEY,
	currency_from VARCHAR (50) NOT NULL,
	currency_to VARCHAR (50) NOT NULL,
	conversion_rate FLOAT (8) NOT NULL
	);