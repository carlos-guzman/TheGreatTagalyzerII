from django.conf.urls import include, url

from . import views
from django.contrib.auth import views as auth_views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^(?P<user_id>[0-9]+)/$', views.stream, name='stream'),
    url('^', include('django.contrib.auth.urls'))
]
