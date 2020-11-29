# Overview
This is an Android project that acts as a Store Finder. The list of stores is based off of a CSV file that contains the locations of various Target stores in the United States. The app presents a simple UI where a user can enter an address, and then a set of radio buttons for Miles or Kilometers, and then a "Find address" button. When a user clicks on this button, it takes the address provide, and makes a Geocode network call to LocationIQ, a Geocode service, which then attempts to find the Lat/Lon of the address. Once these coordinates are retrieved (in the MainViewModel class), then the closest store is found and sent to the UI, along with the distance from the address to that store.

# Project Structure
There are several different packages set up to imitate how a production app might have separated the code:
### Model
This package contains the model object for a store, as well as the Store repository, and the CSV helper file that parses the actual CSV file.
### Network
This package contains the Network Manager class, as well as the response object which is hydrated from parsing the JSON response.
### UI
This package contains the Activity class which is responsible for showing the main screen, as well as an Interface to allow for callbacks back into the UI.
### Viewmodel
This package contains the ViewModel which does most of the work. It takes the data, makes the network call, and pushes that data to the UI.
### Util
This is where the Lat/Long distance calculations live.

# Technologies used
- Android X Jetpack classes, such as LiveData and ViewModel objects to follow the newest architecture guidelines
- Android Hilt on top of Dagger 2 is used for Dependency Injection
- Volley is used as the main networking layer
- Gson is used for JSON parsing
- Kotlin coroutines are used for asynchronous code calls
- Mockito is used for Mocking in unit tests (as well as the nhaarman library making it easier to write them in Kotlin)

# Unit tests
The unit tests are located here:
https://github.com/MobileOak/GroveCo/tree/master/app/src/test/java/co/grove/storefinder

If this were an actual production app, there would be more unit tests than provided - the ones that exist are there to demonstrate my ability to use Mockito for creating unit tests. In a production app, I would have also written a smoke test in Espresso to test that UI actually loaded and that the buttons could be clicked.

# Building the App
For those looking to build the app, the steps are:
 - Download and install Android Studio
 - Clone this repository
 - Open the root file in Android Studio
 - Run the app (either on an attached Android device with developer options enabled and USB debugging enabled or in an emulator)

Additionally, you could, on an Android device that has permissions set to allow side-loading of APKs, point at my pre-built APK located here:
https://github.com/MobileOak/GroveCo/raw/master/storeFinder.apk
