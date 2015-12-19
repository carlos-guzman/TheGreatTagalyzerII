import sys

def sentimentAnalysis():
    return(0.7)

# input comes from STDIN (standard input)
for line in sys.stdin:
    line = line.strip()
    words = line.split(',')
    text = words[2]
    hashtag_id = words[3]
    sentiment_value = sentimentAnalysis(text)
    result = "%s,%s" % (str(hashtag_id), str(sentiment_value))
    sys.stdout.write(result)


# input comes from STDIN
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()

    # parse the input we got from mapper.py
    hashtag_id = line.split(',')[0]
    sentiment_values = line.split(',')[1]

    hashtagSentiment = 0
    count = 0
    for value in sentiment_values:
        hashtagSentiment += value
        count += 1

    hashtagSentiment /= count
    sys.stdout(hashtag_id,hashtagSentiment)
