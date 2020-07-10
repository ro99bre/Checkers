FROM openjdk:14-buster
ARG SBT_VERSION=1.3.13

# Tnstall sbt
RUN \
    curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
    dpkg -i sbt-$SBT_VERSION.deb && \
    rm sbt-$SBT_VERSION.deb && \
    apt-get update && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6 openjfx

WORKDIR /Sources
ADD . /Sources
RUN sbt compile

CMD sbt run
