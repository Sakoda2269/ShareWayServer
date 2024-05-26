INSERT INTO ACCOUNT VALUES 
(
	'79de0f50-5113-43ec-83c5-c16671d01cf4',
	'test1',
	'$2a$08$7563MWncbW/oenHWfZSKE.jWeMARqdO/WZajbstas2xUamOBXDWz6',
	'test1@example.com',
	'ROLE_GENERAL',
	'こんにちは',
	null
);

INSERT INTO MANUAL VALUES
(
	'3a6b4766-a35f-4025-9d28-361fdd1d2744',
	'testManual',
	'79de0f50-5113-43ec-83c5-c16671d01cf4',
	null
);

INSERT INTO STEP VALUES
(
	'3a6b4766-a35f-4025-9d28-361fdd1d2744',
	0,
	'testStep0',
	'this is test step number 0',
	null
),
(
	'3a6b4766-a35f-4025-9d28-361fdd1d2744',
	1,
	'testStep1',
	'this is test step number 1',
	null
);