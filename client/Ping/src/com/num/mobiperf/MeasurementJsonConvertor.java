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


import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.myjson.FieldNamingPolicy;
import com.google.myjson.Gson;
import com.google.myjson.GsonBuilder;
import com.google.myjson.JsonDeserializationContext;
import com.google.myjson.JsonDeserializer;
import com.google.myjson.JsonElement;
import com.google.myjson.JsonParseException;
import com.google.myjson.JsonPrimitive;
import com.google.myjson.JsonSerializationContext;
import com.google.myjson.JsonSerializer;

/**
 * Utility class that use the gson library to provide bidirectional conversion between
 * measurement objects (descriptions, tasks, and results, etc.) and JSON objects. 
 * New types of MeasurementDesc should be registered in the static HashMap initialization
 * section.
 */
@SuppressWarnings("rawtypes")
public class MeasurementJsonConvertor {
  /* 1. Automatically perform bidirectional translations for fields in java 'lowerCaseCamel' style 
   * to JSON 'lower_case_with_underscores' style. 
   * 2. Serialize and de-serialize UTC format date string
   * 3. It also serializes all null fields to 'null'
   */
  public static Gson gson = new GsonBuilder().serializeNulls().
      registerTypeAdapter(Date.class, new DateTypeConverter()).
      setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  private static final DateFormat dateFormat = 
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
  }
  
  
  public static JSONObject encodeToJson(Object obj) throws JSONException {
    String str = gson.toJson(obj);
    return new JSONObject(str);
  }
  
  public static String toJsonString(Object obj) {
    return gson.toJson(obj);
  }
  
  public static Gson getGsonInstance() {
    return gson;
  }

  private static class DateTypeConverter implements JsonSerializer<Date>,
                                                        JsonDeserializer<Date> {
    public JsonElement serialize(Date src, Type srcType, JsonSerializationContext context) {
      return new JsonPrimitive(formatDate(src));
    }

    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context)
        throws JsonParseException {
      try {
        return parseDate(json.getAsString());
      } catch (NumberFormatException e) {
        throw new JsonParseException("Cannot convert time string: "  + json.toString());
      } catch (IllegalArgumentException e) {
        throw new JsonParseException("Cannot convert time string: " + json.toString());
      } catch (ParseException e) {
        throw new JsonParseException("Cannot convert UTC time string: "  + json.toString());
      }
    }
  }
  
  private static Date parseDate(String dateString) throws ParseException {
    return dateFormat.parse(dateString);
  }

  private static String formatDate(Date date) {
    return dateFormat.format(date);
  }
}
