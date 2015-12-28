javac -cp ../libs/commons-codec-1.10.jar Test.java ../src/com/acrcloud/utils/*.java
java -cp ../libs/commons-codec-1.10.jar:../src/:. -Djava.library.path=../libs-so/ Test test.mp3
