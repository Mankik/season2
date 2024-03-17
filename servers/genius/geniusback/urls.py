from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import *

router = DefaultRouter()
router.register(r'members', MembersViewSet)
router.register(r'books', BooksViewSet)
router.register(r'mylibrary', MyLibraryViewSet)
router.register(r'draft', DraftViewSet)
router.register(r'intro', IntroViewSet)
router.register(r'draftpage', DraftPageViewSet)
router.register(r'feedback', FeedBackViewSet)
router.register(r'followers', FollowersViewSet)
router.register(r'flower', FlowerViewSet)
router.register(r'myforest', MyForestViewSet)
router.register(r'myflower', MyFlowerViewSet)

urlpatterns= [
    path('', include(router.urls)),
]
