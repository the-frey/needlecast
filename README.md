[![Build Status](https://travis-ci.org/the-frey/needlecast.svg?branch=master)](https://travis-ci.org/the-frey/needlecast)

# needlecast

A micro library for working with Kafka from Clojure.

I've been hacking around with this for a while and thought it was about time I put it on GitHub as it's a pain to be emailing myself Gists and snippets.

## Usage

This project is on Clojars.

## Development

Download using the scripts in the `./travis` dir. You might need to strip `sudo` from the start of all commands, as that is just for Travis.

Download the foreman gem.

    $ gem install foreman

Use the Procfile to get started.

    foreman start

If the kafka logs go awry, delete them:

    rm -r /tmp/kafka-logs

If zookeeper goes nuts, delete its temp directory:

    rm -r /tmp/zookeeper

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
