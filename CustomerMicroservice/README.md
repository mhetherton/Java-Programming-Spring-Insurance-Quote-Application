# Customer-Microservice

# GET 
http://localhost:9999/h2-ui
http://localhost:9999/api/customer
http://localhost:9999/api/customer/1
http://localhost:9999/api/customer/account/ACC127
http://localhost:9999/api/customer/count


# POST -> 
http://localhost:9999/api/customer
Make sure Content-Type is JSON in Postman
{
    "accountNumber": "ACC128",
    "name": "Tom Jones",
    "email": "Tom.Jones@gmail.com"
}

# GET newly created customer ->
http://localhost:9999/api/customer/account/ACC128
http://localhost:9999/api/customer/6



# GET Product-Microservice via client ->
http://localhost:9998/api/product/types
http://localhost:9999/api/customer/products/descriptions?productType=Printer




























