# File Upload - Spring Boot
Simple upload & download file by using spring boot on static folder

## Featured
- Spring Boot Web
- Upload and Download file (User & Admin)
- Max file size : 5 MB
- All type file can be upload

## API

### Admin

Methods | Url | Action | Body |
--- | --- | --- | --- |
| POST | /api/file/admin/upload | upload file admin | file |
| GET | /api/file/admin/files | get list and path store file admin | - |
| GET | /api/file/admin/download/{filename.extensions} | get and download file admin | - |

### User

Methods | Url | Action | Body |
--- | --- | --- | --- |
| POST | /api/file/user/upload | upload file user | file |
| GET | /api/file/user/files | get list and path store file user | - |
| GET | /api/file/user/download/{filename.extensions} | get and download file user | - |
