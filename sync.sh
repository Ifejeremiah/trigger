#!/bin/bash

APP_NAME=$1

if [ $# -eq 0 ]; then
    echo "No arguments supplied"
    exit 1
elif [ ! -f ../$APP_NAME/.cicd ]; then
    echo "No trigger file (.cicd) found"
    exit 1
else
  cd ../$APP_NAME/

  INNER_PORT=$(cat .cicd | xargs | cut -d " " -f 1 | cut -d "=" -f 2)
  OUTER_PORT=$(cat .cicd | xargs | cut -d " " -f 2 | cut -d "=" -f 2)
  PREFERRED_BRANCH=$(cat .cicd | xargs | cut -d " " -f 3 | cut -d "=" -f 2)

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
  docker run -d -p $OUTER_PORT:$INNER_PORT $APP_NAME:$NEW_GIT_COMMIT
  echo complete.
fi
