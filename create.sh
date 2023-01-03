# This script will create a planetarium docker image

docker build -t alexanderhunt/planetarium:stdout .
docker push alexanderhunt/planetarium:stdout
