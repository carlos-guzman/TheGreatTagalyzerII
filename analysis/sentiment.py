from nltk.corpus import wordnet as wn
from nltk.corpus import sentiwordnet as swn
import string
import csv
import sys

#[hashtag, sent_value]
hashtag_data = {}
post_result_name = 'post_data.csv'

#read in corpus and returns sentiment value of each hashtag
def main():
	post = sys.argv[1]
	#post_result = open(post_result_name, 'w')
	print getSentimentValue(post)
	#post_result.close()

#given post, get sentiment value
def getSentimentValue(post):
	hashtags = getHashTags(post)
	post = cleanPost(post)
	words = post.split(' ')
	total_score = 0
	for current in words:
		values = sentiValues(current)
		total_score += (values[0] - values[1])
	return total_score

#given a string, grab all the hashtags
def getHashTags(post):
	words = post.split()
	hashtags = []
	for current in words:
		if (current[0] == '#'):
			hashtags.append(current[1:])
	return hashtags

#given a post, remove punctuations and set all characters to lowercase
def cleanPost(post):
	result = repr(post.lower())
	return result.translate(string.maketrans("",""), string.punctuation)

#given a word, returns list of sentiment values
#[0] = positive, [1] = negative, [2] = objectiveness
#just give 1.0 to objectiveness if word not found
def sentiValues(word):
	code = wn.synsets(word)
	sentiValues = [0, 0, 1]
	if (len(code) > 0):
		plainTextWord = str(code[0])[8:-2]
		values = swn.senti_synset(plainTextWord)
		if values:
			sentiValues[0] = values.pos_score()
			sentiValues[1] = values.neg_score()
			sentiValues[2] = values.obj_score()
	return sentiValues

if (__name__ == "__main__"):
	main()
