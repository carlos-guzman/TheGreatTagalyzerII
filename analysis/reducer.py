#!/usr/bin/env python
import sys

def sentimentAnalysis(text):
    return(0,5)

for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()

    # parse the input we got from mapper.py
    hashtag_id = line.split(',')[0]
    sentiment_values = line[line.index('[')+1 :line.index(']')].strip().split(',')
    count = 0
    hashtagSentiment = 0.0
    for value in sentiment_values:
        print(value.strip())
        hashtagSentiment += float(value)
        count += 1

    hashtagSentiment /= count
    result = "%s,%s" % (str(hashtag_id), str(hashtagSentiment))
    sys.stdout.write(result)