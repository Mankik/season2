from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()
client = OpenAI()

# Print model list
# print(client.models.list())
# print("")

# https://platform.openai.com/docs/api-reference/chat/create?lang=python
MODEL_NAME = "gpt-3.5-turbo-1106"
JSON_MODE = " to output JSON"
completion = client.chat.completions.create(
  model="gpt-3.5-turbo-1106",
  response_format={"type": "json_object"},
  messages=[
    {
      "role": "system",
      "content": """You are a helpful assistant designed to output JSON.
        Input and answers are basically in Korean.""",
    },
    {"role": "user", "content": "오늘 저녁을 추천해줘!!"},
  ],
)

# https://community.openai.com/t/how-can-i-get-free-trial-credits/26742/27
# print(completion.choices[0].message)
print(completion)
