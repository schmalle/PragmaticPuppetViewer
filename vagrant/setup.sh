#!/bin/sh


sudo locale-gen UTF-8
sudo apt-get -y upgrade

sudo echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | sudo tee /etc/apt/sources.list.d/webupd8team-java.list
sudo echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | sudo tee -a /etc/apt/sources.list.d/webupd8team-java.list
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886
sudo apt-get -y update
sudo apt-get -y install oracle-java8-installer

sudo apt-get -y install puppet
sudo apt-get -y install mysql-server
sudo apt-get -y install git unzip wget curl


cd /tmp
git clone https://github.com/schmalle/PragmaticPuppetViewer.git

wget http://downloads.typesafe.com/typesafe-activator/1.2.10/typesafe-activator-1.2.10-minimal.zip
unzip typesafe-activator-1.2.10-minimal.zip

export PATH=$PATH:/tmp/activator-1.2.10-minimal
cd /tmp/PragmaticPuppetViewer
activator start



