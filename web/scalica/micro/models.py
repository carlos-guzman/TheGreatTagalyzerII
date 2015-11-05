from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from django.conf import settings
from django.db import models
from django.forms import ModelForm, TextInput

# Note: Until we have a mirrored user Model, all fields that were previously
# foreign keys to the auth User, must be named user_id.

class Post(models.Model):
  user_id = models.BigIntegerField(null=True)
  text = models.CharField(max_length=256, default="")
  pub_date = models.DateTimeField('date_posted')
  def __str__(self):
    if len(self.text) < 16:
      desc = self.text
    else:
      desc = self.text[0:16]
    return str(self.user_id) + ':' + desc

class Following(models.Model):
  user_id = models.BigIntegerField(null=True)
  followee_id = models.BigIntegerField(null=True)
  follow_date = models.DateTimeField('follow date')
  def __str__(self):
    return std(self.user_id) + "->" + str(self.followee_id)

# Model Forms
class PostForm(ModelForm):
  class Meta:
    model = Post
    fields = ('text',)
    widgets = {
      'text': TextInput(attrs={'id' : 'input_post'}),
    }

class FollowingForm(ModelForm):
  class Meta:
    model = Following
    fields = ('followee_id',)

class MyUserCreationForm(UserCreationForm):
  class Meta(UserCreationForm.Meta):
    help_texts = {
      'username' : '',
    }
