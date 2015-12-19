
from link import lnk
import time

import tagalyzer_pb2

_ONE_DAY_IN_SECONDS = 60 * 60 * 24

shards = 4
db = lnk.dbs.tagalyzer

class Tagalyzer(tagalyzer_pb2.BetaTagalyzerServicer):

  def CreatePost(self, request, context):
    self.client_id = request.client_id
    self.text = request.text
    self.created_at = request.created_at
    tags = self.parse_tags(post)
    
    for t in tags:
      shard_id = 1 + java_string_hashcode(t) % shards
      print "Shard id for {}: {0:07d}".format(t, shard_id)
      query = "insert into shard_{0:07d}.hashtags (name) select '{}' where not exists (select id from shard_{0:07d}.hashtags where name='{}') returning id;".format(shard_id, tag, shard_id, tag)
      tag_id = db.execute(query)
      post_query = "insert into shard_{0:07d}.posts (client_id, text, created_at, hashtag_id) values ({}, '{}', '{}', {}) returning id;".format(shard_id, self.client_id, self.text, self.created_at, tag_id)
      db.execute(post_query)
    return tagalyzer_pb2.PostReply(id=self.client_id)

  def parse_tags(self, post):
    return [i[1:] for i in post.split() if i.startswith("#")]

  def java_string_hashcode(s):
    h = 0
    for c in s:
      h = (31 * h + ord(c)) & 0xFFFFFFFF
    return ((h + 0x80000000) & 0xFFFFFFFF) - 0x80000000
 

def serve():
  server = tagalyzer_pb2.beta_create_Tagalyzer_server(Tagalyzer())
  server.add_insecure_port('[::]:50051')
  server.start()
  try:
    while True:
      time.sleep(_ONE_DAY_IN_SECONDS)
  except KeyboardInterrupt:
    server.stop(0)

if __name__ == '__main__':
  serve()
