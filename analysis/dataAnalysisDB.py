import psycopg2, sys, json


# doesnt have the times!
# columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value', 'inserted_at', 'created_at']

result = None

postId = 2;
sentimentValue = 1

def connectIntoDB():
    con = psycopg2.connect("dbname='tagalyzer' user='taguser' host='db.tagalyzer.io' password='swordfish'")
    cursor = con.cursor()


def getValuesByID(id, connection, cursor):

    columns = ['id', 'client_id', 'text', 'owner_id', 'sentiment_value']
    #extract the post given with the given id and create a dictinuary
    cursor.execute("""SELECT * FROM posts WHERE id = %s ;""" % (str(id)) )
    rows = cursor.fetchall()

    for row in rows:
        print(row);
        result = (dict(zip(columns, row)))


def updatePostSentiment(sentimentValue, postID, connection, cursor):

    cursor.execute("""UPDATE posts SET sentiment_value = %s WHERE id = %s ;""" % (str(sentimentValue), str(postID) ))
    connection.commit()
    print "Updated post %s with the sentiment %s" % (str(sentimentValue), str(postID))


def updateHashtagSentiment(CombinedSentimentValue, hashtag, connection, cursor):

    cursor.execute("""UPDATE hashtags SET sentiment_value = %s WHERE tag = %s """  % (str(CombinedSentimentValue), hashtag) )
    connection.commit()


def main():
    try:
        try:
            con = psycopg2.connect("dbname='tagalyzer' user='taguser' host='db.tagalyzer.io' password='swordfish'")
            cursor = con.cursor()

            getValuesByID(3, con, cursor)
            updatePostSentiment(sentimentValue, 3, con, cursor)
        except:
            print "I am unable to connect to the database"

    except psycopg2.DatabaseError, e:
        print 'Error %s' % e
        sys.exit(1)
    finally:
        if con:
            con.close()

if __name__ == "__main__":
    main()



