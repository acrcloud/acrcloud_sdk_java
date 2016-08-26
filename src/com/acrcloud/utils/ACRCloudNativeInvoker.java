
package com.acrcloud.utils;

/**
 * <p>
 * ACRCloudExtrTool Copyright 2015 ACRCloud v1.0.0
 * </p>
 * <p>
 * This module can create "ACRCloud Fingerprint" from most of audio/video file.(
 * Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...;Video: mp4, mkv, wmv, flv,
 * ts, avi ...).
 * </p>
 * 
 * @author qinxue.pan E-mail: xue@acrcloud.com
 * @version 1.0.0
 * @create 2015.10.01
 * 
 **/
public interface ACRCloudNativeInvoker {

	/**
	 * @param fileName
	 *            Path of input file;
	 * @param startTimeSeconds
	 *            Start time of input file, default is 0;
	 * @param audioLenSeconds
	 *            Length of audio data you need. if you create recognize
	 *            fingerprint, default is 12 seconds, if you create db
	 *            fingerprint, it is not usefully;
	 * @param isDB
	 *            If it is True, it will create db fingerprint;
	 * @return
	 */
	byte[] createFingerprintByFile(String fileName, int startTimeSeconds, int audioLenSeconds, boolean isDB);

	/**
	 * @param dataBuffer
	 *            data buffer of input file;
	 * @param dataBufferLen
	 *            the length of data buffer of input file;
	 * @param startTimeSeconds
	 *            Start time of input file, default is 0;
	 * @param audioLenSeconds
	 *            Length of audio data you need. if you create recognize
	 *            fingerprint, default is 12 seconds, if you create db
	 *            fingerprint, it is not usefully;
	 * @param isDB
	 *            If it is True, it will create db fingerprint;
	 * @return
	 */
	byte[] createFingerprintByFileBuffer(byte[] dataBuffer, int dataBufferLen, int startTimeSeconds,
			int audioLenSeconds, boolean isDB);

	byte[] createFingerprint(byte[] dataBuffer, int dataBufferLen, boolean isDB);

	/**
	 * It will return the audio data(RIFF (little-endian) data, WAVE audio,
	 * Microsoft PCM, 16 bit, mono 8000 Hz);
	 * 
	 * @param fileName
	 *            Path of input file;
	 * @param startTimeSeconds
	 *            Start time of input file, default is 0;
	 * @param audioLenSeconds
	 *            Length of audio data you need, if it is 0, will decode all the
	 *            audio;
	 * @return the audio data(RIFF (little-endian) data, WAVE audio, Microsoft
	 *         PCM, 16 bit, mono 8000 Hz);
	 */
	byte[] decodeAudioByFile(String fileName, int startTimeSeconds, int audioLenSeconds);

	/**
	 * It will return the audio data(RIFF (little-endian) data, WAVE audio,
	 * Microsoft PCM, 16 bit, mono 8000 Hz);
	 * 
	 * @param dataBuffer
	 *            data buffer of input file;
	 * @param dataBufferLen
	 *            the length of data buffer of input file;
	 * @param startTimeSeconds
	 *            Start time of input file, default is 0;
	 * @param audioLenSeconds
	 *            Length of audio data you need, if it is 0, will decode all the
	 *            audio;
	 * @return the audio data(RIFF (little-endian) data, WAVE audio, Microsoft
	 *         PCM, 16 bit, mono 8000 Hz);
	 */
	byte[] decodeAudioByFileBuffer(byte[] dataBuffer, int dataBufferLen, int startTimeSeconds, int audioLenSeconds);

	/**
	 * set debug, and print all info
	 */
	void setDebug();

	/**
	 * return the doc of this module
	 * 
	 * @return the doc of this module
	 */
	String getDoc();

	int getDurationMSByFile(String fileName);
}
