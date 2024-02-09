FROM python:3.10.0 AS builder
LABEL authors="JY"
ENV PYTHONBUFFERED 1
WORKDIR /app
RUN pip install poetry
COPY pyproject.toml poetry.lock ./
RUN poetry config virtualenvs.create false
RUN poetry install --no-interaction --no-ansi

COPY . /app/

FROM python:3.10.0 AS deployer
COPY --from=builder /app /app/
COPY --from=builder /usr/local/lib/python3.10/site-packages /usr/local/lib/python3.10/site-packages
COPY --from=builder /usr/local/bin /usr/local/bin/

RUN pip install poetry
RUN poetry config virtualenvs.create false

WORKDIR /app

CMD poetry run python manage.py runserver 0.0.0.0:8000
