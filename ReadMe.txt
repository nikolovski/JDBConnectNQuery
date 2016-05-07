Database ConnectNQuery provided by
          .--.  ,---.  _______ ,-..-. .-. .---.
|\    /| / /\ \ | .-.\|__   __||(||  \| |/ .-. )
|(\  / |/ /__\ \| `-'/  )| |   (_)|   | || | |(_)
(_)\/  ||  __  ||   (  (_) |   | || |\  || | | |
| \  / || |  |)|| |\ \   | |   | || | |)|\ `-' /
| |\/| ||_|  (_)|_| \)\  `-'   `-'/(  (_) )---'
'-'  '-'            (__)         (__)    (_)

ConnectNQuery is a java class that allows establishing database connection and executing queries.
It is a simple, easy to use, implement and modify.

To simply run ConnectNQuery please follow these steps:

1. Open terminal and change the directory to the directory where ConnectNQuery.java file is.

2. Execute the following command to compile the java source code:
   javac ConnectNQuery.java

3. Next and finally, execute the following command to connect and query a database:
   java ConnectNQuery <jdbcURL> <username> <password> <driver> <SQL_query>

Note: jdbcURL, username, password and SQL_query are strings so they need to be enclosed in ""
      driver is an int and currently supports only 1 - Oracle DB and 2 - MySQL DB. It is not enclosed in ""
