. ./init.sh
ssh $SITE_NAME "echo ${SITE_PWD} | sudo -S certbot --nginx -d fas21.com -d www.fas21.com"

