# circulo-core

## Install Instructions

### Install MongoDB (OSX)

```
brew update
brew install mongodb
```

### Install SBT

```
brew install sbt
```

### Install MonboDB
## Ubuntu
```
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo "deb http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list
sudo apt-get update
sudo apt-get install -y mongodb-org
sudo service mongod start
```


### Run the tests

```
sbt update
sbt clean test:clean test
```

