# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('micro', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='following',
            name='id',
            field=models.AutoField(auto_created=True, primary_key=True, default='', serialize=False, verbose_name='ID'),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='following',
            name='follower',
            field=models.ForeignKey(related_name='user_follows', to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterField(
            model_name='post',
            name='text',
            field=models.CharField(default=b'blah', max_length=256),
        ),
    ]
