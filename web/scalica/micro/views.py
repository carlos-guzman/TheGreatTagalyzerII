from django.contrib.auth.decorators import login_required
from django.http import HttpResponse
from django.shortcuts import render

# Create your views here.
@login_required(redirect_field_name='my_redirect_field')
def index(request):
    return HttpResponse("Testing index page")

def stream(request, user_id):
    return HttpResponse("Stream for user: %s." % user_id)
