. ./checkProps4LinuxDEB.sh

mvn -f shoring clean install dokka:dokka

cp -r shoring/blackjackEngine/target/dokka/ /var/www/html/dox/blackjackEngine
cp -r shoring/compat/target/dokka/ /var/www/html/dox/compat
cp -r shoring/desk/target/dokka/ /var/www/html/dox/desk
cp -r shoring/flywheel/target/dokka/ /var/www/html/dox/flywheel
cp -r shoring/fxapp/target/dokka/ /var/www/html/dox/fxapp

#rsync -avuzh --relative shoring/blackjackEngine/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/blackjackEngine
#rsync -avuzh --relative shoring/compat/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/compat
#rsync -avuzh --relative shoring/desk/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/desk
#rsync -avuzh --relative shoring/flywheel/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/flywheel
#rsync -avuzh --relative shoring/fxapp/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/fxapp
