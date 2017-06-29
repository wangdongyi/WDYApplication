package com.wdy.project;


import com.google.gson.annotations.SerializedName;
import com.wdy.project.*;

import java.io.Serializable;

/**
 * 作者：王东一
 * 创建时间：2017/6/28.
 */

public class testBean implements Serializable {

    /**
     * resultcode : 200
     * reason : 查询成功
     * result : {"sk":{"temp":"31","wind_direction":"南风","wind_strength":"4级","humidity":"43%","time":"16:40"},"today":{"temperature":"20℃~32℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风3-4 级","week":"星期三","city":"沈阳","date_y":"2017年06月28日","dressing_index":"炎热","dressing_advice":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。","uv_index":"很强","comfort_index":"","wash_index":"较适宜","travel_index":"较适宜","exercise_index":"较适宜","drying_index":""},"future":{"day_20170628":{"temperature":"20℃~32℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风3-4 级","week":"星期三","date":"20170628"},"day_20170629":{"temperature":"20℃~33℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期四","date":"20170629"},"day_20170630":{"temperature":"21℃~34℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期五","date":"20170630"},"day_20170701":{"temperature":"20℃~36℃","weather":"多云转小雨","weather_id":{"fa":"01","fb":"07"},"wind":"南风微风","week":"星期六","date":"20170701"},"day_20170702":{"temperature":"21℃~31℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东南风微风","week":"星期日","date":"20170702"},"day_20170703":{"temperature":"20℃~33℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期一","date":"20170703"},"day_20170704":{"temperature":"21℃~34℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期二","date":"20170704"}}}
     * error_code : 0
     */

    @SerializedName("resultcode")
    private String resultcode;
    @SerializedName("reason")
    private String reason;
    @SerializedName("result")
    private ResultBean result;
    @SerializedName("error_code")
    private int errorCode;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static class ResultBean {
        /**
         * sk : {"temp":"31","wind_direction":"南风","wind_strength":"4级","humidity":"43%","time":"16:40"}
         * today : {"temperature":"20℃~32℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风3-4 级","week":"星期三","city":"沈阳","date_y":"2017年06月28日","dressing_index":"炎热","dressing_advice":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。","uv_index":"很强","comfort_index":"","wash_index":"较适宜","travel_index":"较适宜","exercise_index":"较适宜","drying_index":""}
         * future : {"day_20170628":{"temperature":"20℃~32℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风3-4 级","week":"星期三","date":"20170628"},"day_20170629":{"temperature":"20℃~33℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期四","date":"20170629"},"day_20170630":{"temperature":"21℃~34℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期五","date":"20170630"},"day_20170701":{"temperature":"20℃~36℃","weather":"多云转小雨","weather_id":{"fa":"01","fb":"07"},"wind":"南风微风","week":"星期六","date":"20170701"},"day_20170702":{"temperature":"21℃~31℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东南风微风","week":"星期日","date":"20170702"},"day_20170703":{"temperature":"20℃~33℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期一","date":"20170703"},"day_20170704":{"temperature":"21℃~34℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期二","date":"20170704"}}
         */

        @SerializedName("sk")
        private SkBean sk;
        @SerializedName("today")
        private TodayBean today;
        @SerializedName("future")
        private FutureBean future;

        public SkBean getSk() {
            return sk;
        }

        public void setSk(SkBean sk) {
            this.sk = sk;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public static class SkBean {
            /**
             * temp : 31
             * wind_direction : 南风
             * wind_strength : 4级
             * humidity : 43%
             * time : 16:40
             */

            @SerializedName("temp")
            private String temp;
            @SerializedName("wind_direction")
            private String windDirection;
            @SerializedName("wind_strength")
            private String windStrength;
            @SerializedName("humidity")
            private String humidity;
            @SerializedName("time")
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWindDirection() {
                return windDirection;
            }

            public void setWindDirection(String windDirection) {
                this.windDirection = windDirection;
            }

            public String getWindStrength() {
                return windStrength;
            }

            public void setWindStrength(String windStrength) {
                this.windStrength = windStrength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayBean {
            /**
             * temperature : 20℃~32℃
             * weather : 晴
             * weather_id : {"fa":"00","fb":"00"}
             * wind : 西南风3-4 级
             * week : 星期三
             * city : 沈阳
             * date_y : 2017年06月28日
             * dressing_index : 炎热
             * dressing_advice : 天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。
             * uv_index : 很强
             * comfort_index :
             * wash_index : 较适宜
             * travel_index : 较适宜
             * exercise_index : 较适宜
             * drying_index :
             */

            @SerializedName("temperature")
            private String temperature;
            @SerializedName("weather")
            private String weather;
            @SerializedName("weather_id")
            private WeatherIdBean weatherId;
            @SerializedName("wind")
            private String wind;
            @SerializedName("week")
            private String week;
            @SerializedName("city")
            private String city;
            @SerializedName("date_y")
            private String dateY;
            @SerializedName("dressing_index")
            private String dressingIndex;
            @SerializedName("dressing_advice")
            private String dressingAdvice;
            @SerializedName("uv_index")
            private String uvIndex;
            @SerializedName("comfort_index")
            private String comfortIndex;
            @SerializedName("wash_index")
            private String washIndex;
            @SerializedName("travel_index")
            private String travelIndex;
            @SerializedName("exercise_index")
            private String exerciseIndex;
            @SerializedName("drying_index")
            private String dryingIndex;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WeatherIdBean getWeatherId() {
                return weatherId;
            }

            public void setWeatherId(WeatherIdBean weatherId) {
                this.weatherId = weatherId;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDateY() {
                return dateY;
            }

            public void setDateY(String dateY) {
                this.dateY = dateY;
            }

            public String getDressingIndex() {
                return dressingIndex;
            }

            public void setDressingIndex(String dressingIndex) {
                this.dressingIndex = dressingIndex;
            }

            public String getDressingAdvice() {
                return dressingAdvice;
            }

            public void setDressingAdvice(String dressingAdvice) {
                this.dressingAdvice = dressingAdvice;
            }

            public String getUvIndex() {
                return uvIndex;
            }

            public void setUvIndex(String uvIndex) {
                this.uvIndex = uvIndex;
            }

            public String getComfortIndex() {
                return comfortIndex;
            }

            public void setComfortIndex(String comfortIndex) {
                this.comfortIndex = comfortIndex;
            }

            public String getWashIndex() {
                return washIndex;
            }

            public void setWashIndex(String washIndex) {
                this.washIndex = washIndex;
            }

            public String getTravelIndex() {
                return travelIndex;
            }

            public void setTravelIndex(String travelIndex) {
                this.travelIndex = travelIndex;
            }

            public String getExerciseIndex() {
                return exerciseIndex;
            }

            public void setExerciseIndex(String exerciseIndex) {
                this.exerciseIndex = exerciseIndex;
            }

            public String getDryingIndex() {
                return dryingIndex;
            }

            public void setDryingIndex(String dryingIndex) {
                this.dryingIndex = dryingIndex;
            }

            public static class WeatherIdBean {
                /**
                 * fa : 00
                 * fb : 00
                 */

                @SerializedName("fa")
                private String fa;
                @SerializedName("fb")
                private String fb;

                public String getFa() {
                    return fa;
                }

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public String getFb() {
                    return fb;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }
            }
        }

        public static class FutureBean {
            /**
             * day_20170628 : {"temperature":"20℃~32℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风3-4 级","week":"星期三","date":"20170628"}
             * day_20170629 : {"temperature":"20℃~33℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期四","date":"20170629"}
             * day_20170630 : {"temperature":"21℃~34℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期五","date":"20170630"}
             * day_20170701 : {"temperature":"20℃~36℃","weather":"多云转小雨","weather_id":{"fa":"01","fb":"07"},"wind":"南风微风","week":"星期六","date":"20170701"}
             * day_20170702 : {"temperature":"21℃~31℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东南风微风","week":"星期日","date":"20170702"}
             * day_20170703 : {"temperature":"20℃~33℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期一","date":"20170703"}
             * day_20170704 : {"temperature":"21℃~34℃","weather":"晴","weather_id":{"fa":"00","fb":"00"},"wind":"西南风4-5 级","week":"星期二","date":"20170704"}
             */

            @SerializedName("day_20170628")
            private Day20170628Bean day20170628;
            @SerializedName("day_20170629")
            private Day20170629Bean day20170629;
            @SerializedName("day_20170630")
            private Day20170630Bean day20170630;
            @SerializedName("day_20170701")
            private Day20170701Bean day20170701;
            @SerializedName("day_20170702")
            private Day20170702Bean day20170702;
            @SerializedName("day_20170703")
            private Day20170703Bean day20170703;
            @SerializedName("day_20170704")
            private Day20170704Bean day20170704;

            public Day20170628Bean getDay20170628() {
                return day20170628;
            }

            public void setDay20170628(Day20170628Bean day20170628) {
                this.day20170628 = day20170628;
            }

            public Day20170629Bean getDay20170629() {
                return day20170629;
            }

            public void setDay20170629(Day20170629Bean day20170629) {
                this.day20170629 = day20170629;
            }

            public Day20170630Bean getDay20170630() {
                return day20170630;
            }

            public void setDay20170630(Day20170630Bean day20170630) {
                this.day20170630 = day20170630;
            }

            public Day20170701Bean getDay20170701() {
                return day20170701;
            }

            public void setDay20170701(Day20170701Bean day20170701) {
                this.day20170701 = day20170701;
            }

            public Day20170702Bean getDay20170702() {
                return day20170702;
            }

            public void setDay20170702(Day20170702Bean day20170702) {
                this.day20170702 = day20170702;
            }

            public Day20170703Bean getDay20170703() {
                return day20170703;
            }

            public void setDay20170703(Day20170703Bean day20170703) {
                this.day20170703 = day20170703;
            }

            public Day20170704Bean getDay20170704() {
                return day20170704;
            }

            public void setDay20170704(Day20170704Bean day20170704) {
                this.day20170704 = day20170704;
            }

            public static class Day20170628Bean {
                /**
                 * temperature : 20℃~32℃
                 * weather : 晴
                 * weather_id : {"fa":"00","fb":"00"}
                 * wind : 西南风3-4 级
                 * week : 星期三
                 * date : 20170628
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class Day20170629Bean {
                /**
                 * temperature : 20℃~33℃
                 * weather : 晴
                 * weather_id : {"fa":"00","fb":"00"}
                 * wind : 西南风4-5 级
                 * week : 星期四
                 * date : 20170629
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class Day20170630Bean {
                /**
                 * temperature : 21℃~34℃
                 * weather : 晴
                 * weather_id : {"fa":"00","fb":"00"}
                 * wind : 西南风4-5 级
                 * week : 星期五
                 * date : 20170630
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class Day20170701Bean {
                /**
                 * temperature : 20℃~36℃
                 * weather : 多云转小雨
                 * weather_id : {"fa":"01","fb":"07"}
                 * wind : 南风微风
                 * week : 星期六
                 * date : 20170701
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class Day20170702Bean {
                /**
                 * temperature : 21℃~31℃
                 * weather : 中雨
                 * weather_id : {"fa":"08","fb":"08"}
                 * wind : 东南风微风
                 * week : 星期日
                 * date : 20170702
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class Day20170703Bean {
                /**
                 * temperature : 20℃~33℃
                 * weather : 晴
                 * weather_id : {"fa":"00","fb":"00"}
                 * wind : 西南风4-5 级
                 * week : 星期一
                 * date : 20170703
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class Day20170704Bean {
                /**
                 * temperature : 21℃~34℃
                 * weather : 晴
                 * weather_id : {"fa":"00","fb":"00"}
                 * wind : 西南风4-5 级
                 * week : 星期二
                 * date : 20170704
                 */

                @SerializedName("temperature")
                private String temperature;
                @SerializedName("weather")
                private String weather;
                @SerializedName("weather_id")
                private TodayBean.WeatherIdBean weatherId;
                @SerializedName("wind")
                private String wind;
                @SerializedName("week")
                private String week;
                @SerializedName("date")
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public TodayBean.WeatherIdBean getWeatherId() {
                    return weatherId;
                }

                public void setWeatherId(TodayBean.WeatherIdBean weatherId) {
                    this.weatherId = weatherId;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }
        }
    }
}
