@echo off
title MockServerApplication
java -classpath ..\conf;..\lib\* -Dspring.profiles.active=dev MockServerApplication
pause