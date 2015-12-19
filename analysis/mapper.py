#!/usr/bin/env python
import sys
def sentimentAnalysis():
    return(0.5)

for line in sys.stdin:
    line = line.strip()
    words = line.split(',')
    text = words[2]
    hashtag_id = words[3]
    sentiment_value = sentimentAnalysis(text)
    result = "%s,%s" % (str(hashtag_id), str(sentiment_value))
    sys.stdout.write(result)