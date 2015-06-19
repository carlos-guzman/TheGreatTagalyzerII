from django.contrib.auth import logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.http import HttpResponse
from django.shortcuts import render
from .models import Following, Post

# Create your views here.
#@login_required(login_url='/micro/login/')
@login_required
def index(request):
    '''List of recent posts by people I follow'''
    follows = [o.followee_id for o in Following.objects.filter(
        follower_id=request.user.id)]
    # print Following.objects.filter(follower_id=1).query
    post_list = Post.objects.filter(user_id__in=follows)
    context = {'post_list': post_list}
    return render(request, 'micro/index.html', context)

def stream(request, user_id):
    return HttpResponse("Stream for user: %s." % user_id)

def register(request):
    return HttpResponse("TODO: Add register user page")
