package com.acrcloud.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Result {

	public static final Result HTTP_ERROR = new Result("Http Error", 3000);

	public static final Result NO_RESULT = new Result("No Result", 1001);

	public static final Result DECODE_AUDIO_ERROR = new Result("Can not decode audio data", 2005);

	private Status status;
	@JsonProperty("result_type")
	private Integer resultType;
	private Metadata metadata;

	public Result() {
		super();
	}

	public Result(String msg, Integer code) {
		super();
		this.status = new Status(msg, code);
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public Integer getResultType() {
		return resultType;
	}

	public Status getStatus() {
		return status;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		if (metadata == null & resultType == null && status != null) {
			return status.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (metadata == null & resultType == null && status != null && obj != null && getClass() == obj.getClass()) {
			return status.equals(((Result) obj).status);
		}
		return super.equals(obj);
	}
}
