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

CREATE TABLE usersbyid ( 
	login text,
	name text,
	email text, 
	creationdate timestamp,
	PRIMARY KEY (login) 
);

CREATE TABLE similarmovies ( 
	movieid text,
	similarity float,
	similarmovieid text,
	PRIMARY KEY (movieid, similarity, similarmovieid)
) WITH CLUSTERING ORDER BY (similarity DESC);

CREATE TABLE useractivitybyuser ( 
	userid text, 
	activitytype text,
	activityid timeuuid,
	activityuserid text,
	activitymovieid text,
	PRIMARY KEY ((userid, activitytype), activityid) 
) WITH CLUSTERING ORDER BY (activityid ASC);

CREATE TABLE useractivitybytime ( 
	timeprefix text, 
	activitytype text,
	activityid timeuuid,
	activityuserid text,
	activitymovieid text,
	PRIMARY KEY ((timeprefix, activitytype), activityid) 
) WITH CLUSTERING ORDER BY (activityid ASC);

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

