# acrcloud_sdk_java
create  "ACRCloud Fingerprint" by Audio/Video file, and use "ACRCloud Fingerprint" to recognize metainfos by "ACRCloud webapi".

# Overview
This java SDK can recognize ACRCloud by most of audio/video file.<br>
>>>>Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...<br>
>>>>Video: mp4, mkv, wmv, flv, ts, avi ...

# ACRCloud
Docs: [https://docs.acrcloud.com/](https://docs.acrcloud.com/)<br>
Console: [https://console.acrcloud.com/](https://console.acrcloud.com/)

# Windows Runtime Library 
**If you run the SDK on Windows, you must install this library.**<br>
X86: [download and install Library(windows/vcredist_x86.exe)](https://www.microsoft.com/en-us/download/details.aspx?id=5555)<br>
x64: [download and install Library(windows/vcredist_x64.exe)](https://www.microsoft.com/en-us/download/details.aspx?id=14632)


# Functions
Introduction all API.
## src/com/acrcloud/utils/ACRCloudRecognizer.java
```java
      public String recognizeByFile(String filePath, int startSeconds)
      /**
      *
      *  recognize by file path of (Audio/Video file)
      *          Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...
      *          Video: mp4, mkv, wmv, flv, ts, avi ...
      *
      *  @param filePath query file path
      *  @param startSeconds skip (startSeconds) seconds from from the beginning of (filePath)
      *
      *  @return result
      *
      **/
    
      public String recognizeByFileBuffer(byte[] fileBuffer, int fileBufferLen, int startSeconds)
      /**
      *
      *  recognize by buffer of (Audio/Video file)
      *          Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...
      *          Video: mp4, mkv, wmv, flv, ts, avi ...
      *
      *  @param fileBuffer query buffer
      *  @param fileBufferLen the length of fileBufferLen
      *  @param startSeconds skip (startSeconds) seconds from from the beginning of fileBuffer
      *
      *  @return result
      *
      **/
    
      public String recognize(byte[] wavAudioBuffer, int wavAudioBufferLen)
      /**
      *
      *  recognize by wav audio buffer(RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 8000 Hz)
      *
      *  @param wavAudioBuffer query audio buffer
      *  @param wavAudioBufferLen the length of wavAudioBuffer
      *
      *  @return result
      *
      **/
```

## src/com/acrcloud/utils/ACRCloudExtrTool.java 
```java
public static byte[] createFingerprintByFile(String fileName, int startTimeSeconds, int audioLenSeconds, boolean isDB)
      //fileName: Path of input file;
      //startTimeSeconds: Start time of input file, default is 0;
      //audioLenSeconds: Length of audio data you need. if you create recogize frigerprint, default is 12 seconds, if you create db frigerprint, it is not usefully;
      //isDB: If it is True, it will create db frigerprint;
      //@return "ACRCloud Fingerprint". If can not create frigerprint, return null.

public static byte[] createFingerprintByFileBuffer(byte[] dataBuffer, int dataBufferLen, int startTimeSeconds, int audioLenSeconds, boolean isDB)
      //dataBuffer: data buffer of input file;
      //dataBufferLen: length of dataBuffer
      //startTimeSeconds: Start time of input file, default is 0;
      //audioLenSeconds: Length of audio data you need. if you create recogize frigerprint, default is 12 seconds, if you create db frigerprint, it is not usefully;
      //isDB: If it is True, it will create db frigerprint;
      //@return "ACRCloud Fingerprint". If can not create frigerprint, return null.

public static byte[] createFingerprint(byte[] dataBuffer, int dataBufferLen, boolean isDB)
      //dataBuffer: audio data buffer(RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 8000 Hz);
      //isDB: If it is True, it will create db frigerprint;
      //@return "ACRCloud Fingerprint". If can not create frigerprint, return null.

public static byte[] decodeAudioByFile(String fileName, int startTimeSeconds, int audioLenSeconds) 
      //It will return the audio data(RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 8000 Hz);
      //fileName: Path of input file;
      //startTimeSeconds: Start time of input file, default is 0;
      //audioLenSeconds: Length of audio data you need, if it is 0, will decode all the audio;
      //@return audio data.

public static byte[] decodeAudioByFileBuffer(byte[] dataBuffer, int dataBufferLen, int startTimeSeconds, int audioLenSeconds)
      //It will return the audio data(RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 8000 Hz);
      //dataBuffer: data buffer of input file;
      //startTimeSeconds: Start time of input file, default is 0;
      //audioLenSeconds: Length of audio data you need, if it is 0, will decode all the audio;
      //@return audio data.

def version()
      //return the version of this module
```
# Example
run Test: <br>
\>>> cd test<br>
Replace "xxxxxxxx" below with your project's access_key and access_secret.<br>
\>>> sh run.sh<br>
```java
import java.io.*;
import java.util.Map;
import java.util.HashMap;

import com.acrcloud.utils.ACRCloudRecognizer;

public class Test {

    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<String, Object>();
        config.put("host", "ap-southeast-1.api.acrcloud.com");
        config.put("access_key", "XXXXXXXX");
        config.put("access_secret", "XXXXXXXX");
        config.put("debug", false);
        config.put("timeout", 10); // seconds

        ACRCloudRecognizer re = new ACRCloudRecognizer(config);

        // It will skip 80 seconds.
        String result = re.recognizeByFile(args[0], 80);
        System.out.println(result);
        
        /**
          *
          *  recognize by buffer of (Formatter: Audio/Video)
          *     Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...
          *     Video: mp4, mkv, wmv, flv, ts, avi ...
          *
          *
          **/
        File file = new File(args[0]);
        byte[] buffer = new byte[3 * 1024 * 1024];
        if (!file.exists()) {
            return;
        }
        FileInputStream fin = null;
        int bufferLen = 0;
        try {
            fin = new FileInputStream(file);
            bufferLen = fin.read(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                        fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("bufferLen=" + bufferLen);

        if (bufferLen <= 0)
            return;

        // It will skip 80 seconds from the begginning of (buffer).
        result = re.recognizeByFileBuffer(buffer, bufferLen, 80);
        System.out.println(result);
      }
}
```

# Eclipse Project
**1. create java project:** <br>
  ![image](https://github.com/acrcloud/acrcloud_sdk_java/blob/master/eclipse_tutorial_image/create_project.png) <br>
**2. add jar library, click right-hand on "commons-codec-1.10.jar" >> Build Path >> Add to Build Path:** <br>
  ![image](https://github.com/acrcloud/acrcloud_sdk_java/blob/master/eclipse_tutorial_image/add_jar.png) <br>
  ![image](https://github.com/acrcloud/acrcloud_sdk_java/blob/master/eclipse_tutorial_image/add_jar_1.png) <br>
**3.add (dll or so) library:** <br>
  ![image](https://github.com/acrcloud/acrcloud_sdk_java/blob/master/eclipse_tutorial_image/add_native.png) <br>

