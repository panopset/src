[home](../../README.md)


Create the nginx server block and secure it with letsencrypt.

Just need to do one if hosting both on a [single](./single/README.md) server:


From your workstation:



    ./secprep.sh $PSS $PSD
    ./secprep.sh $PDS $PDD



From the server, run the new dolink.sh script in the home directory.


    ssh $PSS
    ./dolink.sh
    sudo nginx -t   
    

... if no errors, <span style="color:red">make sure your floating IP for your DNS is pointing to your server</span> and...



    sudo nginx -t
    
    
... verify nginx.conf test is successful, then, make sure $PSD and/or $PDD is pointing to the right IP, then:



    sudo systemctl reload nginx
    ./docert.sh
    
