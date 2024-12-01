[home](../../README.md)

# Panopset Mobile Applications Build Manual

From [Flutter](https://docs.flutter.dev/get-started/install/linux/android):


    sudo apt-get update -y && sudo apt-get upgrade -y;
    sudo apt-get install -y curl git unzip xz-utils zip libglu1-mesa
    sudo apt-get install libc6:amd64 libstdc++6:amd64 lib32z1 libbz2-1.0:amd64

You'll also [need](https://docs.flutter.dev/platform-integration/linux/install-linux/install-linux-from-android):

    sudo apt install -y build-essential clang cmake git ninja-build pkg-config libgtk-3-dev liblzma-dev libstdc++-12-dev

Add flutter and Android cmdline-tools to your path in ~/.profile:

    export ANDROID_HOME=~/Android/Sdk
    PATH=$PATH:$HOME/Documents/apps/flutter/bin
    export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/cmdline-tools/latest/bin

... as shown in the "Show sh command" of the Flutter installation link above.  (If you use "Show bash command" 
you'll mess up sdkman unless you manually edit the file to make sure the sdkman script is at the end.)


