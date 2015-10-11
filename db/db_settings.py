# Database sepecific settings.
DATABASES = {
  'default': {
    'ENGINE': 'django.db.backends.mysql',
    'OPTIONS': {
      'read_default_file': os.path.join(BASE_DIR, '..', '..', 'db', 'my.cnf'),
    },
  }
}


# Database routers go here:
# DATABASE_ROUTERS = ['micro.routers.UserRouter']
