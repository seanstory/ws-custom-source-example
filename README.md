# Workplace Search - Custom API Source Example
A Custom API Source implementation for Elastic Workplace Search that crawls a filesystem for content.

### Building
To build the project, run a simple

    mvn clean install

### Installation
After building, move the tarball from `ws-custom-source-example-dist/target/*.tar.gz` to wherever you wish to
install your source, and simple untar the tarball.

### Configuration
You must configure your Content Source Key and Access token in `config/source.yaml`.

### Usage
To run, simply execute:

    bin/sync helloworld
