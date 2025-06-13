FROM python:3.10.17-slim-bullseye

WORKDIR /app

COPY requirements.txt build/requirements.txt

RUN apt-get update -y && \
    apt-get install -y build-essential libpq-dev gcc

RUN python -m pip install --no-cache-dir --upgrade -r build/requirements.txt

COPY ml/ ml/

COPY main.py .
COPY ir.py .

EXPOSE 8080

ENTRYPOINT ["python"]

CMD ["main.py"]

