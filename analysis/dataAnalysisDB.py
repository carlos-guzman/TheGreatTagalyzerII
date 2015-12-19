import psycopg2, sys, json

import tinys3
from boto.s3.connection import S3Connection

#we should hide these later...
S3_ACCESS_KEY = 'AKIAJAX3BHOTZJ2BBUVQ'
S3_SECRET_KEY = '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'

dbname = 'tagalyzer'
dbuser = 'taguser'
dbhost = 'db.tagalyzer.io'
dbpass = 'swordfish'

filename = "data.csv"

shards = 4 #shards start from 1 to 4

# doesnt have the times!
# columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value', 'inserted_at', 'created_at']

result = None

#ok
def connectIntoDB():
    DBcon = psycopg2.connect("dbname={} user={} host={} password={}".format(dbname, dbuser, dbhost, dbpass))
    cursor = DBcon.cursor()
    return DBcon

#ok
#execute arbitrary query (for debugging purposes)
def execQuery(query, connection, cursor):
    cursor.execute(query)
    rows = cursor.fetchall()

    for row in rows:
        print(row)

#ok
#gets all posts which hasn't been analyzed yet
def getNonAnalyzed(connection, cursor):
    columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value']

    for n in range(1, shards+1):
        cursor.execute("""SELECT * FROM shard_{0:07d}.posts WHERE sentiment_value IS NULL;""".format(n) )
        rows = cursor.fetchall()
        file = open(filename, 'w')
        for row in rows:
            print row
            file.write(str(row));
            file.write("\n");
            result = (dict(zip(columns, row)))

#given a post id, extract the post and create a dictionary
def getValuesByID(postID, connection, cursor):
    columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value']
    print postID
    print postID % 4 + 1
    shardID = (postID % shards) + 1
    cursor.execute("""SELECT * FROM shard_{0:07d}.posts WHERE id = '{i}';""".format(shardID, i=str(postID)) )
    rows = cursor.fetchall()

    for row in rows:
        print row
        file.write(row);
        result = (dict(zip(columns, row)))
#ok
def uploadS3():
    S3conn = tinys3.Connection(S3_ACCESS_KEY,S3_SECRET_KEY,tls=True)
    f = open(filename,'rb')
    S3conn.upload(filename,f,'tagalyzer/data')

#update the sentiment value of the post with the given post ID
def updatePostSentiment(sentimentValue, postID, connection, cursor):
    shardID = (postID % shards) + 1
    cursor.execute("""UPDATE shard_{0:07d}.posts SET sentiment_value = {s} WHERE id = {i} ;""".format(shardID, s=str(sentimentValue), i=str(postID) ) )
    connection.commit()
    print "Updated post {} with the sentiment {}".format(str(postID), str(sentimentValue))

#ok
#update the sentiment value of the given hashtag
def updateHashtagSentiment(combinedSentimentValue, hashtag, connection, cursor):
    shardID = (java_string_hashcode(hashtag) % shards) + 1
    cursor.execute("""UPDATE shard_{0:07}.hashtags SET sentiment_value = {s} WHERE name = '{t}';""".format(shardID, s=str(combinedSentimentValue), t=hashtag) )
    connection.commit()

#ok
#hashing function used for sharding purposes
def java_string_hashcode(s):
    h = 0
    for c in s:
        h = (31 * h + ord(c)) & 0xFFFFFFFF
    return ((h + 0x80000000) & 0xFFFFFFFF) - 0x80000000

def main():
    try:
        DBcon = connectIntoDB()
        cursor = DBcon.cursor()
        #updateHashtagSentiment(-0.5, 'asdf', DBcon, cursor)
        tp = "test post from david"
 
        getValuesByID(8589934593L, DBcon, cursor)
        #updatePostSentiment(-0.1, 8589934593L, DBcon, cursor)
        #getNonAnalyzed(DBcon, cursor)
        #uploadS3()

    except psycopg2.DatabaseError, e:
        print 'Error %s' % e
        sys.exit(1)
    finally:
        if DBcon:
            DBcon.close()

if __name__ == "__main__":
    main()
