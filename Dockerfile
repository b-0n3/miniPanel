FROM debian:buster

EXPOSE   80 2082
RUN apt-get update
RUN    apt-get install -y  apt-transport-https ca-certificates
RUN    apt-get install -y gnupg2 ;\
       wget https://nginx.org/keys/nginx_signing.key ; \
       apt-key   add nginx_signing.key  ;\
       apt-get -y update ; \
       apt-get -y install nginx
COPY ./srcs/templ /var/www/html/templ
COPY ./srcs/javaInstaller.sh  /tmp/javaInstaller.sh
RUN chmod +x /tmp/javaInstaller.sh 
RUN bash /tmp/javaInstaller.sh
RUN apt-get install -y wget vim lsb-release gnupg
RUN echo "mysqll-apt-config mysql-apt-config/select-server select mysql-5.7" | debconf-set-selections
RUN wget https://repo.mysql.com/mysql-apt-config_0.8.13-1_all.deb

RUN dpkg -i mysql-apt-config_0.8.13-1_all.deb

RUN apt update -y
RUN apt install mysql-server -y
