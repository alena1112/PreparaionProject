#!/bin/sh

docker_image=docker-jewelry-db

build_image() {
  echo build $1
  docker build -t $1 .
}

push_image() {
  echo push $1
  docker push $1
}

#run_image() {
#  echo run $1
#  docker run -d --name docker-jewelry-db -p 5432:5432 docker-jewelry-db
#}

# $1 - command
# $2 - version

case "$1" in
    build)
        build_image $docker_image:$2
      ;;
    push)
        push_image $docker_image:$2
      ;;
    *)
      echo "An error occurred. Build or push command expected"
      exit 1
      ;;
esac
