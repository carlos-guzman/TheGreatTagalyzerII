# Functions to manipulate IDs and shards.

# Assumptions.
# - Average user posts 10 posts a day.
# - 4KiB to store a post + aux data.
# - Plan ahead for 10 years.
# - A user has 1024 followers.
# - Storing social graph per user (edges + reverse) = 1Kib per follower.
# - Media will be stored elsewhere.
# Required storage per user = 365*10*40KiB + 1MiB =~ 150MiB.
# 
# For 100M users we will need approx. 15PB.
# Assuming a single database can handle at most 4TB (max table size in MySQL)
# we need at most 3750 Physical shards. Logical shards <= Physical shards.
# So 4096 shards should suffice. Added benefit of a multiple of 4 bits is that
# we can display as 3 hex digits.
LOGICAL_SHARD_BITS = 12
LOGICAL_SHARDS = 4096

# All internal IDs will be 64 bits (represented as a 16 hex Char string).
NUM_ID_BITS = 64

def logical_shard_num_from_id(id):
  '''Returns an integer logical shard number.'''
  pass

def logical_shard_name_from_id(id):
  '''Returns the name of a shard from an id. Always a string of length 3.'''
  pass

class ShardResolver():
  def __init__(self): pass
  def resolve(logical_shard_name): pass
