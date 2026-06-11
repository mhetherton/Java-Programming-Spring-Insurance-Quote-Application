# Product-Microservice

http://localhost:9998/api/product
http://localhost:9998/api/product/1
http://localhost:9998/api/product/types
http://localhost:9998/api/product/search/type/in?productTypes=Laptop,Smartwatch
http://localhost:9998/api/product/search/type/like?pattern=M
http://localhost:9998/api/product/count/type?productType=Laptop
http://localhost:9998/api/product/count



POST -> http://localhost:9998/api/product
Make sure Content-Type is JSON in Postman
{
    "productType": "Google Pixel 9a",
    "productDescription": "This policy covers accidental damage, fire, liquid damage, and theft. Your Pixel only."
}


http://localhost:9998/api/product/count/type?productType=Google Pixel 9a
http://localhost:9998/api/product/18






























