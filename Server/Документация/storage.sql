-- ship."location" definition

-- Drop table

-- DROP TABLE ship."location";

CREATE TABLE ship."location" (
	id_location serial4 NOT NULL,
	title varchar NULL,
	"position" varchar NOT NULL,
	CONSTRAINT location_id_location_not_null NOT NULL id_location,
	CONSTRAINT location_pk PRIMARY KEY (id_location),
	CONSTRAINT location_position_not_null NOT NULL "position"
);


-- ship.status definition

-- Drop table

-- DROP TABLE ship.status;

CREATE TABLE ship.status (
	id_status serial4 NOT NULL,
	"name" varchar(255) NOT NULL,
	description text NULL,
	CONSTRAINT status_id_status_not_null NOT NULL id_status,
	CONSTRAINT status_name_not_null NOT NULL name,
	CONSTRAINT status_pkey PRIMARY KEY (id_status)
);


-- ship.cabin definition

-- Drop table

-- DROP TABLE ship.cabin;

CREATE TABLE ship.cabin (
	id_cabin serial4 NOT NULL,
	id_status int4 NOT NULL,
	"number" int4 NOT NULL,
	is_day bool DEFAULT true NOT NULL,
	CONSTRAINT cabin_id_cabin_not_null NOT NULL id_cabin,
	CONSTRAINT cabin_id_status_not_null NOT NULL id_status,
	CONSTRAINT cabin_is_day_not_null NOT NULL is_day,
	CONSTRAINT cabin_number_key UNIQUE (number),
	CONSTRAINT cabin_number_not_null NOT NULL number,
	CONSTRAINT cabin_pkey PRIMARY KEY (id_cabin),
	CONSTRAINT cabin_id_status_fkey FOREIGN KEY (id_status) REFERENCES ship.status(id_status)
);


-- ship.schedule definition

-- Drop table

-- DROP TABLE ship.schedule;

CREATE TABLE ship.schedule (
	id_schedule serial4 NOT NULL,
	title varchar NOT NULL,
	"date" date NOT NULL,
	start_time time NULL,
	end_time time NULL,
	id_location int4 NOT NULL,
	CONSTRAINT schedule_data_not_null NOT NULL date,
	CONSTRAINT schedule_id_schedule_not_null NOT NULL id_schedule,
	CONSTRAINT schedule_location_not_null NOT NULL id_location,
	CONSTRAINT schedule_pk PRIMARY KEY (id_schedule),
	CONSTRAINT schedule_title_not_null NOT NULL title,
	CONSTRAINT schedule_location_fk FOREIGN KEY (id_location) REFERENCES ship."location"(id_location)
);