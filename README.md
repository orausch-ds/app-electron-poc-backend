Proof of concept for electron app.

This contains the Spring Boot Backend.

Use "mvn clean package" to create the jar artifact of the application and a custom jre.
Put the files /target/custom-jre and target/electron-poc-backend-jar-VERSION.jar into the /backend folder of the
frontend.

Frontend: https://github.com/orausch-ds/app-electron-poc-frontend

SSTs:
- GET localhost:9229/orders
- POST localhost:9229/orders/order
- DELETE localhost:9229/orders/order/{orderId}