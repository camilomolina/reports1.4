FROM camilomolina/jboss:4.0.5

MAINTAINER Camilo Molina <camilo.molina.orth@gmail.com>

ADD ./artefacts/* /usr/local/jboss/jboss-4.0.5.GA/server/default/deploy/
ADD ./lib/* /usr/local/jboss/jboss-4.0.5.GA/server/default/lib/

ADD ./config/reports-ds.xml /usr/local/jboss/jboss-4.0.5.GA/server/default/deploy/

ENTRYPOINT ["sh", "/usr/local/jboss/jboss-4.0.5.GA/bin/run.sh"]