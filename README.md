# Open8 Android SDK
This is an example app to demonstrate how a developer can leverage our ad network to monetize their app. Integration documentation are available on [wiki](https://github.com/open8inc/open8-sdk-android-example/wiki/In-Feed-Wide-Fit-Panel-Ad-Integration-example).

The SDK is currently in beta stage. Only Infeed video ads are supported at the moment.

## Download
Open8 SDK is distributed via JCenter. To use it, include the following to your `build.gradle`:
```
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/open8inc/Open8AdSDK' }
    }
}

dependencies {
    compile 'com.open8.sdklib:open8-sdk:0.1.1'
}
```
## Requirements

- Android API Version 16 and up.
