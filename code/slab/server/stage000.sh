# !/bin/bash

echo "staging $1 to $2."
rm -rf /var/www/$2/html
rsync -avuzh /var/www/$1/html /var/www/$2/

