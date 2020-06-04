# Mock Server Tools
***
Mock server used to support for testing as substitution of the real server.
## Features

- HTTP protocol api mock service
- HTTPS protocol api mock service, certificates are stored in the securities directory

## Where and What form are the Mock Data Store?
- Commons
    > All types of mock service can be stored into DB(Mysql), Mongo, File, but for the different store type mush have one
    data loader which should implements MockDataLoader, and explicit specify the implementation with property mock.server.http.data-loader
- Http mock service
- Mock data is stored in the json files which are in the classpath mock-data.
- All mock data is stored with a key-value form, the key is the identifier for the mock response body.


## Steps for using ssl mock server

