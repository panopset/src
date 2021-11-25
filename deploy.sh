. ~/.com.panopset.deploy.properties
. ./deploy.properties
rsync -avuzh ${HTTP_HOME} ${PSS}:/var/www/${PSD}
