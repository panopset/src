[home](../README.md)

# Server Deployment Manual

## Requirements

* [Workstation Configuration](workstation.md)
* Obtain [domain](https://domains.squarespace.com) name(s) for each deployment.

## Overview

Panopset is hosted on a [DigitalOcean](https://digitalocean.com) server.

Adjust the instructions, as needed, for your hardware.

## Steps

ssh to your server, with this script

    cd code
    ./s.sh

Do the following, on your new server:


    vim init.sh


i, Paste the following in init.sh:


    apt-get -y update
    apt-get -y install nginx vim net-tools certbot python3-certbot-nginx openjdk-21-jre-headless default-jdk
    apt-get -y upgrade
    ufw allow OpenSSH
    ufw allow 'Nginx Full'
    ufw --force enable
    export HOSTNAME=$(curl -s http://169.254.169.254/metadata/v1/hostname)
    export PUBLIC_IPV4=$(curl -s http://169.254.169.254/metadata/v1/interfaces/public/0/ipv4/address)
    echo Droplet: $HOSTNAME, IP Address: $PUBLIC_IPV4 > /usr/share/nginx/html/index.html
    sed -i 's/# server_names_hash_bucket_size/server_names_hash_bucket_size/g' /etc/nginx/nginx.conf

then, esc, :wq out and

    chmod +x init.sh
    ./init.sh


Make sure the above steps executed successfully:


    echo $HOSTNAME
    java -version
    netstat -tulpn
    ufw status


[Verify](./verify.md) results.


Once you get the expected verification results, it is safe to:


    sudo reboot 0







