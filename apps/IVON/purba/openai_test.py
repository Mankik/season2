import sys
import base64
import requests
import os
from dotenv import load_dotenv
import json

env_vars = load_dotenv()
api_key = os.getenv("OPENAI_API_KEY")
def encode_image(image_path):
    with open(image_path, "rb") as image_file:
        return base64.b64encode(image_file.read()).decode('utf-8')

def analyze_image(image_path):
    load_dotenv()
    base64_image = encode_image(image_path)

    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {api_key}"
    }

    payload = {
        "model": "gpt-4-vision-preview",
        "messages": [
            {
                "role": "user",
                "content": [
                    {
                        "type": "text",
                        "text": "이 이미지를 요약해서 choises 필드 안에 Title, Summary을 반드시 넣어주고 만약 Location, Start Date, End Date, Bank Account, Charge 에 해당하는 항목이 있다면 넣어서 반환해줘"
                    },
                    {
                        "type": "image_url",
                        "image_url": {
                            "url": f"data:image/jpeg;base64,{base64_image}"
                        }
                    }
                ]
            }
        ],
        "max_tokens": 300
    }

    response = requests.post("https://api.openai.com/v1/chat/completions", headers=headers, json=payload)
    return response.json()

if __name__ == '__main__':
    image_path = sys.argv[1]
    analysis_result = json.dumps(analyze_image(image_path))
    print(analysis_result)
