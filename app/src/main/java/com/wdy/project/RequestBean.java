package com.wdy.project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：王东一
 * 创建时间：2017/6/29.
 */

public class RequestBean {

    /**
     * data : {"yesterday":{"date":"28日星期三","high":"高温 35℃","fx":"南风","low":"低温 24℃","fl":"微风","type":"多云"},"city":"北京","aqi":"66","forecast":[{"date":"29日星期四","high":"高温 35℃","fengli":"微风级","low":"低温 24℃","fengxiang":"南风","type":"雷阵雨"},{"date":"30日星期五","high":"高温 36℃","fengli":"微风级","low":"低温 25℃","fengxiang":"南风","type":"多云"},{"date":"1日星期六","high":"高温 33℃","fengli":"微风级","low":"低温 24℃","fengxiang":"南风","type":"多云"},{"date":"2日星期天","high":"高温 31℃","fengli":"微风级","low":"低温 23℃","fengxiang":"南风","type":"阴"},{"date":"3日星期一","high":"高温 32℃","fengli":"微风级","low":"低温 24℃","fengxiang":"南风","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"}
     * status : 1000
     * desc : OK
     */

    @SerializedName("data")
    private DataBean data;
    @SerializedName("status")
    private int status;
    @SerializedName("desc")
    private String desc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class DataBean {
        /**
         * yesterday : {"date":"28日星期三","high":"高温 35℃","fx":"南风","low":"低温 24℃","fl":"微风","type":"多云"}
         * city : 北京
         * aqi : 66
         * forecast : [{"date":"29日星期四","high":"高温 35℃","fengli":"微风级","low":"低温 24℃","fengxiang":"南风","type":"雷阵雨"},{"date":"30日星期五","high":"高温 36℃","fengli":"微风级","low":"低温 25℃","fengxiang":"南风","type":"多云"},{"date":"1日星期六","high":"高温 33℃","fengli":"微风级","low":"低温 24℃","fengxiang":"南风","type":"多云"},{"date":"2日星期天","high":"高温 31℃","fengli":"微风级","low":"低温 23℃","fengxiang":"南风","type":"阴"},{"date":"3日星期一","high":"高温 32℃","fengli":"微风级","low":"低温 24℃","fengxiang":"南风","type":"多云"}]
         * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
         * wendu : 27
         */

        @SerializedName("yesterday")
        private YesterdayBean yesterday;
        @SerializedName("city")
        private String city;
        @SerializedName("aqi")
        private String aqi;
        @SerializedName("ganmao")
        private String ganmao;
        @SerializedName("wendu")
        private String wendu;
        @SerializedName("forecast")
        private List<ForecastBean> forecast;

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 28日星期三
             * high : 高温 35℃
             * fx : 南风
             * low : 低温 24℃
             * fl : 微风
             * type : 多云
             */

            @SerializedName("date")
            private String date;
            @SerializedName("high")
            private String high;
            @SerializedName("fx")
            private String fx;
            @SerializedName("low")
            private String low;
            @SerializedName("fl")
            private String fl;
            @SerializedName("type")
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean {
            /**
             * date : 29日星期四
             * high : 高温 35℃
             * fengli : 微风级
             * low : 低温 24℃
             * fengxiang : 南风
             * type : 雷阵雨
             */

            @SerializedName("date")
            private String date;
            @SerializedName("high")
            private String high;
            @SerializedName("fengli")
            private String fengli;
            @SerializedName("low")
            private String low;
            @SerializedName("fengxiang")
            private String fengxiang;
            @SerializedName("type")
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
