## Time-Based OTP

Mobile Version of TOTP

# Configuration

To use this application, set environment variable in [gradle.properties](https://github.com/abudawud/totp-mobile-java/blob/master/gradle.properties) file. Here is the factory
configuration:

```
# APPLICATION CONFIG
app.endpoint="http://172.17.0.1:8080/"
app.encrypt.response=true
app.encrypt.request=true

otp.main.key="e0e2865c7370d8a0846a491a009ff6d05686e993"
otp.chiper="AES-256-CBC"
otp.time.force=4
otp.key.life=60
otp.key.debug=false
```