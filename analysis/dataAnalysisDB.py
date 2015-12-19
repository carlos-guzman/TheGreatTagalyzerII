import psycopg2, sys, json

import tinys3
from boto.s3.connection import S3Connection


S3_ACCESS_KEY = 'AKIAJAX3BHOTZJ2BBUVQ'
S3_SECRET_KEY = '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'

dbname = 'tagalyzer'
dbuser = 'taguser'
dbhost = 'db.tagalyzer.io'
dbpass = 'swordfish'

filename = "data.csv"



# doesnt have the times!
# columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value', 'inserted_at', 'created_at']

result = None

postId = 2;
sentimentValue = 1



def connectIntoDB():
    DBcon = psycopg2.connect("dbname=%s user=%s host=%s password=%s" % (dbname, dbuser, dbhost, dbpass))
    cursor = DBcon.cursor()
    return DBcon


def getNonAnalyzed(connection, cursor):

    # Get the shards!
    shard = "shard_0000001."
    columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value']
    #extract the post given with the given id and create a dictionary

    cursor.execute("""SELECT * FROM %sposts WHERE sentiment_value IS NULL  ;""" % (shard) )
    rows = cursor.fetchall()
    file = open(filename, 'w')
    for row in rows:
        file.write(str(row));
        file.write("\n");
        result = (dict(zip(columns, row)))


def getValuesByID(id, connection, cursor):

    # Get the shards!
    shard = "shard_0000001."
    columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value']
    #extract the post given with the given id and create a dictionary
    cursor.execute("""SELECT * FROM %sposts WHERE id = %s ;""" % (shard, str(id)) )
    rows = cursor.fetchall()

    for row in rows:
        file.write(row);
        result = (dict(zip(columns, row)))


def uploadS3():

    S3conn = tinys3.Connection(S3_ACCESS_KEY,S3_SECRET_KEY,tls=True)
    f = open(filename,'rb')
    S3conn.upload(filename,f,'tagalyzer/data')


def updatePostSentiment(sentimentValue, postID, connection, cursor):

    cursor.execute("""UPDATE posts SET sentiment_value = %s WHERE id = %s ;""" % (str(sentimentValue), str(postID) ))
    connection.commit()
    print "Updated post %s with the sentiment %s" % (str(sentimentValue), str(postID))


def updateHashtagSentiment(CombinedSentimentValue, hashtag, connection, cursor):

    cursor.execute("""UPDATE hashtags SET sentiment_value = %s WHERE tag = %s """  % (str(CombinedSentimentValue), hashtag) )
    connection.commit()


def main():
    try:

        DBcon = connectIntoDB()
        cursor = DBcon.cursor()
        getNonAnalyzed(DBcon, cursor)
        uploadS3()


    except psycopg2.DatabaseError, e:
        print 'Error %s' % e
        sys.exit(1)
    finally:
        if DBcon:
            DBcon.close()

if __name__ == "__main__":
    main()



