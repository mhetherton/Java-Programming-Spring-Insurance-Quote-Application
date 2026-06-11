# Insurance-Quote-BackEnd

http://localhost:8888/check-threads
http://localhost:8888/
http://localhost:8888/1
http://localhost:8888/searchproducttype?producttype=Laptop

### Get all items
GET http://localhost:8888/

### Get item by ID (example: ID 1)
GET http://localhost:8888/1

### Search by product type (example: Laptop)
GET http://localhost:8888/searchproducttype?producttype=Laptop


POST -> http://localhost:8888
Make sure Content-Type is JSON in Postman
{
  "productType": "Pixel 9a",
  "productValue": 450.00
}

### Create a new item
POST http://localhost:8888/
Content-Type: application/json

{
  "productType": "Smartwatch",
  "productValue": 250.00
}

### Update an item (example: ID 1)
PUT http://localhost:8888/31
Content-Type: application/json

{
  "productType": "Laptop",
  "productValue": 999.99
}

### Delete an item (example: ID 1)
DELETE http://localhost:8888/31


http://localhost:8888/31
http://localhost:8888/searchproducttype?producttype=Pixel 9a

# Using client to other Microservice
http://localhost:8888/customer/customerdetailswithinsureditems
http://localhost:8888/items/insureditemswithcustomerandproductdetails
http://localhost:8888/paginated
http://localhost:8888/paginatedandsorted?sort=productType,desc&sort=quoteAmount,asc
http://localhost:8888/paginatedandsorted?sort=productType,asc&sort=quoteAmount,desc
http://localhost:8888/insureditems/search?productType=Television&customerAccountNumber=ACC123&productValue=1500
http://localhost:8888/searchByValueAndType?minValue=800&maxValue=1000&typePattern=Laptop



mvn clean package -DskipTests
mvn spring-boot:run -pl CustomerMicroservice
mvn spring-boot:run -pl InsuranceQuoteBackEnd
mvn spring-boot:run -pl ProductMicroservice

















