from django.contrib.auth.models import User
from django.db import models

# Create your models here.
# Take the users Table from https://docs.djangoproject.com/en/1.8/topics/auth/default/
class Post(models.Model):
  user = models.ForeignKey(User)
  text = models.CharField(max_length=256, default="blah")
  pub_date = models.DateTimeField('date_posted')
  def __str__(self):
    if len(self.text) < 16:
      desc = self.text
    else:
      desc = textself.text[0:16]
    return self.user.username + ':' + desc

class Following(models.Model):
  follower = models.ForeignKey(User, related_name="user_follows")
  followee = models.ForeignKey(User, related_name="user_followed")
  follow_date = models.DateTimeField('follow data')
  def __str__(self):
    return self.follower.username + "->" + self.followee.username

