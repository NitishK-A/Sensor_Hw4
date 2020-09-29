# Sensor


- Collected sensor data using accelerometer, gyroscope, orientation, GPS, and proximity.
- Displayed the collected values on the screen as well as store them in an SQLite database with timestamps. Stored each sensor data in a separate table and one can also export this data as a csv file.
- Using  X, Y, Z accelerations of the accelerometer sensor detected if a user has shaken his phone by computing the speed at which the device has been shaken. 
- I calculate the difference between past and current X, Y and Z readings.
- Calculated the speed as the change of accelerations with time. If the speed is above a threshold a shake is detected
