Library Management System

Manage books
Manage user
Manage access
Capability to upload/create entry of books via a CSV file to create inventory
Capablity to create book inventory by calling multiple source
			Source 1: book inventory 1
			Source 2: book inventory 2
Unique entry for a book - duplicate detection is your logic		
Manage book borrowing

**Authorisation**
For Authorisation have added a header which which verify if the user is an Admin
X-User-Id:1

**Design Patterns:**
Proxy Design Pattern for authorisation
Strategy Pattern to choose relevant Processing methodogy
Factory Pattern fetch and upload books from different sources

**Note**
Since the application isn't connecting to database hence we need to add data everytime we start the application
The postman collection has been added in resources folder
