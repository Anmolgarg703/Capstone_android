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
* Resource_Id
* Resource_Name
* POC_Id (Point of contact)

###POC Table
* POC_Id
* Name
* Email_Id
* Contact_Number

###Bookings Table
* Resource_Id
* User_Id
* Date
* Start_Time
* End_Time
* TimeStamp
* Capacity (The resource can accommodate how many people)

###Category Table
* Category
* Number_of_bookings (just in case we have to keep a restriction on the number of booking that can be made by each user)

The time range in which a specific category of users can book a particular resource will be maintained by the identity service (still to be discussed).

##Features
* The users will have an available list of options as to which resource is available on what days and during which time durations
* The users can select the resource (for example, lecture halls) and then specify the capacity required, and the corresponding results will be displayed.
* The users can book the resources, as well as cancel or modify their bookings from this service.
* The notification of the bookings will be sent to the required persons by calling the communication service. The persons can be the POC for that resource, as well as the mandatory people for the event for which the resource has been booked.