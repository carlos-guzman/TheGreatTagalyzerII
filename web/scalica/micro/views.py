from django.contrib.auth.decorators import login_required
from django.http import HttpResponse
from django.shortcuts import render
from django.contrib.auth.models import User
from .models import Following

# Create your views here.
@login_required(redirect_field_name='my_redirect_field')
def index(request):
    '''List of recent posts by people I follow'''
    follows = [o.followee_id for o in Following.objects.filter(follower_id=1)]
    # print Following.objects.filter(follower_id=1).query
    followed_users = User.objects.filter(id__in=follows)
    context = {'follows_list': followed_users}
    return render(request, 'micro/index.html', context)

def stream(request, user_id):
    return HttpResponse("Stream for user: %s." % user_id)
