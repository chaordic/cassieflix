CREATE KEYSPACE cassieflix
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE cassieflix;

CREATE TABLE "movies" ( 
	"id" text,
	"title" text,
	"year" int,
	"rated" text,
	"released" timestamp,
	"runtime" text,
	"genre" text,
	"director" text,
	"writer" text,
	"actors" text,
	"plot" text,
	"language" text,
	"country" text,
	"awards" text,
	"poster" text, 
	PRIMARY KEY ("id") 
);
