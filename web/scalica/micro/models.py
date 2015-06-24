from django.contrib.auth.models import User
from django.db import models
from django.forms import ModelForm

# Create your models here.

class Post(models.Model):
  user = models.ForeignKey(User)
  text = models.CharField(max_length=256, default="What's on your mind...")
  pub_date = models.DateTimeField('date_posted')
  def __str__(self):
    if len(self.text) < 16:
      desc = self.text
    else:
      desc = self.text[0:16]
    return self.user.username + ':' + desc

class Following(models.Model):
  follower = models.ForeignKey(User, related_name="user_follows")
  followee = models.ForeignKey(User, related_name="user_followed")
  follow_date = models.DateTimeField('follow data')
  def __str__(self):
    return self.follower.username + "->" + self.followee.username

class PostForm(ModelForm):
    class Meta:
        model = Post
        fields = ['text']

class FollowingForm(ModelForm):
    class Meta:
        model = Following
        fields = ['followee']
