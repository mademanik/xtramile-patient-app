# patient-app-fullstask

### Overview

1. Create new patient, open url : http://localhost:8081/patient
![create](https://i.postimg.cc/K8PwQ74r/create.png)
![create_done](https://i.postimg.cc/ydbbWvrB/create-done.png)

2. Show patient details
![show](https://i.postimg.cc/3rP3cBrP/show.png)

3. Delete Patient
![delete](https://i.postimg.cc/8cfnvMqv/delete.png)
![delete_done](https://i.postimg.cc/DmVpPmTM/delete-done.png)

4. Update Patient
![updatepasien](https://i.postimg.cc/GmRdcmyK/update.png)
![updatepasien_done](https://i.postimg.cc/NfmQqH5L/update-done.png)

5. Search Patient By Pid
![searchpid](https://i.postimg.cc/h4JSswnL/pid.png)

6. Search Patient By Name
![searchname](https://i.postimg.cc/B66zqdpM/name.png)

### Technology Stack
1. Springboot (backend)
2. Angular (frontend)
3. Rest API
4. Mysql

### Prerequisite

1. Check ng version
   ```
   ng version
   Angular CLI: 15.2.5
   ```
2. Check node version
   ```
   node -v
   v16.20.0
   ```
3. Check npm version
   ```
   npm -v
   8.19.4
   ```
4. Check JDK version
   ```
   java -version
   java version "17.0.6" 2023-01-17 LTS
   ```

### Installation Steps

1. Clone this repo
   ```
   git clone https://github.com/mademanik/xtramile-patient-app.git
   ```
   
2. make sure you have MySQL database in your local pc, then create database name : xtramile_patient_app, with user: root and pass: (blank)
   ```
   create database xtramile_patient_app
   ```
   
#### Running Springboot Backend Server
3. cd into xtramile-patient-app
   ```
   cd xtramile-patient-app
   ```
4. cd into xtramile-patient-backend
   ```
   cd xtramile-patient-backend
   ```
5. config MySQL database connection at src\main\resources\application.properties
  
6. run springboot
   ```
   mvn spring-boot:run
   ```
7. running backend server done

#### Running Angular Frontend Client
8. cd into xtramile-patient-frontend
   ```
   cd xtramile-patient-frontend
   ```
9. run npm install or yarn to download package dependency
   ```
   npm i
   ```
10. run npm start to run angular client frontend
   ```
   npm start
   ```
11. open to url: http://localhost:8081/patient to open CRUD patient app
12. running frontend client done
