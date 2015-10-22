javac -classpath ../libs/commons-codec-1.10.jar Test.java ../src/com/acrcloud/utils/*.java
java -Djava.library.path=../libs-so -classpath .;../src;../libs/commons-codec-1.10.jar Test test.mp3