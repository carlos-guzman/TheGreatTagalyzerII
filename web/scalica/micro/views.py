from django.contrib.auth import logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.core.paginator import Paginator, EmptyPage, PageNotAnInteger
from django.http import HttpResponse
from django.shortcuts import render
from django.utils import timezone
from .models import Following, Post, FollowingForm, PostForm

# Create your views here.

# Views requiring authentication
# Transitions {login}
def index(request):
  if request.user.is_authenticated():
    return home(request)
  else:
    return anon_home(request)

def anon_home(request):
  return render(request, 'micro/public.html')

def stream(request, user_id):
  user = User.objects.get(pk=user_id)
  post_list = Post.objects.filter(user_id=user_id)
  paginator = Paginator(post_list, 10)
  page = request.GET.get('page')
  try:
    posts = paginator.page(page)
  except PageNotAnInteger:
    # If page is not an integer, deliver first page.
    posts = paginator.page(1) 
  except EmptyPage:
    # If page is out of range (e.g. 9999), deliver last page of results.
    posts = paginator.page(paginator.num_pages)
  context = {
    'posts' : posts,
    'stream_user' : user,
  }
  return render(request, 'micro/stream.html', context)

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
  if request.method == 'POST':
    form = PostForm(request.POST)
    new_post = form.save(commit=False)
    new_post.user = request.user
    new_post.pub_date = timezone.now()
    new_post.save()
    return home(request)
  else:
    form = PostForm
  return render(request, 'micro/post.html', {'form' : form})

# Transitions {stream,home,logout}
@login_required
def follow(request):
  if request.method == 'POST':
    # Use pagination to show the list of the people that you follow.
    return follow(request)
  else:
    form = FollowingForm
  return render(request, 'micro/follow.html', {'form' : form})
