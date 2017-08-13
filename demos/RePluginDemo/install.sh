./gradlew clean assembleDebug
echo "build successful"
rm ./app/src/main/assets/plugins/plugina.jar
rm ./app/src/main/assets/plugins/usercenter.jar
echo "delete last build apk successful"
cp ./plugina/build/outputs/apk/plugina-debug.apk ./app/src/main/assets/plugins/plugina.jar
cp ./usercenter/build/outputs/apk/usercenter-debug.apk ./app/src/main/assets/plugins/usercenter.jar
echo "all successful"