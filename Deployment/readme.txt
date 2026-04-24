Currently the jar file for the backend contains:
Pg database called trial so in appliaction properties the db address is:
spring.datasource.url=jdbc:postgresql://localhost:5432/trial
spring.datasource.username=postgres
spring.datasource.password=admin

And the frontend url is set to:
frontend.url=http://localhost:5173/


To run the frontend production version it is recomended to use vscode with liveserver extension:
Open the dist folder and right click the intex.html file. Select "Open with liveserver". 

For both frontend and backend to work you should run the jar file in backend_live_server directory and run this jar file. It was preconfigured to work with liveserver at port 5500.
