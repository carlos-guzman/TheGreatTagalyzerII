# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('micro', '0002_auto_20150616_2018'),
    ]

    operations = [
        migrations.AlterField(
            model_name='following',
            name='followee',
            field=models.ForeignKey(related_name='user_followed', to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterField(
            model_name='following',
            name='follower',
            field=models.ForeignKey(related_name='user_follows', to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterField(
            model_name='post',
            name='user',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL),
        ),
        migrations.DeleteModel(
            name='User',
        ),
    ]
