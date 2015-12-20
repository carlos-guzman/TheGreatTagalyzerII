#!/usr/bin/env python
import sys


results = {}
hashtag_ids = []

def sentimentAnalysis(text):
    return(0,5)

file = open("data.csv", 'r')
for line in file:
    line = line.strip()

    # parse the input we got from mapper.py
    hashtag_id, sentiment_value = line.split(",")
    hashtag_id = int(hashtag_id[:-1])
    sentiment_value = float(sentiment_value)
    if hashtag_id not in hashtag_ids:
        hashtag_ids.append(hashtag_id)

    count = 1
    if hashtag_id not in results:
        results[hashtag_id] = [sentiment_value, count]
    else:
        count = results[hashtag_id][1] + 1
        existingSentiment = results[hashtag_id][0]
        average = (existingSentiment + sentiment_value) / count
        results[hashtag_id] = [average, count]

for hashtag in hashtag_ids:
    print(hashtag, results[hashtag][0])