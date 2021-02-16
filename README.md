# GitHub-Starred-Repositories

GitHub-Starred-Repositories will fetch most recent starred repositories from github

## Architecture Used
`Clean Architecture` - https://github.com/android10/Android-CleanArchitecture/blob/master/README.md


## Libraries Used
1. RxJava
2. Dagger 2
3. Glide
4. FBShimmer
5. Retrofit


## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/mmanojkumar/github_starred_repos
```


## Configuration
### Keystores:
Refer `app/gradle.properties` for release build
```gradle
RELEASE_STORE_FILE=./../***FileLocation***
RELEASE_STORE_PASSWORD=***Password***
RELEASE_KEY_ALIAS=***Alias***
RELEASE_KEY_PASSWORD=***Password***
```

### Build variants
- Debug
- Release

Use the Android Studio *Build Variants* button to choose between **debug** and **release**  build types


### Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*


From Terminal:
1. Go to project root folder
2. Run ***./gradle assembleDebug*** to generate debug apk file
2. Run ***./gradle assembleRelease*** to generate release apk file
4. Run ***./gradle assemble*** to generate both debug and release apk files


## Developed By
This project is mantained by:
* [Manojkumar Murugesan](https://github.com/mmanojkumar)

