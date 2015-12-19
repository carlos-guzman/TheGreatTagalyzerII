from boto.s3.connection import S3Connection
import psycopg2

S3_ACCESS_KEY = 'AKIAJAX3BHOTZJ2BBUVQ'
S3_SECRET_KEY = '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'
dbname = 'tagalyzer'
dbuser = 'taguser'
dbhost = 'db.tagalyzer.io'
dbpass = 'swordfish'

outputFileName = "EMRoutput.csv"

def downloadS3():
    s3conn = S3Connection(S3_ACCESS_KEY, S3_SECRET_KEY)
    bucket = s3conn.get_bucket('tagalyzer')
    key = bucket.get_key('output/%s' % (outputFileName))
    key.get_contents_to_filename(outputFileName)

def connectIntoDB():
    DBcon = psycopg2.connect("dbname=%s user=%s host=%s password=%s" % (dbname, dbuser, dbhost, dbpass))
    cursor = DBcon.cursor()
    return DBcon

def updatePostSentiment(conn, cursor):

    file = open(outputFileName, 'r')
    content = file.readline()
    hashtag_ID = content.split(',')[0][:-1]
    sentimentValue = content.split(',')[1]


    shard = "shard_0000001."
    cursor.execute("""UPDATE %shashtags SET sentiment_value = %s WHERE id = %s ;""" % (shard, str(sentimentValue), str(hashtag_ID) ))
    conn.commit()
    print "Updated post %s with the sentiment %s" % (str(sentimentValue), str(hashtag_ID))


def main():
    try:
        DBcon = connectIntoDB()
        cursor = DBcon.cursor()
        downloadS3()
        updatePostSentiment(DBcon, cursor)

    except psycopg2.DatabaseError, e:
        print 'Error %s' % e
        sys.exit(1)
    finally:
        if DBcon:
            DBcon.close()

if __name__ == "__main__":
    main()

