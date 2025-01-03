[home](README.md)

# Domain

Once you have an IP address to host your website, you'll need to update/add
the custom records in the DNS settings, like this, replacing the localhost
127.0.0.1 IP address with your server's IP address, and fas21 with your
domain name:

| Host          | Type  | Data      |
|---------------|-------|-----------|
| *             | A     | 127.0.0.1 |
| @             | A     | 127.0.0.1 |
| fas21.com     | A     | 127.0.0.1 |
| www.fas21.com | CNAME | fas21.com |

Once you can ping your domain (if I have to explain ping, I'm amazed you 
are reading this, but hats off for diving right in I guess) you're ready
to set up your server [block](block.md).
