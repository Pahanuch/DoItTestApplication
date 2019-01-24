# DoItTestApplication
Test project for DOIT Software company

## Test task
iOS/Android developer

### Task
Create an application based on existing API.

As result, you should:
provide a link to the Github repo;
provide a link to the test APP (акk - hr.doitua@gmail.com, UDID: iPhone 5 - 254561de14e76d53f2d25a8b009394dd73ca2864) using  https://www.diawi.com/ or  https://hockeyapp.net/.

#### Description
- API works as an image gallery.
- As a user, using a client application, I can:
- sign up;
- sign in;
- view added images;
- add a new image;
- generate GIF.

#### API
- Base URL http://api.doitserver.in.ua
- ApiDoc http://api.doitserver.in.ua/api/doc

#### Screens
- Login;
- Pictures list:
- image section includes the image, address and weather (these data are returned from API);
- Upload new picture:
- image;
- description;
- #tag;

the coordinates of your current location or coordinates of image Metadata (if any).
GIF images generation:
- the method returns a link to the GIF image consisting of the last 5 uploaded images;
- the app shows a popup with the GIF image over the pictures list.
