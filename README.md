# Most Active Cookies
Parse a cookie file and print the most active cookies for a  given day

# Steps to build

`gradle clean test jar`

# Steps to run

`./most_active_cookie <FILE_NAME> -d <DAY>`

Example:

`./most_active_cookie cookie_log.csv -d 2018-12-08`

# Remarks
  1. Tested on Cygwin 
  2. "APP_JAR_PATH" property can be used to refer to the most active cookie CLI jar (By default it uses: ./build/libs/MostActiveCookie-1.0-SNAPSHOT.jar). 
