# Genius

AI fairytale maker Project

## Techknowlogies Used

- python : 3.10.0
- django : 5.0.1
- mysqlclient : 2.2.1
- pyproject : 1.0.0
- poetry : 1.7.1

## Project Structure
├─ servers

│  └─ genius

│     ├─ djangoProject

│     │  ├─ asgi.py

│     │  ├─ settings.py

│     │  ├─ urls.py

│     │  └─ wsgi.py

│     ├─ Dockerfile

│     ├─ podman-compose.yml

│     ├─ poetry.lock

│     ├─ poetry.toml

│     ├─ pyproject.toml

│     ├─ README.md

│     └─ src

│        ├─ .python-version

│        ├─ manage.py

│        └─ __init__.py

## How to use
First run : docker-compose -f podman-compose.yml up --build -d
Run :  docker-compose -f podman-compose.yml up -d
Exit :  docker-compose -f podman-compose.yml down
