package org.sunny.DAO;

import java.io.Serializable;

public class StockInfo implements Serializable{
    private static final Long serialVersion = 1L;

    //股票编码
    private String stockCode;

    //股票名称
    private String stockName;

    //交易时间
    private Long tradeTime;

    //昨日收盘价
    private Float preClossPrice;

    //开盘价
    private Float openPrice;

    //当前价格
    private Float currentPrice;

    //今日最高价
    private Float highPrice;

    //今日最低价
    private Float lowPrice;

    public static Long getSerialVersion() {
        return serialVersion;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Long tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Float getPreClossPrice() {
        return preClossPrice;
    }

    public void setPreClossPrice(Float preClossPrice) {
        this.preClossPrice = preClossPrice;
    }

    public Float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Float openPrice) {
        this.openPrice = openPrice;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Float highPrice) {
        this.highPrice = highPrice;
    }

    public Float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Float lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", tradeTime=" + tradeTime +
                ", preClossPrice=" + preClossPrice +
                ", openPrice=" + openPrice +
                ", currentPrice=" + currentPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockInfo stockInfo = (StockInfo) o;

        if (!stockCode.equals(stockInfo.stockCode)) return false;
        if (!stockName.equals(stockInfo.stockName)) return false;
        if (!tradeTime.equals(stockInfo.tradeTime)) return false;
        if (preClossPrice != null ? !preClossPrice.equals(stockInfo.preClossPrice) : stockInfo.preClossPrice != null)
            return false;
        if (openPrice != null ? !openPrice.equals(stockInfo.openPrice) : stockInfo.openPrice != null) return false;
        if (!currentPrice.equals(stockInfo.currentPrice)) return false;
        if (highPrice != null ? !highPrice.equals(stockInfo.highPrice) : stockInfo.highPrice != null) return false;
        return lowPrice != null ? lowPrice.equals(stockInfo.lowPrice) : stockInfo.lowPrice == null;
    }

    @Override
    public int hashCode() {
        int result = stockCode.hashCode();
        result = 31 * result + stockName.hashCode();
        result = 31 * result + tradeTime.hashCode();
        result = 31 * result + (preClossPrice != null ? preClossPrice.hashCode() : 0);
        result = 31 * result + (openPrice != null ? openPrice.hashCode() : 0);
        result = 31 * result + currentPrice.hashCode();
        result = 31 * result + (highPrice != null ? highPrice.hashCode() : 0);
        result = 31 * result + (lowPrice != null ? lowPrice.hashCode() : 0);
        return result;
    }


}
