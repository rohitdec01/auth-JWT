# auth-JWT

This is a sample application to get auth-JWT token. Where /authentication api is not protected.
All the other APIs will be protected and required a valid token with each request.
Here is the URls.

I have hardcoded one user for now in the application.
````
User name: test
Password: test
````
To run this app on the localhost from postman following URLs can be used.
````
POST:    http://localhost:8083/authenticate
GET:     https://auth-jwt-rohitdec01.herokuapp.com/dashboardUserDetail
````

This application has been deployed to Heroku So, the following URL's are from the heroku server. 

##### To get the JWT token based on the user.

````
POST http request:
https://auth-jwt-rohitdec01.herokuapp.com/authenticate

JSON user credentials: pass as a body with the request.
{
    "userName": "test",
    "password": "test"
}
````

##### To get the userDetails based on the user's token you have to use the following URL with a GET http request.
 
````
GET http Request:
https://auth-jwt-rohitdec01.herokuapp.com/dashboardUserDetail

Headers:
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNTkzMzU0NDg1LCJpYXQiOjE1OTMzMTg0ODV9.CgzTDZ4YTQ-nAGLJRNcf-wXeitJwSMPkhK2C7aWJZbk
````
