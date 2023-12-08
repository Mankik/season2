from pprint import pprint

from dotenv import load_dotenv
from langchain.chat_models import ChatOpenAI
from langchain.prompts import PromptTemplate
from langchain.schema import AIMessage, HumanMessage, SystemMessage
from utils.date import today

load_dotenv()

# https://api.python.langchain.com/en/stable/chat_models/langchain.chat_models.openai.ChatOpenAI.html#langchain.chat_models.openai.ChatOpenAI
# 기타 ChatGPT의 값을 넣고 싶다면 model_kwargs 이용 https://platform.openai.com/docs/api-reference/chat/create
# model_kwargs = { "top_p": 0.7 }
chat = ChatOpenAI()
today_message = PromptTemplate.from_template("오늘은 {date}이야. 어울리는 점심을 추천해줘.")

messages = [
  SystemMessage(
    content=" ".join(
      [
        "You are an AI bot that recommends launch.",
        "Present users with five lists, each line starting with a '-'.",
        "Questions and answers are primarily conducted in Korean.",
      ]
    )
  ),
  HumanMessage(content=today_message.format(date=today())),
  AIMessage(
    content="당신이 어디에 있는지 알려주시겠어요? 지역에 따라 점심 추천이 달라질 수 있어서요."
  ),
  HumanMessage(content="대한민국의 안양시 안양아이에스비즈타워센트럴의 음식점에서 고려해주세요."),
]
completion = chat.invoke(messages)  # type: ignore
pprint(completion.content)
