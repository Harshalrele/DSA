The pass.txt file contains users that should be preloaded into the db by the backend.

The Create_Scripts contains the create scrpits for the db should the backend fail to load the data by itself.
It is highly recomended to allow the backend to create the db classes instead of using the provided sql files.

However if it is required to manualy create those tables then:
- Set the "spring.jpa.hibernate.ddl-auto=none" in application.properties
- Enter the scripts in the following order:
	- app_user
	- basic_auth_user
	- person
	- refresh_token
	- examination_dictionary
	- clinic_staff
	- lab_staff
	- patient
	- receptionist
	- doctor
	- lab_assistant
	- lab_supervisor
	- visit
	- lab_examination
	- physical_examination

Alternative way:
In backup directory there is a db backup which should work when imported using pgadmin with data already present. Make sure to change the application.properties in order to select the correct tablespace