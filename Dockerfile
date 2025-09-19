# Jenkins LTS with JDK17
FROM jenkins/jenkins:lts-jdk17
ENV TZ=Europe/Rome
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc.yaml
ENV NODE_PATH=/usr/local/lib/node_modules
USER root
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl ca-certificates git unzip maven nodejs npm \
 && rm -rf /var/lib/apt/lists/*
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
RUN npm i -g openai glob minimist
COPY codex-ai-quality-gate-demo /usr/share/demo
COPY casc.yaml /usr/share/jenkins/ref/casc.yaml
COPY init.groovy.d/01-seed.groovy /usr/share/jenkins/ref/init.groovy.d/01-seed.groovy
RUN chown -R jenkins:jenkins /usr/share/demo /usr/share/jenkins /var/jenkins_home
EXPOSE 8080 50000
HEALTHCHECK --interval=30s --timeout=5s --retries=30 \
  CMD curl -sf http://localhost:8080/login || exit 1
USER jenkins
