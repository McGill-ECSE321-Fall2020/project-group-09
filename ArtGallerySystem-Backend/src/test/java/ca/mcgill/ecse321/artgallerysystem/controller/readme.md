This folder includes the request we created using postman for integration testing 

To run the tests, use newman

newman run anyAPItestYouWant.json

The test will go through a general use-case of the program. 

(Since all classes are kind of dependent on each other, there might be failure if run json files individually. However, most of tests passed when we ran these requests together.) 

If you you do not have newman (requires Node.js)

npm install -g newman.

Link to postman: https://www.getpostman.com/collections/67011e7c6faf591edf87
