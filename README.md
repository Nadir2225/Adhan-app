# Adhan Reminder App

This project is an Android application designed to remind users of Adhan (prayer times) using Jetpack Compose for the UI. It includes alarm scheduling, location-based prayer time adjustments, a variety of Adhan sounds to choose from, and a Qibla compass for prayer direction. The application leverages WorkManager for scheduling alarms and ViewModel for managing data updates.

## Features

- **Alarm Scheduling with WorkManager**: The app uses WorkManager to schedule and manage Adhan notifications for each prayer time.
- **Location-Based Prayer Times**: Automatically adjusts prayer times based on the user’s location, with location permissions managed dynamically.
- **Adhan Sound Selection**: A settings screen allows users to choose their preferred Adhan sound and preview it.
- **Qibla Compass**: Displays the direction of prayer.
- **Data Management with ViewModel**: Ensures smooth data flow and UI updates.

## Screenshots
![adhan](https://github.com/user-attachments/assets/73322843-ee66-4ef8-8ee2-573ed68c4ee7)



## Setup and Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/Nadir2225/Adhan-app.git
    cd Adhan-app
    ```

2. **Open in Android Studio**:
   - Open Android Studio and select "Open an Existing Project".
   - Navigate to the cloned project directory and select it.

3. **Configure Dependencies**:
    Make sure to have the following dependencies in your `build.gradle` file:
    ```gradle
    implementation "androidx.compose.ui:ui:<version>"
    implementation "androidx.compose.material3:material3:<version>"
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:<version>"
    implementation "androidx.work:work-runtime-ktx:<version>"
    implementation "com.google.android.gms:play-services-location:<version>"
    ```

4. **Request Permissions**:
    Ensure that `ACCESS_COARSE_LOCATION` and `POST_NOTIFICATIONS` (for Android 13 and above) are configured in `AndroidManifest.xml`.

5. **Run the App**:
   Use the "Run" button in Android Studio to install and start the app on an emulator or physical device.

## Usage

- **Prayer Times Screen**: Shows calculated prayer times for the current location.
- **Settings Screen**: Choose and preview Adhan sounds.
- **Compass**: Points to Qibla direction.

## GitHub Repository

Find the complete source code and project files on GitHub at the following link:  
[GitHub Repository - Adhan App](https://github.com/Nadir2225/Adhan-app)

This repository includes all necessary resources, installation instructions, and detailed notes to contribute to the project or explore its architecture.
