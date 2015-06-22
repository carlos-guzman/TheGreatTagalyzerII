from django.contrib.auth import logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.http import HttpResponse
from django.shortcuts import render
from .models import Following, Post

# Create your views here.

# Views requiring authentication
# Transitions {login}
def index(request):
    if request.user.is_authenticated():
        return home(request)
    else:
        return HttpResponse("Landing page. No login required")

def stream(request, user_id):
    return HttpResponse("Stream for user: %s." % user_id)

def register(request):
    return HttpResponse("TODO: Add register user page")

# Logged-in operation

# Transitions {stream,post,follow,logout}
@login_required
def home(request):
    '''List of recent posts by people I follow'''
    #TODO: See if can replace with a django join. 
    follows = [o.followee_id for o in Following.objects.filter(
        follower_id=request.user.id)]
    post_list = Post.objects.filter(user_id__in=follows)
    context = {'post_list': post_list}
    return render(request, 'micro/index.html', context)

# Allows to post something and shows my most recent posts.
# Transitions {home,logout}
@login_required
def post(request):
    return HttpResponse("TODO: Add post page")

# Transitions {stream,home,logout}
@login_required
def follow(request):
    return HttpResponse("TODO: Add follow page")
