
--This project contains program to create and upload data into database tables in SQLITE3 from Excel files.
--

--To run the project,
Step 1-- Download the .zip file.
Step 2-- Extract the .zip file.
Step 3-- Run the FileToDb.exe file (Grant permission ro run .exe from untrusted sources)
Step 4-- If you want to check sample Excel file, just click "Enter" when prompted for Excel file input
         If you want to upload your own file, Paste thhe Excel file inside the directory of the extracted folder and give the filename when          prompted for  Excel file input. 
Step 5 -- Press "Enter" and check whether "test.db" is created.
Step 6 -- To Check the created Database, run the following commands on "sqlite3.exe" present in the extracted project folder.
--
       -- .open test.db
       -- .tables 

Step 7 -- The above commands will show the tablenames created, try -- select count(*) from _tableName_ -- to verify whether number of rows copied from Excel is correct. 






<<<<<<< HEAD
# sample-projects
=======
# file-to-database
>>>>>>> a72eff14b44f1b866d210bf13066ecebbc00bd6b
