# TheGreatTagalyzerII
Large Scale Web Apps project

We will analyze tags from Scalica or any social network and produce analysis, graphing, monitoring, tracking and statistics for it

Each part of the project is in a different branch. The idea is that each branch could be a different server.

analyzer -

api - Dropwizard Java project that includes the DB migrations. All necessary information to build and run is in the README. The migrations are in API/src/resources/migrations.xml The API is currently running on api.tagalyzer.io

dashboard - To see it, just open index.html. The dashboard is CNAMEd or redirected from dashboard.tagalyzer.io

grpc - Ideally the creator code that calls the server RPC would be in the client, or scalica. I had some trouble trying to run scalica successfully so the client just takes the post information as arguments. The server just can be ran with ./server. There is a server running in grpc.tagalyzer.io:50051

scalica - This branch contains the latest scalica. Our changes are not reflected in this version (like adding __init__.py to utils) because it was never working (latest error I remember was some Form Validation or similar) If we get it to work soon, it will be in scalica.tagalyzer.io (my failed attempts are there)


Our Postgres DB is in db.tagalyzer.io:5432
If we add more dbs for sharding, the format will be 01.db.tagalyzer.io, 02.db.tagalyzer.io... I don't think we will have more than 100 databases.



By Batu Aytemiz, Carlos Guzman, David Hu and David Peso.
