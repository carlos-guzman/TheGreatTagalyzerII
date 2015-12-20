#!/usr/bin/env python
import sys


for line in sys.stdin:
    line = line.strip()
    words = line.split(',')
    text = words[2]
    hashtag_id = words[3]
    sentiment_value = 0.5
    if (hashtag_id == " None"):
        continue
        
    print(hashtag_id,sentiment_value)