from django.contrib.auth.models import User
from django.db import models

# Create your models here.
# Take the users Table from https://docs.djangoproject.com/en/1.8/topics/auth/default/
class Post(models.Model):
    user = models.ForeignKey(User)
    text = models.CharField(max_length=256, default="blah")
    pub_date = models.DateTimeField('date_posted')

class Following(models.Model):
    follower = models.ForeignKey(User, related_name="user_follows")
    followee = models.ForeignKey(User, related_name="user_followed")
    follow_date = models.DateTimeField('follow data')

