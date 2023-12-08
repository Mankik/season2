from datetime import datetime


def today():
  now = datetime.now()
  return now.strftime("%Y년 %m월 %d일")
