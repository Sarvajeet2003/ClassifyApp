cmake_minimum_required(VERSION 3.10.2)

project(MyNativeApp)

# Set the path to the Android NDK.
# Replace 'path_to_ndk' with the actual path to your NDK installation.
set(ANDROID_NDK /Users/sarvajeethuk/Library/Android/sdk/ndk/26.3.11579264)

# Set the path to the CMake toolchain file for Android.
set(CMAKE_TOOLCHAIN_FILE ${ANDROID_NDK}/build/cmake/android.toolchain.cmake)

# Set the minimum API level required for your application.
set(ANDROID_PLATFORM android-xx)

# Set the NDK version using major.minor.micro format.
set(ANDROID_NDK_VERSION_MAJOR 22)
set(ANDROID_NDK_VERSION_MINOR 0)
set(ANDROID_NDK_VERSION_MICRO 7026061)

# Add your native library source files.
add_library(native-lib SHARED src/main/jni/native-lib.cpp)

# Link your native library with necessary system libraries.
target_link_libraries(native-lib log)
