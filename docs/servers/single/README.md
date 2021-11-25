[home](../../../README.md)

# panopset.com Static and Dynamic Content Server Mirror

Follow these instructions, to mirror panopset.com on a single host.

When creating the digitalocean server, check the 'User data' box,

![User data](../../images/userdata.png)

... and past this in:


Check it, and paste this in


    #!/bin/bash
    apt-get -y update
    apt-get -y install nginx vim net-tools certbot python3-certbot-nginx openjdk-16-jre-headless default-jdk
    apt-get -y upgrade
    ufw allow OpenSSH
    ufw allow 'Nginx Full'
    ufw enable
    export HOSTNAME=$(curl -s http://169.254.169.254/metadata/v1/hostname)
    export PUBLIC_IPV4=$(curl -s http://169.254.169.254/metadata/v1/interfaces/public/0/ipv4/address)
    echo Droplet: $HOSTNAME, IP Address: $PUBLIC_IPV4 > /usr/share/nginx/html/index.html
    
    
If you are not planning on deploying to a secured server, you may drop:


    certbot python3-certbot-nginx



Edit your ssh config file


    vim ~/.ssh/config


Add an entry for your new server


    Host <your $PSS, which will be the same as your $PDS for a single host install>
    HostName <your static content server host or floating ip>
    User root
    IdentityFile ~/.ssh/<your private key file>
    

... and ssh out to it:

    
    ssh $PSS


... verify system is up and running, and that net-tools was installed confirming that the 'User data' script ran okay.  You should also be able to hit the server with a browser and see the default nginx page.


    netstat -tulpn


you should see nginx listening on port 80.

... next, verify the firewall settings are good:


    ufw status


... make sure you see something like this

![Firewall](../../images/firewall.png)


    exit
    
    
On your dev system, 

    
    . ./loadprops_linux.sh 
    ./userprep.sh $PSS
    ssh $PSS
    ./crtusr.sh


... you should then see:


    authorized_keys
    
    
now, 


    exit

    
...and change the server User from root to your $PAN_USR user in your ssh config.


    vim ~/.ssh/config


    Host static
    HostName <your static content server host or floating ip>
    User <your user id>
    IdentityFile ~/.ssh/<your private key


... do the update and wq, and then ssh back in to confirm you are logged in as the new user:


    ssh $PSS
    exit

Deploy the spring boot jar...


    ./bd.sh
    ./pd.sh
    ./serviceprep.sh

Going back to the server


     ssh $PSS
     sudo vim /etc/nginx/nginx.conf
     
     
Add this to the end of the http block:


    add_header Cache-Control "no-cache, no-store, max-age=0, must-revalidate";
    add_header X-Content-Type-Options nosniff;
    add_header X-Frame-Options SAMEORIGIN;


Optionally create the nginx server block and [secure](../secureprep.md) it with letsencrypt.  If you decide not to do this, make sure to blank out PAN_STATIC_DOMAIN 
and PAN_DYNAMIC_DOMAIN in your ~/.com.panopset.deploy.properties file.  Also your 
domain below would be default, in that case.


    vim /etc/nginx/sites-available/<domain>


Replace the / location section with:



     location /images/ {
      try_files $uri $uri/ =404;
     }
     location /downloads/ {
      try_files $uri $uri/ =404;
     }
     location /installers/ {
      try_files $uri $uri/ =404;
     }
     location /css/ {
      try_files $uri $uri/ =404;
     }
     location /js/ {
      try_files $uri $uri/ =404;
     }
     location /gen/ {
      try_files $uri $uri/ =404;
     }
     location /s/ {
      try_files $uri $uri/ =404;
     }
     location / {
      proxy_pass http://127.0.0.1:8081;
     }



... wq out and verify that the nginx config is good, and install the service:


    sudo nginx -t
    ./installservice.sh
    sudo systemctl enable web.service
    sudo reboot 0

    
... and then on your dev PC (build static and publish static):
    
    
    ./bs.sh
    ./ps.sh
    
    
To build and deploy the dynamic server jar again, repeat just these steps from above (build dynamic and publish dynamic):


    ./bd.sh
    ./pd.sh


Now that everything is set up, to rebuild and publish both static and dynamic content, you would do this:



    ./all.sh
     ssh $PSS
     sudo service web restart
    
    
...or, to just build both, you would:



    ./ball.sh


    
Building and deploying the desktop applications is a matter of updating the version, building the artificats, creating their json description files, and deploying them, depending on your platform.


Linux:


    ./build.sh
    ./deploy.sh
    
    
Windows:


    build.cmd
    deploy.cmd
    
    
Macintosh:


    ./buildmac.sh
    ./deploy.sh
    
    
    
