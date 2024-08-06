from django.shortcuts import render
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import viewsets, status
from rest_framework.views import APIView
from django.contrib.auth import authenticate, login
from django.db.models import Max, F
from rest_framework_simplejwt.tokens import RefreshToken
from django.shortcuts import get_object_or_404
from geniusback.models import *
from .serializers import createSerializer
from openai import OpenAI
import os

from .utils import generate, generate_image
import logging

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


# temporary auth for API test
class LoginViewforAuth(APIView):
    def post(self, request, *args, **kwargs):
        username = request.data.get('username')
        password = request.data.get('password')
        user = authenticate(request, username=username, password=password)
        if user is not None:
            login(request, user)
            refresh = RefreshToken.for_user(user)
            return Response({
                'refresh': str(refresh),
                'access': str(refresh.access_token),
                'message': 'logged in successfully'
            }, status=status.HTTP_200_OK)
        else:
            return Response({'error': 'invalid info'}, status=status.HTTP_401_UNAUTHORIZED)


class MembersViewSet(viewsets.ModelViewSet):
    queryset = Members.objects.all()
    serializer_class = MembersSerializer

    @action(detail=False, methods=['get'])
    def user_nickname(self, request):
        # calling user nickname
        user = request.user
        return Response({"user_nickname": user.nickname})

    @action(detail=False, methods=['get'])
    def user_id(self, request):
        # calling user id(PK)
        user = request.user
        return Response({"user_id": user.id})


# buying seeds
class PurchaseSeeds(APIView):
    def post(self, request, *args, **kwargs):
        user = request.user
        seeds_for_purchase = request.data.get('seeds_for_purchase', 0)

        try:
            seeds_for_purchase = int(seeds_for_purchase)
        except ValueError:
            return Response({'error': '올바르지 않은 씨앗 값입니다.'}, status=status.HTTP_400_BAD_REQUEST)

        if seeds_for_purchase < 0:
            return Response({'error': '씨앗의 값이 0보다 작습니다.'}, status=status.HTTP_400_BAD_REQUEST)

        user.seedCnt += int(seeds_for_purchase)
        user.save()
        return Response({'message': '씨앗 구매 성공!', '씨앗 개수': user.seedCnt})


# counting amount of seeds
class GetSeedsCount(APIView):
    def get(self, request, *args, **kwargs):
        user = request.user
        return Response({'씨앗 개수': user.seedCnt})


class BooksViewSet(viewsets.ModelViewSet):
    queryset = Books.objects.all()
    serializer_class = BooksSerializer

    @action(detail=False, methods=['post'])
    def generate_books(self, request):
        title = request.data.get('title')
        image_url = request.data.get('image_url')

        new_book = Books(bookName=title, coverImg=image_url)
        new_book.save()

        return Response({
            'message': 'new book created successfully',
            'book_id': new_book.id,
            'book_name': new_book.bookName,
            'book_coverImg': new_book.coverImg
        }, status=status.HTTP_201_CREATED)


class MyLibraryViewSet(viewsets.ModelViewSet):
    queryset = MyLibrary.objects.all()
    serializer_class = MyLibrarySerializer

    class Book_Search_ViewSet(APIView):
        def get(self, member_id):
            try:
                books = Books.objects.filter(mylibrary__user_id=member_id)
                serializer = BooksSerializer(books, many=True)
                return Response(serializer.data, status=status.HTTP_200_OK)
            except Members.DoesNotExist:
                return Response({'error': 'Member not found'}, status=status.HTTP_404_NOT_FOUND)


class DraftViewSet(viewsets.ModelViewSet):
    queryset = Draft.objects.all()
    serializer_class = DraftSerializer

    @action(detail=True, methods=['post'])
    def choose_diff(self, request, pk=None):
        draft = self.get_object()

        diff_count = request.data.get('diff_Count')
        if diff_count is None:
            return Response({'error': 'diff_Count is required.'}, status=status.HTTP_400_BAD_REQUEST)
        try:
            diff_count = int(diff_count)
            if not 2 <= diff_count <= 4:
                raise ValueError
        except ValueError:
            return Response({'error': 'invalid diff_Count. '
                                    'must be an integer between 3 and 5.'}, status=status.HTTP_400_BAD_REQUEST)

        draft.diff = diff_count
        draft.save()

        return Response({'message': "diff_Count updated successfully", 'diff': diff_count})

    @action(detail=True, methods=['get'])
    def create_book_cover(self, request, pk=None):
        draft = self.get_object()
        pages = DraftPage.objects.filter(draft=draft).order_by('pageNum')
        full_text = ' '.join(page.pageContent for page in pages)
        try:
            image_url = generate_image(full_text)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        return Response({'draft_id': draft.id, 'image_url': image_url})

    @action(detail=True, methods=['get'])
    def get_page_content(self, request, pk=None):
        draft = self.get_object()
        draft_pages = DraftPage.objects.filter(draft=draft).order_by('pageNum')
        contents = [page.pageContent for page in draft_pages]

        return Response({
            'draft_id': draft.id,
            'pages_content': contents
        }, status=status.HTTP_200_OK)

    @action(detail=True, methods=['post'])
    def save_genre(self, request, pk=None):
        draft = self.get_object()
        selected_genre = request.data.get('genre')
        draft.genre = selected_genre
        draft.save()

        return Response({'message': 'Selected answer saved successfully', 'page_id': draft.id,
                        'page_content': draft.genre})

    @action(detail=False, methods=['post'], url_path='genre')
    def genre(self, request):
        nickname = request.data.get('nickname')
        genre = request.data.get('genre')

        if not nickname or not genre:
            return Response({"error": "닉네임과 장르를 모두 제공해야 합니다."}, status=status.HTTP_400_BAD_REQUEST)

        try:
            member = Members.objects.get(nickname=nickname)
        except Members.DoesNotExist:
            return Response({"error": "회원을 찾을 수 없습니다."}, status=status.HTTP_404_NOT_FOUND)

        latest_draft = Draft.objects.filter(user=member).latest('savedAt')
        latest_draft.genre = genre
        latest_draft.save()

        return Response({"message": "장르가 성공적으로 업데이트되었습니다."}, status=status.HTTP_200_OK)

    @action(detail=False, methods=['post'])
    def writer(self, request):
        nickname = request.data.get('nickname')
        writer_name = request.data.get('writer')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # Draft 인스턴스 생성
        draft_data = {
            'user': member.id,
            'writer': writer_name,
            'drawSty': request.data.get('drawSty', 0),
            'diff': request.data.get('diff', 0)
        }
        draft_serializer = DraftSerializer(data=draft_data)
        if draft_serializer.is_valid():
            draft_serializer.save()
            return Response(draft_serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(draft_serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class IntroViewSet(viewsets.ModelViewSet):
    queryset = Intro.objects.all()
    serializer_class = IntroSerializer

    @action(detail=False, methods=['post'])
    def generate_subject(self, request):
        genre = request.data.get('genre')
        user_id = request.data.get('user_id')
        member = get_object_or_404(Members, id=user_id)
        if not genre:
            return Response({'error': 'Genre is required'}, status=status.HTTP_400_BAD_REQUEST)
        subject_prompt = f"장르 {genre}에 기반한 독특한 이야기 주제를 3개만 생성해."
        new_draft = Draft.objects.create(user=member)
        try:
            responses = generate(subject_prompt)
            if isinstance(responses, str):
                images = []
                subjects = responses.split('\n')
                for response in subjects:
                    if response.strip():
                        image_url = generate_image(response)
                        images.append(image_url)
                return Response({'topics': subjects, 'images': images, 'draft_id': new_draft.id})
            else:
                return Response({'error': 'Invalid response format'},
                                status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

    @action(detail=False, methods=['post'])
    def create_intro_content(self, request):
        draft_id = request.data.get('draft_id')
        user_id = request.data.get('user_id')
        diff = int(request.data.get('diff'))
        intro_mode = request.data.get('introMode')
        selected_subject = request.data.get('selected_subject')
        if not draft_id:
            return Response({'error': 'Draft ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        if not user_id:
            return Response({'error': 'User ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        draft = get_object_or_404(Draft, pk=draft_id)
        name_prompt = f"주제 {selected_subject}를 기반해서 주인공의 이름 {diff}개만 생성해."
        try:
            response = generate(name_prompt)
            if isinstance(response, str):
                protagonist_names = response.split('\n')
            else:
                return Response({'error': 'Invalid response format'},
                                status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        intro = Intro(draft=draft, user_id=user_id, introMode=intro_mode,
                    subject=selected_subject, IntroContent=protagonist_names)
        intro.save()
        return Response({'intro_id': intro.id, 'subject': selected_subject,
                        'intro_content': protagonist_names})

    @action(detail=False, methods=['post'])
    def recreate_intro_content(self, request):
        draft_id = request.data.get('draft_id')
        user_id = request.data.get('user_id')
        diff = int(request.data.get('diff'))
        intro_mode = request.data.get('introMode')
        selected_subject = request.data.get('selected_subject')
        if not draft_id:
            return Response({'error': 'Draft ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        if not user_id:
            return Response({'error': 'User ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        draft = get_object_or_404(Draft, pk=draft_id)
        data = Intro.objects.all()
        data.delete()

        name_prompt = f"주제 {selected_subject}를 기반해서 주인공의 이름 {diff}개만 생성해."
        try:
            response = generate(name_prompt)
            if isinstance(response, str):
                protagonist_names = response.split('\n')
            else:
                return Response({'error': 'Invali   d response format'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        intro = Intro(draft=draft, user_id=user_id, introMode=intro_mode,
                    subject=selected_subject, IntroContent=protagonist_names)
        intro.save()
        return Response({'intro_id': intro.id, 'subject': selected_subject,
                        'intro_content': protagonist_names})

    name = ''
    gender = ''
    age = 0
    personality = ''
    story = ''

    @action(detail=False, methods=['post'])
    def basicInfo(self, request):
        IntroViewSet.name = request.data.get('name')
        IntroViewSet.gender = request.data.get('gender')
        IntroViewSet.age = request.data.get('age')
        IntroViewSet.personality = request.data.get('personality')
        IntroViewSet.story = request.data.get('story')
        return Response({'message': "기본 정보 입력 완료",
                        '기본정보':
                            {'name': IntroViewSet.name,
                            'gender': IntroViewSet.gender,
                            'age': IntroViewSet.age,
                            'personality': IntroViewSet.personality,
                            'story': IntroViewSet.story}}, status=status.HTTP_201_CREATED)

    @action(detail=False, methods=['post'])
    def firstquestion(self, request):
        nickname = request.data.get('nickname')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # member가 작성한 최신 draft 조회
        draft = Draft.objects.filter(user=member).order_by('-savedAt').first()

        if not draft:
            return Response({"error": "Draft not found"}, status=status.HTTP_404_NOT_FOUND)

        # draft로 genre 조회
        genre = draft.genre

        client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a fairy tale writer for kids and teenager."},
                # 당신은 아이들과 십대들을 위한 동화 작가입니다.
                {
                    "role": "user",
                    "content": "I will try to create a fairy tale creation service."  # 동화 제작 서비스를 만들겁니다.
                    "Please write a story about the beginning of a fairy tale in 3 sentences based on the genre of the fairy tale,"  # 동화의 장르를 바탕으로 동화의 시작에 대한 이야기를 3개의 문장으로 작성해 주세요.
                    "Name of the main character, gender, personality, age and a must-see story. 2~3줄 정도의 짧은 이야기를 생성해주세요. 그리고 다음 이야기 진행을 위한 질문을 작성해주세요."
                    # 주인공 이름, 성별, 성격, 나이 그리고 꼭 들어갔으면 하는 이야기, 그리고 다음 동화 이야기를 위한 짧은 질문도 같이 작성해주세요.
                    # the name of the main character, gender, personality, age, and the story to enter. And please write a short question for the next story of the fairy tale.
                },
                {
                    "role": "user",
                    "content": f"The genre is {genre}, the main character's name is {IntroViewSet.name}, the gender is {IntroViewSet.gender}, the personality is {IntroViewSet.personality}, and he is {IntroViewSet.age} years old."
                    f"the story you wish to enter is {IntroViewSet.story}."  # 장르는 {genre}, 주인공의 이름은 {name}, 성별은 {gender}, 성격은 {personality}, 나이는 {age}. 꼭 들어갔으면 하는 이야기는 {story}.
                    "답변을 한글로 바꿔주세요."
                },

                {"role": "system", "content": "You are a fairy tale writer for kids."},#당신은 아이들과 십대들을 위한 동화 작가입니다.
                {
                    "role": "user", "content": "I will try to create a fairy tale creation service."#동화 제작 서비스를 만들겁니다.
                    "Please write a story about the beginning of a fairy tale in 3 sentences based on the genre of the fairy tale,"#동화의 장르를 바탕으로 동화의 시작에 대한 이야기를 3개의 문장으로 작성해 주세요.
                    "Name of the main character, gender, personality, age and a must-see story. 2~3줄 정도의 짧은 이야기를 생성해주세요. 그리고 다음 이야기 진행을 위한 질문을 작성해주세요."#주인공 이름, 성별, 성격, 나이 그리고 꼭 들어갔으면 하는 이야기, 그리고 다음 동화 이야기를 위한 짧은 질문도 같이 작성해주세요. 
                    "짧은 이야기를 생성해주고 한 칸 띄워서 '다음 이야기를 위한 질문:'의 형태로 작성해주세요."
                },
                {
                    "role": "user", "content": f"The genre is {genre}, the main character's name is {IntroViewSet.name}, the gender is {IntroViewSet.gender}, the personality is {IntroViewSet.personality}, and he is {IntroViewSet.age} years old."
                    f"the story you wish to enter is {IntroViewSet.story}."#장르는 {genre}, 주인공의 이름은 {name}, 성별은 {gender}, 성격은 {personality}, 나이는 {age}. 꼭 들어갔으면 하는 이야기는 {story}.
                    "답변을 한글로 바꿔주세요."
                },
            ]
        )

        intro_data = {
            'draft': draft.id,
            'user': member.id,
            'introMode': 1,
            'subject': IntroViewSet.story,
            'IntroContent': completion.choices[0].message.content
        }
        intro_serializer = IntroSerializer(data=intro_data)
        if intro_serializer.is_valid():
            intro_serializer.save()
            return Response(intro_serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(intro_serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @action(detail=False, methods=['post'])
    def userchat(self, request):
        nickname = request.data.get('nickname')
        chat = request.data.get('chat')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # 해당 멤버와 연관된 intro 중에서 가장 ID 값이 큰 intro를 조회
        latest_intro_id = Intro.objects.filter(user=member).aggregate(Max('id'))['id__max']
        latest_intro = Intro.objects.filter(id=latest_intro_id).first()

        if latest_intro:
            # IntroContent 업데이트
            latest_intro.IntroContent += "/n" + chat + "/n"
            latest_intro.save()
            return Response({'message': 'IntroContent updated successfully'}, status=201)
        else:
            return Response({'error': 'No intro instance found for the member'}, status=404)

    @action(detail=False, methods=['post'])
    def middlequestion(self, request):
        nickname = request.data.get('nickname')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # member가 작성한 최신 draft 조회
        draft = Draft.objects.filter(user=member).order_by('-savedAt').first()

        # intro 조회
        intro = Intro.objects.filter(user_id=member.id, draft_id=draft.id).aggregate(Max('id'))
        max_intro_id = intro['id__max']

        if max_intro_id is not None:
            intro_data = Intro.objects.get(id=max_intro_id)
            contents = intro_data.IntroContent
        else:
            return Response({'message': 'No intro data found for the given member and draft.'}, status=status.HTTP_404_NOT_FOUND)
        
        client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a fairy tale writer for kids and teenager."},#당신은 아이들과 십대들을 위한 동화 작가입니다.
                {
                    "role": "user", "content": f"{contents}"
                    "Please write a story in 3 sentences"
                    "제공되는 내용과 다음 이야기를 위한 질문에 대한 답변에 맞춰 자연스럽게 이어지게 2~3줄 정도의 짧은 이야기를 생성해주세요. 그리고 다음 이야기 진행을 위한 질문을 작성해주세요."#주인공 이름, 성별, 성격, 나이 그리고 꼭 들어갔으면 하는 이야기, 그리고 다음 동화 이야기를 위한 짧은 질문도 같이 작성해주세요. 
                    "짧은 이야기를 생성해주고 한 칸 띄워서 '다음 이야기를 위한 질문:'의 형태로 작성해주세요."
                    "답변을 한글로 바꿔주세요."
                },
            ]
        )

        # 해당 멤버와 연관된 intro 중에서 가장 ID 값이 큰 intro를 조회
        latest_intro_id = Intro.objects.filter(user=member).aggregate(Max('id'))['id__max']
        latest_intro = Intro.objects.filter(id=latest_intro_id).first()

        if latest_intro:
            # IntroContent 업데이트
            latest_intro.IntroContent += "/n" + completion.choices[0].message.content + "/n"
            latest_intro.save()
            return Response({
                'message': 'IntroContent updated successfully.',
                '만들어진 이야기': completion.choices[0].message.content
            }, status=status.HTTP_201_CREATED)
        else:
            return Response({'error': 'No intro instance found for the member'}, status=status.HTTP_404_NOT_FOUND)

    @action(detail=False, methods=['post'])
    def endingquestion(self, request):
        nickname = request.data.get('nickname')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # member가 작성한 최신 draft 조회
        draft = Draft.objects.filter(user=member).order_by('-savedAt').first()

        # intro 조회
        intro = Intro.objects.filter(user_id=member.id, draft_id=draft.id).aggregate(Max('id'))
        max_intro_id = intro['id__max']

        if max_intro_id is not None:
            intro_data = Intro.objects.get(id=max_intro_id)
            contents = intro_data.IntroContent
        else:
            return Response({'message': 'No intro data found for the given member and draft.'}, status=status.HTTP_404_NOT_FOUND)
        
        client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a fairy tale writer for kids and teenager."},#당신은 아이들과 십대들을 위한 동화 작가입니다.
                {   
                    "role": "user", "content": f"{contents}"
                    "Please write a story in 3 sentences"
                    "제공되는 내용과 다음 이야기를 위한 질문에 대한 답변에 맞춰 자연스럽게 이어지게 2~3줄 정도의 짧은 이야기를 생성해주세요. 그리고 이야기의 종료를 위한 질문을 작성해주세요."#주인공 이름, 성별, 성격, 나이 그리고 꼭 들어갔으면 하는 이야기, 그리고 다음 동화 이야기를 위한 짧은 질문도 같이 작성해주세요. 
                    "짧은 이야기를 생성해주고 한 칸 띄워서 '엔딩을 위한 질문:'의 형태로 작성해주세요."
                    "답변을 한글로 바꿔주세요."
                },
            ]
        )
        
        # 해당 멤버와 연관된 intro 중에서 가장 ID 값이 큰 intro를 조회
        latest_intro_id = Intro.objects.filter(user=member).aggregate(Max('id'))['id__max']
        latest_intro = Intro.objects.filter(id=latest_intro_id).first()

        if latest_intro:
            # IntroContent 업데이트
            latest_intro.IntroContent += "/n" + completion.choices[0].message.content
            latest_intro.save()
            return Response({
                'message': 'IntroContent updated successfully.',
                '만들어진 이야기': completion.choices[0].message.content
            }, status=status.HTTP_201_CREATED)
        else:
            return Response({'error': 'No intro instance found for the member'}, status=status.HTTP_404_NOT_FOUND)

    @action(detail=False, methods=['post'])
    def bookstory(self, request):
        nickname = request.data.get('nickname')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # member가 작성한 최신 draft 조회
        draft = Draft.objects.filter(user=member).order_by('-savedAt').first()

        # intro 조회
        intro = Intro.objects.filter(user_id=member.id, draft_id=draft.id).aggregate(Max('id'))
        max_intro_id = intro['id__max']

        if max_intro_id is not None:
            intro_data = Intro.objects.get(id=max_intro_id)
            contents = intro_data.IntroContent
        else:
            return Response({'message': 'No intro data found for the given member and draft.'}, status=status.HTTP_404_NOT_FOUND)

        client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a fairy tale writer for kids and teenager."},#당신은 아이들과 십대들을 위한 동화 작가입니다.
                {   
                    "role": "user", "content": f"{contents}"
                    "위의 내용을 이용해서 8페이지 분량의 동화책을 작성해줘. 그런데 위의 내용 중에서 질문과 질문에 대한 답변을 그대로 동화책에 넣지 말고 자연스러운 이야기로 바꿔줘."
                    "그리고 페이지 별로 띄어쓰기로 구분해줘."
                    "답변을 한글로 바꿔줘."
                },
            ]
        )

        bookstory = completion.choices[0].message.content

        # 줄바꿈 문자를 기준으로 내용 분리
        bookstory_pages = bookstory.split('\n\n')

        # DraftPage에 내용을 저장
        for page_num, page_content in enumerate(bookstory_pages, start=1):
            DraftPage.objects.create(
                draft=draft,
                user=member,
                pageNum=page_num,
                pageContent=page_content
            )

        return Response({
            "message": "동화 이야기가 생성되어 저장되었습니다.",
            "동화이야기": {f"page_{i+1}": content for i, content in enumerate(bookstory_pages)}
        }, status=status.HTTP_201_CREATED)


class DraftPageViewSet(viewsets.ModelViewSet):
    queryset = DraftPage.objects.all()
    serializer_class = DraftPageSerializer

    @action(detail=False, methods=['post'])
    def make_draft_page(self, request):
        draft_id = request.data.get('draft_id')
        user_id = request.data.get('user_id')
        diff = int(request.data.get('diff'))
        draft = get_object_or_404(Draft, pk=draft_id)
        selected_subject = request.data.get('selected_subject')
        intro_content = request.data.get('intro_content')
        if not draft_id:
            return Response({'error': 'Draft ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        if not user_id:
            return Response({'error': 'User ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        latest_page = DraftPage.objects.filter(draft=draft).order_by('-pageNum').first()
        if latest_page:
            total_pages = latest_page.pageNum + 1
        else:
            total_pages = 1

        if total_pages == 1:
            alpha_question_prompt = (f"이야기의 주제인 {selected_subject}와 "
                                     f"이야기의 주인공 이름 {intro_content}을 보고, "
                                     f"주인공에 대한 성격 {diff}개를 단답형으로 생성해.")
            try:
                response = generate(alpha_question_prompt)
                if isinstance(response, str):
                    alpha_answer = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            new_page = DraftPage.objects.create(draft=draft, user_id=user_id, pageNum=total_pages)

            return Response({
                'question': intro_content+"는 어떤 성격이야?",
                'answers': alpha_answer,
                'page_num': total_pages,
                'page_id': new_page.id
            })

        if total_pages == 2:
            alpha_question_prompt = (f"이야기의 주제인 {selected_subject}과"
                                     f"이야기의 주인공 이름 {intro_content}을 참고해서"
                                     f"주인공이 살고있는 장소 {diff}개를 단답형으로 생성해."
                                     f"주인공의 이름은 언급하지 말고 주인공이 살고있는 장소만을 생성해.")
            try:
                response = generate(alpha_question_prompt)
                if isinstance(response, str):
                    alpha_answer = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            new_page = DraftPage.objects.create(draft=draft, user_id=user_id, pageNum=total_pages
                                                )

            return Response({
                'question': intro_content+"는 어떤 장소에서 살고 있어?",
                'answers': alpha_answer,
                'page_num': total_pages,
                'page_id': new_page.id
            })

        if 7 > total_pages > 2:
            context = ' '.join(
                [page.pageContent for page in DraftPage.objects.filter(draft=draft).order_by('pageNum')])
            first_question_prompt = (f"지금까지의 줄거리야 : {context}. 이를 기반으로, "
                                     f"이야기를 전개시키기 위해 이야기와 관련된 질문을 한가지만 해.")

            try:
                response = generate(first_question_prompt)
                if isinstance(response, str):
                    first_question = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            second_question_prompt = (f"지금까지의 줄거리 {context}와 이야기 관련 질문 {first_question}을 보고, "
                                      f"그 질문에 부합하면서 창의적인 답변 {diff}개를 단답형으로 생성해.")
            try:
                response = generate(second_question_prompt)
                if isinstance(response, str):
                    semi_final_answer = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            new_page = DraftPage.objects.create(draft=draft, user_id=user_id,
                                                pageNum=total_pages)

            return Response({
                'next_question': first_question,
                'answers': semi_final_answer,
                'page_num': total_pages,
                'page_id': new_page.id
            })
        else:
            Response({'error' : 'Invalid page number'}, status=status.HTTP_400_BAD_REQUEST)
    @action(detail=False, methods=['post'])
    def finish_draft_page(self, request):
        draft_id = request.data.get('draft_id')
        user_id = request.data.get('user_id')
        diff = int(request.data.get('diff'))
        draft = get_object_or_404(Draft, pk=draft_id)
        latest_page = DraftPage.objects.filter(draft=draft).order_by('-pageNum').first()
        total_pages = latest_page.pageNum + 1
        if not draft_id:
            return Response({'error': 'Draft ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        if not user_id:
            return Response({'error': 'User ID is required'}, status=status.HTTP_400_BAD_REQUEST)

        context = ' '.join(
            [page.pageContent for page in DraftPage.objects.filter(draft=draft).order_by('pageNum')])

        if 9 > total_pages > 6:
            final_question_prompt = (f"지금까지의 줄거리야 : {context}. 이를 기반으로, "
                                     f"이야기를 마무리하기 위한 질문을 한가지만 해.")
            try:
                response = generate(final_question_prompt)
                if isinstance(response, str):
                    final_question = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            final_answer_prompt = (f"지금까지의 줄거리 {context}와 "
                                   f"동화를 마무리할 질문 {final_question}을 보고, "
                                   f"그 질문에 부합하면서 창의적인 답변 {diff}개를 단답형으로 생성해.")
            try:
                response = generate(final_answer_prompt)
                if isinstance(response, str):
                    final_answer = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            new_page = DraftPage.objects.create(draft=draft, user_id=user_id,
                                                pageNum=total_pages)

            return Response({
                'final_question': final_question,
                'answers': final_answer,
                'page_num': total_pages,
                'page_id': new_page.id
            })

        if total_pages == 9:
            final_question_prompt = (f"지금까지의 줄거리야 : {context}."
                                     f"이야기를 끝내기 위한 질문을 한가지만 해."
                                     f"이 질문 이후로 동화는 끝나므로, 신중하게 답변해.")
            try:
                response = generate(final_question_prompt)
                if isinstance(response, str):
                    final_question = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            final_answer_prompt = (f"지금까지의 줄거리 {context}와 "
                                   f"동화를 끝내기 위한 질문 {final_question}을 보고, "
                                   f"그 질문에 부합하면서 창의적인 답변 {diff}개를 단답형으로 생성해."
                                   f"이 선택지 이후로 동화는 끝나므로, 신중하게 답변해.")
            try:
                response = generate(final_answer_prompt)
                if isinstance(response, str):
                    final_answer = response.split('\n')
                else:
                    return Response({'error': 'Invalid response format'},
                                    status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

            new_page = DraftPage.objects.create(draft=draft, user_id=user_id,
                                                pageNum=total_pages)

            return Response({
                'final_question': final_question,
                'answers': final_answer,
                'page_num': total_pages,
                'page_id': new_page.id
            })

    @action(detail=False, methods=['post'])
    def save_selected_answer(self, request):
        draftpage_id = request.data.get('draftpage_id')
        question = request.data.get('question')
        selected_answer = request.data.get('selected_answer')

        if not draftpage_id:
            return Response({'error': 'Page ID is required'}, status=status.HTTP_400_BAD_REQUEST)
        if not selected_answer:
            return Response({'error': 'Selected answer is required'}, status=status.HTTP_400_BAD_REQUEST)

        draft_page = get_object_or_404(DraftPage, pk=draftpage_id)
        unite_prompt = f"질문 {question}과 답변 {selected_answer}를 기반으로 한 페이지 분량의 동화 내용 일부를 완성해."

        try:
            response = generate(unite_prompt)
            if isinstance(response, str):
                new_page = response.split('\n')
            else:
                return Response({'error': 'Invalid response format'},
                                status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        draft_page.pageContent = new_page
        draft_page.save()

        return Response({'message': 'Selected answer saved successfully', 'page_id': draft_page.id,
                         'page_content': draft_page.pageContent})

    @action(detail=False, methods=['post'])
    def create_content_image(self, request):
        page_id = request.data.get('page_id')
        if not page_id:
            return Response({'error': 'Page ID is required'}, status=status.HTTP_400_BAD_REQUEST)

        draft_page = get_object_or_404(DraftPage, pk=page_id)
        image_prompt = f"이야기 내용: '{draft_page.pageContent}'. 이 내용을 바탕으로 상상력을 자극하는 이미지를 생성해."

        try:
            image_url = generate_image(image_prompt)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        draft_page.pageImage = image_url
        draft_page.save()

        return Response({'page_id': draft_page.id, 'image_url': image_url})

    @action(detail=False, methods=['post'])
    def recreate_content_image(self, request):
        page_id = request.data.get('page_id')
        if not page_id:
            return Response({'error': 'Page ID is required'}, status=status.HTTP_400_BAD_REQUEST)

        draft_page = get_object_or_404(DraftPage, pk=page_id)

        if draft_page.pageImage:
            draft_page.pageImage = None
            draft_page.save()

        image_prompt = f"이야기 내용: '{draft_page.pageContent}'. 이 내용을 바탕으로 상상력을 자극하는 이미지를 생성해."
        try:
            image_url = generate_image(image_prompt)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

        draft_page.pageImage = image_url
        draft_page.save()

        return Response({'page_id': draft_page.id, 'image_url': image_url})


    @action(detail=False, methods=['post'])
    def bookname(self, request):
        nickname = request.data.get('nickname')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # member가 작성한 최신 draft 조회
        draft = Draft.objects.filter(user=member).order_by('-savedAt').first()

        # draft와 user에 해당하는 draftpage 데이터 조회 및 합치기
        draftpages = DraftPage.objects.filter(draft=draft, user=member)
        combined_content = ' '.join([page.pageContent for page in draftpages])

        client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a fairy tale writer for kids and teenager."},#당신은 아이들과 십대들을 위한 동화 작가입니다.
                {   
                    "role": "user", "content": f"{combined_content}"
                    "위의 동화책 내용과 어울리는 제목을 하나만 생성해줘."
                    "답변을 한글로 바꿔줘."
                },
            ]
        )

        return Response({"message" : "동화 제목이 생성되었습니다.", "동화 제목":{completion.choices[0].message.content}}, status=status.HTTP_201_CREATED)

    @action(detail=False, methods=['post'])
    def bookshortstory(self, request):
        nickname = request.data.get('nickname')

        # 닉네임으로 멤버 조회
        member = get_object_or_404(Members, nickname=nickname)

        # member가 작성한 최신 draft 조회
        draft = Draft.objects.filter(user=member).order_by('-savedAt').first()

        # draft와 user에 해당하는 draftpage 데이터 조회 및 합치기
        draftpages = DraftPage.objects.filter(draft=draft, user=member)
        combined_content = ' '.join([page.pageContent for page in draftpages])

        client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a fairy tale writer for kids and teenager."},#당신은 아이들과 십대들을 위한 동화 작가입니다.
                {   
                    "role": "user", "content": f"{combined_content}"
                    "위의 동화책 내용의 줄거리를 2문장으로 짧게 생성해줘."
                    "답변을 한글로 바꿔줘."
                },
            ]
        )

        return Response({"message" : "동화 줄거리가 생성되었습니다.", "동화 줄거리":{completion.choices[0].message.content}}, status=status.HTTP_201_CREATED)

class FeedBackViewSet(viewsets.ModelViewSet):
    queryset = FeedBack.objects.all()
    serializer_class = FeedBackSerializer


class FollowersViewSet(viewsets.ModelViewSet):
    queryset = Followers.objects.all()
    serializer_class = FollowersSerializer

    @action(detail=True, methods=['get'])
    def get_followers(self, request, pk=None):
        try:
            followers_instance=Followers.objects.get(user_id=pk)
            return Response({
                'following' : followers_instance.following,
                'followers' : followers_instance.follower
            })
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

    @action(detail=True, methods=['get'])
    def do_follow(self, request, pk=None):
        follow_user = Members.objects.get(pk=request.user.id)
        following_user = Members.objects.get(pk=pk)

        try:
            follow_instance, _ = Followers.objects.get_or_create(user=follow_user)
            following_instance, _ = Followers.objects.get_or_create(user=following_user)

            follow_instance.following = F('following') + 1
            follow_instance.save()

            following_instance.follower = F('follower') + 1
            following_instance.save()

            return Response({'message': 'Follow successes'}, status=status.HTTP_200_OK)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

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
