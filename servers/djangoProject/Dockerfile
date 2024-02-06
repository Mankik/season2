FROM python:3.10.0
LABEL authors="JY"
ENV PYTHONBUFFERED 1
WORKDIR /app
RUN pip install poetry
COPY pyproject.toml poetry.lock ./
RUN poetry config virtualenvs.create false
RUN poetry install
COPY . /app/
CMD poetry run python manage.py runserver 0.0.0.0:8000
