### Information ################################################################
## Base Images:
# https://hub.docker.com/_/python

## Build:
# podman build -t genius-server --target=runtime .

## Run:
# podman run -p localhost:8000:8000 genius-server:latest

### Builder ####################################################################
FROM python:3.10-bullseye AS builder
LABEL authors="JY"

# https://python-poetry.org/docs/configuration/#using-environment-variables
ENV POETRY_NO_INTERACTION=1 \
    POETRY_VIRTUALENVS_IN_PROJECT=1 \
    POETRY_VIRTUALENVS_CREATE=1 \
    POETRY_CACHE_DIR=/tmp/poetry_cache \
    PYTHONBUFFERED=1

WORKDIR /app

COPY pyproject.toml poetry.toml poetry.lock ./

RUN pip install poetry==1.7.1
RUN --mount=type=cache,target=$POETRY_CACHE_DIR \
    poetry install --no-root --no-interaction --no-ansi

### Runtime ####################################################################
FROM python:3.10-slim-bullseye AS runtime

ENV VIRTUAL_ENV=/app/.venv \
    PATH="/app/.venv/bin:$PATH" \
    PYTHONBUFFERED=1

COPY --from=builder ${VIRTUAL_ENV} ${VIRTUAL_ENV}

WORKDIR /app
COPY src ./src
COPY manage.py ./manage.py
COPY genius ./genius
COPY geniusback ./geniusback
RUN apt-get update && apt-get install -y default-libmysqlclient-dev gcc


EXPOSE 8000

ENTRYPOINT python manage.py runserver 0.0.0.0:8000
