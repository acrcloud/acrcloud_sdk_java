/**
 *
 *  @author qinxue.pan E-mail: xue@acrcloud.com
 *  @version 1.0.0
 *  @create 2015.10.01
 *  
 **/

/*
Copyright 2015 ACRCloud Recognizer v1.0.0

This module can recognize ACRCloud by most of audio/video file. 
        Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...
        Video: mp4, mkv, wmv, flv, ts, avi ...
*/

package com.acrcloud.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class ACRCloudRecognizer {

    private String host = "ap-southeast-1.api.acrcloud.com";
    private String protocol = "https";
    private String accessKey = "";
    private String accessSecret = "";
    private int timeout = 5 * 1000; // ms
    private boolean debug = false;
    private RecognizerType recType = RecognizerType.AUDIO;

    public enum RecognizerType {
        AUDIO, HUMMING, BOTH
    }

    public ACRCloudRecognizer(Map<String, Object> config) {
        if (config.get("host") != null) {
            this.host = (String)config.get("host");
        }
        if (config.get("protocol") != null) {
            this.protocol = (String)config.get("protocol");
        }
        if (config.get("access_key") != null) {
            this.accessKey = (String)config.get("access_key");
        }
        if (config.get("access_secret") != null) {
            this.accessSecret = (String)config.get("access_secret");
        }
        if (config.get("timeout") != null) {
            this.timeout = 1000 * ((Integer)config.get("timeout")).intValue();
        }
        if (config.get("rec_type") != null) {
            this.recType = (RecognizerType)(config.get("rec_type"));
        }
        if (config.get("debug") != null) {
            this.debug = ((Boolean)config.get("debug")).booleanValue();
            if (this.debug) {
                ACRCloudExtrTool.setDebug();
            }
        }
    }

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
    public String recognize(byte[] wavAudioBuffer, int wavAudioBufferLen)
    {
        String result = ACRCloudStatusCode.NO_RESULT;
        try {
            byte[] fp = null;
            byte[] fpHum = null;
            switch (this.recType) {
                case AUDIO:
                    fp = ACRCloudExtrTool.createFingerprint(wavAudioBuffer, wavAudioBufferLen, false);
                    break;
                case HUMMING:
                    fpHum = ACRCloudExtrTool.createHummingFingerprint(wavAudioBuffer, wavAudioBufferLen);
                    break;
                default:
                    fp = ACRCloudExtrTool.createFingerprint(wavAudioBuffer, wavAudioBufferLen, false);
                    fpHum = ACRCloudExtrTool.createHummingFingerprint(wavAudioBuffer, wavAudioBufferLen);
            }
            
            result = this.doRecogize(fp, fpHum);
        } catch (Exception e) {
            e.printStackTrace();
            result = ACRCloudStatusCode.UNKNOW_ERROR;
        }
        return result;
    }

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
    public String recognizeByFileBuffer(byte[] fileBuffer, int fileBufferLen, int startSeconds)
    {
        return this.recognizeByFileBuffer(fileBuffer, fileBufferLen, startSeconds, 12);
    }

    public String recognizeByFileBuffer(byte[] fileBuffer, int fileBufferLen, int startSeconds, int audioLenSeconds)
    {
        return this.recognizeByFileBuffer(fileBuffer, fileBufferLen, startSeconds, audioLenSeconds, null);
    }

    public String recognizeByFileBuffer(byte[] fileBuffer, int fileBufferLen, int startSeconds, int audioLenSeconds, Map<String, String> userParams)
    {
        String result = ACRCloudStatusCode.NO_RESULT;
        try {
            byte[] fp = null;
            byte[] fpHum = null;
            switch (this.recType) {
                case AUDIO:
                    fp = ACRCloudExtrTool.createFingerprintByFileBuffer(fileBuffer, fileBufferLen, startSeconds, audioLenSeconds, false);
                    break;
                case HUMMING:
                    fpHum = ACRCloudExtrTool.createHummingFingerprintByFileBuffer(fileBuffer, fileBufferLen, startSeconds, audioLenSeconds);
                    break;
                default:
                    fp = ACRCloudExtrTool.createFingerprintByFileBuffer(fileBuffer, fileBufferLen, startSeconds, audioLenSeconds, false);
                    fpHum = ACRCloudExtrTool.createHummingFingerprintByFileBuffer(fileBuffer, fileBufferLen, startSeconds, audioLenSeconds);
            }

            result = this.doRecogize(fp, fpHum, "fingerprint", userParams);
        } catch (Exception e) {
            e.printStackTrace();
            result = ACRCloudStatusCode.UNKNOW_ERROR;
        }
        return result;
    }

    public String recognizeByFpBuffer(byte[] fpBuffer, int fpBufferLen, int startSeconds)
    {
        return this.recognizeByFpBuffer(fpBuffer, fpBufferLen, startSeconds, 10);
    }

    public String recognizeByFpBuffer(byte[] fpBuffer, int fpBufferLen, int startSeconds, int audioLenSeconds)
    {
        String result = ACRCloudStatusCode.NO_RESULT;
        try {
            byte[] fp = ACRCloudExtrTool.createFingerprintByFpBuffer(fpBuffer, fpBufferLen, startSeconds, audioLenSeconds);
            result = this.doRecogize(fp, null);
        } catch (Exception e) {
            e.printStackTrace();
            result = ACRCloudStatusCode.UNKNOW_ERROR;
        }
        return result;
    }

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
    public String recognizeByFile(String filePath, int startSeconds)
    {
        return this.recognizeByFile(filePath, startSeconds, 12);
    }

    public String recognizeByFile(String filePath, int startSeconds, int audioLenSeconds)
    {
        return this.recognizeByFile(filePath, startSeconds, audioLenSeconds, null);
    }

    public String recognizeByFile(String filePath, int startSeconds, int audioLenSeconds, Map<String, String> userParams)
    {
        String result = ACRCloudStatusCode.NO_RESULT;
        try {
            byte[] fp = null;
            byte[] fpHum = null;
            switch (this.recType) {
                case AUDIO:
                    fp = ACRCloudExtrTool.createFingerprintByFile(filePath, startSeconds, audioLenSeconds, false);
                    break;
                case HUMMING:
                    fpHum = ACRCloudExtrTool.createHummingFingerprintByFile(filePath, startSeconds, audioLenSeconds);
                    break;
                default:
                    fp = ACRCloudExtrTool.createFingerprintByFile(filePath, startSeconds, audioLenSeconds, false);
                    fpHum = ACRCloudExtrTool.createHummingFingerprintByFile(filePath, startSeconds, audioLenSeconds);
            }

            result = this.doRecogize(fp, fpHum, "fingerprint", userParams);
        } catch (Exception e) {
            e.printStackTrace();
            result = ACRCloudStatusCode.UNKNOW_ERROR;
        }
        return result;
    }

    public String recognizeAudioByFile(String filePath, int startSeconds, int audioLenSeconds)
    {
        return this.recognizeAudioByFile(filePath, startSeconds, audioLenSeconds, null);
    }

    public String recognizeAudioByFile(String filePath, int startSeconds, int audioLenSeconds, Map<String, String> userParams)
    {
        String result = ACRCloudStatusCode.NO_RESULT;
        try {
            byte[] pcmBuffer = ACRCloudExtrTool.decodeAudioByFile(filePath, startSeconds, audioLenSeconds);
            result = this.doRecogize(pcmBuffer, null, "audio", userParams);
        } catch (Exception e) {
            e.printStackTrace();
            result = ACRCloudStatusCode.UNKNOW_ERROR;
        }
        return result;
    }

    public String recognizeAudioBuffer(byte[] audioBuffer, int audioBufferLen, Map<String, String> userParams)
    {
        if (audioBuffer == null) {
            return ACRCloudStatusCode.NO_RESULT;
        }
        byte[] audioBufferNew = audioBuffer;
        if (audioBufferLen < audioBuffer.length) {
            audioBufferNew = new byte[audioBufferLen];
            System.arraycopy(audioBuffer, 0, audioBufferNew, 0, audioBufferLen);
        }
        return this.doRecogize(audioBufferNew, null, "audio", userParams);
    }

    private String doRecogize(byte[] fp, byte[] fpHum) {
        return this.doRecogize(fp, fpHum, "fingerprint", null);
    }
 
    private String doRecogize(byte[] fp, byte[] fpHum, String dataType, Map<String, String> userParams) {
        if ("fingerprint".equals(dataType)) {
            switch (this.recType) {
                case AUDIO:
                    if (fp == null) {
                        return ACRCloudStatusCode.DECODE_AUDIO_ERROR;
                    }
                    if (fp.length == 0) {
                        return ACRCloudStatusCode.NO_RESULT;
                    }
                    break;
                case HUMMING:
                    if (fpHum == null) {
                        return ACRCloudStatusCode.DECODE_AUDIO_ERROR;
                    }
                    if (fpHum.length == 0) {
                        return ACRCloudStatusCode.NO_RESULT;
                    }
                    break;
                default:
                    if (fp == null && fpHum == null) {
                        return ACRCloudStatusCode.DECODE_AUDIO_ERROR;
                    }
                    if ((fp == null || fp.length == 0) && (fpHum == null || fpHum.length == 0)) {
                        return ACRCloudStatusCode.NO_RESULT;
                    }
            }
        } else {
            if (fp == null || fp.length == 0) {
                return ACRCloudStatusCode.DECODE_AUDIO_ERROR;
            }
        }

        String method = "POST";
        String httpURL = "/v1/identify";
        String sigVersion = "1";
        String timestamp = getUTCTimeSeconds();

        String reqURL = this.protocol + "://" + this.host + httpURL;

        String sigStr = method + "\n" + httpURL + "\n" + accessKey + "\n" + dataType + "\n" + sigVersion + "\n" + timestamp;
        String signature = encryptByHMACSHA1(sigStr.getBytes(), this.accessSecret.getBytes());

        Map<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("access_key", this.accessKey);
        if (fp != null && fp.length > 0) {
            postParams.put("sample_bytes", fp.length + "");
            postParams.put("sample", fp);
        }
        if (fpHum != null && fpHum.length > 0) {
            postParams.put("sample_hum_bytes", fpHum.length + "");
            postParams.put("sample_hum", fpHum);
        }

        postParams.put("timestamp", timestamp);
        postParams.put("signature", signature);
        postParams.put("data_type", dataType);
        postParams.put("signature_version", sigVersion);

        if (userParams != null) {
            for (String key : userParams.keySet()) {
                postParams.put(key, userParams.get(key));
            }
        }

        String res = postHttp(reqURL, postParams, this.timeout);

        return res;
    }

    private String encodeBase64(byte[] bstr) {
        Base64 base64 = new Base64();
        return new String(base64.encode(bstr));
    }

    private String encryptByHMACSHA1(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data);
            return encodeBase64(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private String getUTCTimeSeconds() {  
        Calendar cal = Calendar.getInstance();   
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);   
        int dstOffset = cal.get(Calendar.DST_OFFSET);    
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));    
        return cal.getTimeInMillis()/1000 + "";
    }  

    private String postHttp(String posturl, Map<String, Object> params, int timeOut) {
        String res = "";
        String BOUNDARYSTR = "*****2015.10.01.acrcloud.rec.copyright." + System.currentTimeMillis() + "*****";
        String BOUNDARY = "--" + BOUNDARYSTR + "\r\n";
        String ENDBOUNDARY = "--" + BOUNDARYSTR + "--\r\n\r\n";
        String stringKeyHeader = BOUNDARY + "Content-Disposition:form-data;name=\"%s\"" + "\r\n\r\n%s\r\n";
        String filePartHeader = BOUNDARY + "Content-Disposition: form-data;name=\"%s\";filename=\"%s\"\r\n" + "Content-Type:application/octet-stream\r\n\r\n";		
        URL url = null;
        HttpURLConnection conn = null;
        BufferedOutputStream out = null;
        BufferedReader reader = null;
        ByteArrayOutputStream postBufferStream = new ByteArrayOutputStream();
        try {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value instanceof String || value instanceof Integer) {
                    postBufferStream.write(String.format(stringKeyHeader, key, (String)value).getBytes());
                } else if (value instanceof byte[]) {
                    postBufferStream.write(String.format(filePartHeader, key, key).getBytes());
                    postBufferStream.write((byte[]) value);
                    postBufferStream.write("\r\n".getBytes());
                }
            }
            postBufferStream.write(ENDBOUNDARY.getBytes());
            
            url = new URL(posturl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeOut);
            conn.setReadTimeout(timeOut);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-type", "multipart/form-data;boundary=" + BOUNDARYSTR);
            conn.connect();

            out = new BufferedOutputStream(conn.getOutputStream());

            out.write(postBufferStream.toByteArray());
            out.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String tmpRes = "";
                while ((tmpRes = reader.readLine()) != null) {
                    if (tmpRes.length() > 0)
                        res = res + tmpRes;
                }
            } else {
                System.out.println("http error response code " + responseCode);
                res = ACRCloudStatusCode.HTTP_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = ACRCloudStatusCode.HTTP_ERROR;
        } finally {
            try {
                if (postBufferStream != null) {
                    postBufferStream.close();
                    postBufferStream = null;
                }
            } catch (Exception e) {}

            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (Exception e) {}

            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            } catch (Exception e) {}

            try {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }
            } catch (Exception e) {}
        }
        return res;
    }

    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<String, Object>();
        config.put("host", "identify-cn-north-1.acrcloud.cn");
        config.put("access_key", "febbb4ab979004350d206f1c817b2737");
        config.put("access_secret", "0cQK9weOZpsmgi1oLEbbdCGm1ezanlfoE2uUCWiO");
        config.put("debug", false);
        config.put("timeout", 5);

        ACRCloudRecognizer re = new ACRCloudRecognizer(config);
        //String result = re.recognizeByFile(args[0], 80);
        //System.out.println(result);

        //System.out.println(ACRCloudExtrTool.getDoc());
        //ACRCloudExtrTool.setDebug();
        //byte[] fp = ACRCloudExtrTool.createFingerprintByFile(args[0], 30, 12, false);
        //System.out.println(fp.length);
        //byte[] fp = ACRCloudExtrTool.decodeAudioByFile(args[0], 200, 0);
        //System.out.println(fp.length);

        File file = new File("/Users/pony/Downloads/test.m4a");
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

        String result = re.recognizeByFileBuffer(buffer, bufferLen, 80);
        System.out.println(result);

        //byte[] fp = ACRCloudExtrTool.decodeAudioByBuffer(buffer, bufferLen, 200, 0);
        //System.out.println(fp.length);

        //byte[] fp = ACRCloudExtrTool.createFingerprintByBuffer(buffer, bufferLen, 180, 20, false);
        //System.out.println(fp.length);
    }
}

class ACRCloudStatusCode
{
    public static String HTTP_ERROR = "{\"status\":{\"msg\":\"Http Error\", \"code\":3000}}";
    public static String NO_RESULT = "{\"status\":{\"msg\":\"No Result\", \"code\":1001}}";
    public static String DECODE_AUDIO_ERROR = "{\"status\":{\"msg\":\"Can not decode audio data\", \"code\":2005}}";
    public static String RECORD_ERROR = "{\"status\":{\"msg\":\"Record Error\", \"code\":2000}}";
    public static String JSON_ERROR = "{\"status\":{\"msg\":\"json error\", \"code\":2002}}";
    public static String UNKNOW_ERROR = "{\"status\":{\"msg\":\"unknow error\", \"code\":2010}}";
}

