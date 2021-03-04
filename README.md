# test-app
Task:
Implement Announcement Website with the following functionality:
1. Add new announcement
2. Edit announcement
3. Delete announcement
4. See list of announcement
5. Show selected announcement details:

a. Should contain such fields: Title, Description, Date Added
b. Should show top 3 similar announcements

*two announcements are considered similar if they have at least one same word in title and
description

Using my API:

/api/announcement/  - method GET -  all announcements
/api/announcement/{announcement id}   - method GET -   announcement with given id  
/api/announcement/similar/{announcement id}   - method GET -   3 similar announcements to announcement with given id
/api/announcement/  - method POST -  post announcement
/api/announcement/{announcement id}  -  method PUT - edit announcement with given id
/api/announcement/{announcement id}  -  method DELETE - delete announcement with given id 
