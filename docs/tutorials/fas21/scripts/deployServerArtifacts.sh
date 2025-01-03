. ./init.sh
HTML_DIR=/var/www/${SITE_FAS21}/html/
ssh $SITE_NAME "mkdir -p ${HTML_DIR}"
scp -p temp/index.html $SITE_USR@$SITE_NAME:$HTML_DIR
scp -p temp/${SITE_FAS21} $SITE_USR@$SITE_NAME:/etc/nginx/sites-available/
ssh $SITE_NAME "echo ${SITE_PWD} | sudo -S ln -s /etc/nginx/sites-available/${SITE_FAS21} /etc/nginx/sites-enabled/"
ssh $SITE_NAME "echo ${SITE_PWD} | sudo -S systemctl restart nginx"

