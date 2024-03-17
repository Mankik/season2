from django.db import models

# Create your models here.
class Members(models.Model):
    nickname = models.CharField(max_length=50)
    email = models.EmailField(max_length=40)
    password = models.CharField(max_length=100)
    profImg = models.URLField(max_length=512)
    seedCnt = models.IntegerField(default=10)
    createDate = models.DateTimeField(auto_now_add=True)
    createCnt = models.IntegerField(default=0)

    class Meta:
        db_table = 'member'


class Books(models.Model):
    bookName = models.CharField(max_length=50)
    bCreateDate = models.DateTimeField(auto_now_add=True)
    coverImg = models.URLField(max_length=512)
    copyR = models.CharField(max_length=30)
    evalStart = models.IntegerField(default=0)
    writer = models.CharField(max_length=30)
    lastPage = models.IntegerField()

    class Meta:
        db_table = 'book'


class MyLibrary(models.Model):
    book = models.ForeignKey(Books, on_delete=models.CASCADE)
    user = models.ForeignKey(Members, on_delete=models.CASCADE)

    class Meta:
        db_table = 'mylibrary'


class Draft(models.Model):
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    savedAt = models.DateTimeField(auto_now_add=True)
    drawSty = models.IntegerField(default=0)
    diff = models.IntegerField(default=0)

    class Meta:
        db_table = 'draft'

class Intro(models.Model):
    draft = models.ForeignKey(Draft, on_delete=models.CASCADE)
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    introMode = models.BooleanField()
    subject = models.CharField(max_length=100)
    IntroContent = models.TextField()

    class Meta:
        db_table = 'intro'


class DraftPage(models.Model):
    draft = models.ForeignKey(Draft, on_delete=models.CASCADE)
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    pageNum = models.IntegerField()
    pageContent = models.TextField()

    class Meta:
        db_table = 'draftpage'


class FeedBack(models.Model):
    draft = models.ForeignKey(Draft, on_delete=models.CASCADE)
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    feedCap = models.CharField(max_length=512)
    feedContent = models.TextField()

    class Meta:
        db_table = 'feedback'


class Followers(models.Model):
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    followImg = models.URLField(max_length=512)

    class Meta:
        db_table = 'follower'


class Flower(models.Model):
    flowerName = models.CharField(max_length=50)
    achieveCnt = models.IntegerField()
    flowerImg = models.URLField(max_length=512)

    class Meta:
        db_table = 'flower'


class MyForest(models.Model):
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    flower = models.ForeignKey(Flower, on_delete=models.CASCADE)

    class Meta:
        db_table = 'myforest'


class MyFlower(models.Model):
    user = models.ForeignKey(Members, on_delete=models.CASCADE)
    flower = models.ForeignKey(Flower, on_delete=models.CASCADE)
    getDate = models.DateField()
    isActive = models.BooleanField()

    class Meta:
        db_table = 'myflower'
