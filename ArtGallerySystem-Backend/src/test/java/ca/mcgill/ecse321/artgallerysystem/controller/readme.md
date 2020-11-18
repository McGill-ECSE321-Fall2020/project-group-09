This folder includes the requests we created using postman for integration testing.

To run the tests, use newman:

`newman run anyAPItestYouWant.json`

The test will go through a general use-case of the program. 

Note that since all classes are dependent on each other in many cases, there might be failures if json files are run individually. However, most of the tests passed when we ran all requests together in an appropriate order such that the required objects have been created before used.

If you you do not have newman (requires Node.js),

`npm install -g newman`.

Links to Postman collections:
* [TestAddress](https://www.getpostman.com/collections/daf4e2c4c7cd6640e279)
* [TestArtist](https://www.getpostman.com/collections/e7d1997d4721a57b1033)
* [TestArtPiece](https://www.getpostman.com/collections/b38acada24d888973313)
* [TestCustomer](https://www.getpostman.com/collections/b465765df01213845ceb)
* [TestInStorePickUp](https://www.getpostman.com/collections/5414e50ccc1ee38cd56d)
* [TestParcelDelivery](https://www.getpostman.com/collections/acca2ffa9ff2c691055a)
* [TestPayment](https://www.getpostman.com/collections/c78d2ae72eeaaeb97684)
* [TestPurchase](https://www.getpostman.com/collections/da282f35a67422273cf6)
* [TestSystem](https://www.getpostman.com/collections/8d3f1408eb1d75bf766b)
* [TestUser](https://www.getpostman.com/collections/00d5bf7b1af69b8b8764)
* [TestUserRole](https://www.getpostman.com/collections/af8666a5d93c8d01c029)
