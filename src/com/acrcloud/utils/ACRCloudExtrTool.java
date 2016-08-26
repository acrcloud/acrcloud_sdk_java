package com.acrcloud.utils;

class ACRCloudExtrTool implements ACRCloudNativeInvoker {

	static {
		try {
			System.loadLibrary("acrcloud_extr_tool");
			native_init();
		} catch (Exception e) {
			System.err.println("acrcloud_extr_tool loadLibrary error!");
		}
	}

	public static ACRCloudNativeInvoker getDefaultNativeInvoker() {
		String osName = System.getProperty("os.name");
		String osArch = System.getProperty("os.arch");
		if (osName.startsWith("Linux") && osArch.equals("x86_64")) {
			return new Linux64ACRCloudNativeInvoker();
		}
		if (osName.startsWith("Mac OS") && osArch.equals("x86_64")) {
			return new Mac64ACRCloudNativeInvoker();
		}
		if (osName.startsWith("Windows")) {
			if (osArch.equals("x86_64")) {
				return new Mac64ACRCloudNativeInvoker();
			} else if (osArch.equals("x86")) {
				return new Mac64ACRCloudNativeInvoker();
			}
		}
		throw new UnsupportedOperationException(String.format("Unsupported os: %s, arch: %s", osName, osArch));
	}

	@Override
	public byte[] createFingerprintByFile(String fileName, int startTimeSeconds, int audioLenSeconds, boolean isDB) {
		if (fileName == null || "".equals(fileName)) {
			return null;
		}
		return native_create_fingerprint_by_file(fileName, startTimeSeconds, audioLenSeconds, isDB);
	}

	@Override
	public byte[] createFingerprintByFileBuffer(byte[] dataBuffer, int dataBufferLen, int startTimeSeconds,
			int audioLenSeconds, boolean isDB) {
		if (dataBuffer == null || dataBufferLen <= 0) {
			return null;
		}
		return native_create_fingerprint_by_filebuffer(dataBuffer, dataBufferLen, startTimeSeconds, audioLenSeconds,
				isDB);
	}

	@Override
	public byte[] createFingerprint(byte[] dataBuffer, int dataBufferLen, boolean isDB) {
		if (dataBuffer == null || dataBufferLen <= 0) {
			return null;
		}
		return native_create_fingerprint(dataBuffer, dataBufferLen, isDB);
	}

	@Override
	public byte[] decodeAudioByFile(String fileName, int startTimeSeconds, int audioLenSeconds) {
		if (fileName == null || "".equals(fileName)) {
			return null;
		}
		return native_decode_audio_by_file(fileName, startTimeSeconds, audioLenSeconds);
	}

	@Override
	public byte[] decodeAudioByFileBuffer(byte[] dataBuffer, int dataBufferLen, int startTimeSeconds,
			int audioLenSeconds) {
		if (dataBuffer == null || dataBufferLen <= 0) {
			return null;
		}
		return native_decode_audio_by_filebuffer(dataBuffer, dataBufferLen, startTimeSeconds, audioLenSeconds);
	}

	@Override
	public void setDebug() {
		native_set_debug();
	}

	@Override
	public String getDoc() {
		return native_get_doc();
	}

	@Override
	public int getDurationMSByFile(String fileName) {
		if (fileName == null || "".equals(fileName)) {
			return 0;
		}
		return native_get_duration_ms_by_file(fileName);
	}

	private static native void native_init();

	private native byte[] native_create_fingerprint_by_file(String file_name, int start_time_seconds,
			int audio_len_seconds, boolean is_db_fingerprint);

	private native byte[] native_create_fingerprint_by_filebuffer(byte[] data_buffer, int data_buffer_len,
			int start_time_seconds, int audio_len_seconds, boolean is_db_fingerprint);

	private native byte[] native_create_fingerprint(byte[] wav_data_buffer, int wav_data_buffer_len,
			boolean is_db_fingerprint);

	private native byte[] native_decode_audio_by_file(String file_name, int start_time_seconds, int audio_len_seconds);

	private native byte[] native_decode_audio_by_filebuffer(byte[] data_buffer, int data_buffer_len,
			int start_time_seconds, int audio_len_seconds);

	private native void native_set_debug();

	private native int native_get_duration_ms_by_file(String file_name);

	private native String native_get_doc();

}
