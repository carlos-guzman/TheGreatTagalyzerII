from django.conf.urls import url

from . import views

urlpatterns = [
    # ex: /micro/
    url(r'^$', views.index, name='index'),
    # ex: /stream/7283738/
    url(r'^(?P<user_id>[0-9]+)/$', views.stream, name='stream'),
]
