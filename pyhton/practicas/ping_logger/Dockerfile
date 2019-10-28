FROM python:alpine
MAINTAINER daniel
RUN apk add nmap
RUN apk add fping
RUN apk add net-snmp-tools
RUN pip install flask
COPY . /src
WORKDIR /src
EXPOSE 4000
EXPOSE 161
EXPOSE 162
ENTRYPOINT ["python", "src/app.py"]
