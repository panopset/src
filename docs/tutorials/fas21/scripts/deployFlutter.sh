. ./init.sh
ssh $SITE_NAME "rm -rf $HTML_DIR/web"
scp -p -r ../flutter/blackjack/build/web $SITE_USR@$SITE_NAME:$HTML_DIR
