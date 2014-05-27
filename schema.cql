CREATE KEYSPACE cassieflix
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE cassieflix;

CREATE TABLE moviesbyid (
        id text,
        title text,
        year int,
        rated text,
        released timestamp,
        runtime text,
        genre text,
        director text,
        writer text,
        actors text,
        plot text,
        language text,
        country text,
        awards text,
        poster text,
        PRIMARY KEY (id)
);

CREATE TABLE moviesbyname (
        prefix text,
        id text,
        title text,
        year int,
        rated text,
        released timestamp,
        runtime text,
        genre text,
        director text,
        writer text,
        actors text,
        plot text,
        language text,
        country text,
        awards text,
        poster text,
        PRIMARY KEY (prefix, title, id)
) WITH CLUSTERING ORDER BY (title ASC);

CREATE TABLE usersbyid ( 
	login text,
	name text,
	email text, 
	creationdate timestamp,
	PRIMARY KEY (login) 
);

CREATE TABLE movielistbyid (
        listid uuid, /* list columns */
        listtitle text static,
        listowner text static,
        listcreated timestamp static,
        movieposition int, /* movie columns */
        movieid text,
        movietitle text,
        movieyear int,
        movieposter text,
        PRIMARY KEY (listid, movieposition)
) WITH CLUSTERING ORDER BY (movieposition ASC);

CREATE TABLE movielistsummarybyuser ( 
	userlogin TEXT,
	listcreation timestamp,
	listid uuid,
	listtitle text,
	PRIMARY KEY (userlogin, listcreation, listid) 
) with CLUSTERING ORDER BY (listcreation DESC);
