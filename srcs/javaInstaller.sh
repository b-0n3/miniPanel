
#!/bin/bash

 apt update -y ;  apt install -y wget libasound2 libasound2-data; 
 cat<<EOF >> /etc/apt/sources.list
deb http://ftp.us.debian.org/debian sid main
EOF
 
 apt-get update -y;
 apt-get install -y openjdk-8-jdk
 

java -version;