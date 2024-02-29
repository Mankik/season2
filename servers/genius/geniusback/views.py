from django.shortcuts import render
from rest_framework import viewsets
from .models import *
from .serializers import createSerializer

MembersSerializer = createSerializer(Members)
BooksSerializer = createSerializer(Books)
MyLibrarySerializer = createSerializer(MyLibrary)
DraftSerializer = createSerializer(Draft)
IntroSerializer = createSerializer(Intro)
DraftPageSerializer = createSerializer(DraftPage)
FeedBackSerializer = createSerializer(FeedBack)
FollowersSerializer = createSerializer(Followers)
FlowerSerializer = createSerializer(Flower)
MyForestSerializer = createSerializer(MyForest)
MyFlowerSerializer = createSerializer(MyFlower)

class MembersViewSet(viewsets.ModelViewSet):
    queryset = Members.objects.all()
    serializer_class = MembersSerializer

class BooksViewSet(viewsets.ModelViewSet):
    queryset = Books.objects.all()
    serializer_class = BooksSerializer

class MyLibraryViewSet(viewsets.ModelViewSet):
    queryset = MyLibrary.objects.all()
    serializer_class = MyLibrarySerializer

class DraftViewSet(viewsets.ModelViewSet):
    queryset = Draft.objects.all()
    serializer_class = DraftSerializer

class IntroViewSet(viewsets.ModelViewSet):
    queryset = Intro.objects.all()
    serializer_class = IntroSerializer

class DraftPageViewSet(viewsets.ModelViewSet):
    queryset = DraftPage.objects.all()
    serializer_class = DraftPageSerializer

class FeedBackViewSet(viewsets.ModelViewSet):
    queryset = FeedBack.objects.all()
    serializer_class = FeedBackSerializer

class FollowersViewSet(viewsets.ModelViewSet):
    queryset = Followers.objects.all()
    serializer_class = FollowersSerializer

class FlowerViewSet(viewsets.ModelViewSet):
    queryset = Flower.objects.all()
    serializer_class = FlowerSerializer

class MyForestViewSet(viewsets.ModelViewSet):
    queryset = MyForest.objects.all()
    serializer_class = MyForestSerializer

class MyFlowerViewSet(viewsets.ModelViewSet):
    queryset = MyFlower.objects.all()
    serializer_class = MyFlowerSerializer
# Create your views here.
