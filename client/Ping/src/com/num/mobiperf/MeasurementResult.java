/* Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.num.mobiperf;

import java.util.HashMap;

/**
 * POJO that represents the result of a measurement
 * 
 * @see MeasurementDesc
 */
public class MeasurementResult {

	private String deviceId;

	private DeviceProperty properties;
	private long timestamp;
	private boolean success;
	private String taskKey;
	private String type;
	private HashMap<String, String> parameters;
	private HashMap<String, String> values;

	/**
	 * @param deviceProperty
	 * @param type
	 * @param timestamp
	 * @param success
	 * @param measurementDesc
	 */
	public MeasurementResult(String id, DeviceProperty deviceProperty,
			String type, long timeStamp, boolean success) {
		super();
		this.taskKey = null;
		this.deviceId = id;
		this.type = type;
		this.properties = deviceProperty;
		this.timestamp = timeStamp;
		this.success = success;
		this.parameters = new HashMap<String, String>();
		this.values = new HashMap<String, String>();
	}

	/* Add the measurement results of type String into the class */
	public void addParameters(String resultType, Object resultVal) {
		this.parameters.put(resultType,
				MeasurementJsonConvertor.toJsonString(resultVal));
	}
	
	/* Add the measurement results of type String into the class */
	public void addValue(String resultType, Object resultVal) {
		this.values.put(resultType,
				MeasurementJsonConvertor.toJsonString(resultVal));
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public DeviceProperty getProperties() {
		return properties;
	}

	public void setProperties(DeviceProperty properties) {
		this.properties = properties;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public HashMap<String, String> getValues() {
		return values;
	}

	public void setValues(HashMap<String, String> values) {
		this.values = values;
	}

}
