#!/bin/bash

if [ $1 =  "b" ]
 then

docker build . -t test;
else 
	docker run -p 80:80 -ti test
fi
