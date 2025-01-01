`[home](README.md)

`# Block

Just as nobody builds a house without a blueprint,
you shouldn't build a website without being able to
go back and easily reproduce each step.

In the 
[instructions](https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-20-04)
for setting up server blocks, we see that we need to execute
some commands, on the server, such as:

    sudo mkdir -p /var/www/your_domain/html

One option is to ssh out to your server and execute the commands, but
that leaves you in a lurch, if your site ever has to be re-built.

We need to

* Create a script.
* Document the script.
* Optionally abstract the script, using parameters obtained from a bootstrap file.
* Place the script on the remote system.
* Execute it.




    -- output executables in fw --help
    -- finish this.