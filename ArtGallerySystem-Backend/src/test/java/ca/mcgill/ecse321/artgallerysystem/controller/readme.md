This folder includes the request we created using postman for integration testing 

To run the tests, use newman

newman run anyAPItestYouWant.json

The test will go through a general use-case of the program. It contains assertions to detect if something is failing.

If you you do not have newman (requires Node.js)

npm install -g newman.

Link to postman: https://www.getpostman.com/collections/67011e7c6faf591edf87
