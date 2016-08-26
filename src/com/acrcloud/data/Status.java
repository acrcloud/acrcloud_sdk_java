package com.acrcloud.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Status {

	public static final Status Success = new Status("Success", "1.0", 0);

	private String msg;
	private String version = "1.0";
	private Integer code;

	public Status() {
		super();
	}

	public Status(String msg, Integer code) {
		this(msg, "1.0", code);
	}

	public Status(String msg, String version, Integer code) {
		super();
		this.msg = msg;
		this.version = version;
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getVersion() {
		return version;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Status [msg=" + msg + ", version=" + version + ", code=" + code + "]";
	}

}
