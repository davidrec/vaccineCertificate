# VaccineCertificate

So it turns out that the Ministry of Health in Israel has decided to issue a vaccine certificate to anyone who has undergone 2 vaccines against the corona virus.

![Image of vaccine certificate](https://github.com/davidrec/vaccineCertificate/blob/master/app/Img/vaccine_passport2.jpg)

When I looked at the first vaccinator's certificate, I noticed a QR code, but scanning it produces illegible texts.
A brief search led me to this post:

![Image of Post](https://github.com/davidrec/vaccineCertificate/blob/master/app/Img/Capture.JPG)

Well, I guess in the future they will publish some kind of web service to verify the id with database.
Until then, I decided to build an app that would do the following:
1. Read the QR code
2. Take the relevant part and encode it from base 64
3. parse the JSON and print the data in a readable manner on the screen.
 
### installation instructions:
Pull the code, or download the ZIP, build the app and install the apk on your device.
Or  you can download the APK from here: 
https://appsenjoy.com/h6s4Y

Screenshot from the app:
![Image of screenshot](https://github.com/davidrec/vaccineCertificate/blob/master/app/Img/Screenshot.png)

