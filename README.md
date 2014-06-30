archiver2
=========
  The archiver for http://newsmth.net.

Database:
  Use docs/create.sql to create mysql database for archiver and add a user have full access on it.
  
Maven:
  Your should set maven properties to let it work.
  
  <profile>
    <id>testdb</id>
    <properties>
       <archiver.dburl>jdbc:mysql://127.0.0.1:3306/archiver?autoReconnect=true</archiver.dburl>
       <archiver.dbuser>archiver</archiver.dbuser>
       <archiver.dbpass>xxxxx</archiver.dbpass>
       <archiver.dbdriver>com.mysql.jdbc.Driver</archiver.dbdriver>
    </properties>
  </profile>
  
Test:
    mvn test
