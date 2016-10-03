# Scheduling Service

## Aim
This service aims at managing the booking of various resources of the college such as:
* Auditorium
* Guest House
* Conference Room etc.

##Details

* The users will be classified according to the access levels they have for booking the resources. They can be club secretaries, faculty members, Training and Placement office or students.
* The priority among these users will be defined.

##Database design

Following will be the tables with the corresponding fields:

### Resources Table
*Resource_Id
*Resource_Name
*POC_Id (Point of contact)

###POC Table
*POC_Id
*Name
*Email_Id
*Contact_Number

###Bookings Table
*Resource_Id
*User_Id
*Date
*Start_Time
*End_Time
*TimeStamp

###Category Table
*Category
*Number_of_bookings (just in case we have to keep a restriction on the number of booking that can be made by each user)



