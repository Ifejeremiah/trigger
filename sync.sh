#!/bin/bash

PREFERRED_BRANCH=master
APP_NAME=redirecter
INNER_PORT=8080
OUTER_PORT=8080


echo pulling updates...
git switch $PREFERRED_BRANCH
git pull origin $PREFERRED_BRANCH

NEW_GIT_COMMIT=$(git rev-parse --short @)
OLD_GIT_COMMIT=$(git log -n 2 --pretty=format:"%h" | tail -1)
RUNNING_CONTAINER=$(docker ps -a -q --filter ancestor=$APP_NAME:$OLD_GIT_COMMIT)

echo updating docker containers...
docker kill $RUNNING_CONTAINER
sleep 5
docker rm $RUNNING_CONTAINER
docker rmi $APP_NAME:$OLD_GIT_COMMIT

docker build -t $APP_NAME:$NEW_GIT_COMMIT .
docker run -d -p $OUTER_PORT:$OUTER_PORT $APP_NAME:$NEW_GIT_COMMIT
echo complete.