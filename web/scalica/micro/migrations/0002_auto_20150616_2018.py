# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('micro', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('username', models.CharField(unique=True, max_length=30)),
                ('first_name', models.CharField(max_length=30, blank=True)),
                ('last_name', models.CharField(max_length=30, blank=True)),
                ('email', models.EmailField(max_length=254, blank=True)),
            ],
        ),
        migrations.AlterField(
            model_name='following',
            name='followee',
            field=models.ForeignKey(related_name='user_followed', to='micro.User'),
        ),
        migrations.AlterField(
            model_name='following',
            name='follower',
            field=models.ForeignKey(related_name='user_follows', to='micro.User'),
        ),
        migrations.AlterField(
            model_name='post',
            name='user',
            field=models.ForeignKey(to='micro.User'),
        ),
    ]
