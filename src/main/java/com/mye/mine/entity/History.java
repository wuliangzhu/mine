package com.mye.mine.entity;

import java.util.Date;

public class History implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private String TId;						//tid
    private Long deviceId;					//设备deviceId
    private Date collectTime;				//数据产生时间
    private Integer switch_;				//开关机
    private Integer sleepMode;				//睡眠模式
    private Integer runModel;				//运行模式 5通风 4除湿 3制冷 2制热 1自动
    private Integer windLevel;				//风力等级
    private Integer tempratureSet;			//设置温度
    private Float temperatureReal;			//室内温度
    private Float temperatureAlgorithm;		//算法温度
    private Integer humidityReal;			//温度
    private Integer light;					//光感
    private Integer currentPower;			//即时功率
    private Float currentElec;				//电流
    private Integer voltage;				//电压
    private Integer comfortFlag;			//节能开关：1：打开 0：关闭
    private Integer actualTotalPowerFlag;	//理论用电量算法开关
    private Integer contralFailFlag;		//控制失效标志位：1-不算节电量 0-计算节电量
    private Integer userSleep;				//用户是否睡眠(用户关灯睡觉了)
    private Integer algorithmInterval;		//算法区间
    private Float totalElectric;			//累计用电量
    private Float	dutyCycle0;				//占空比0
    private Float	dutyCycle1;				//占空比1
    private Integer	tempture_type;			//当前温度类型，0：标识内部    1：无线
    private Float temperature_wireless;		//无线传感器温度温度
    private Integer temperatureOut;			//室外温度
    private Float use_elec;					//较上一条指令的用电量增量
    private Float save_elec;				//较上一条指令的节电量
    private Float dTime;
    private Integer status;                 //状态

    public Float getdTime() {
        return dTime;
    }
    public void setdTime(Float dTime) {
        this.dTime = dTime;
    }
    public String getTId() {
        return TId;
    }
    public void setTId(String tId) {
        TId = tId;
    }
    public Long getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
    public Date getCollectTime() {
        return collectTime;
    }
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
    public Integer getSwitch_() {
        return switch_;
    }
    public void setSwitch_(Integer switch_) {
        this.switch_ = switch_;
    }
    public Integer getSleepMode() {
        return sleepMode;
    }
    public void setSleepMode(Integer sleepMode) {
        this.sleepMode = sleepMode;
    }
    public Integer getRunModel() {
        return runModel;
    }
    public void setRunModel(Integer runModel) {
        this.runModel = runModel;
    }
    public Integer getWindLevel() {
        return windLevel;
    }
    public void setWindLevel(Integer windLevel) {
        this.windLevel = windLevel;
    }
    public Integer getTempratureSet() {
        return tempratureSet;
    }
    public void setTempratureSet(Integer tempratureSet) {
        this.tempratureSet = tempratureSet;
    }
    public Float getTemperatureReal() {
        return temperatureReal;
    }
    public void setTemperatureReal(Float temperatureReal) {
        this.temperatureReal = temperatureReal;
    }
    public Float getTemperatureAlgorithm() {
        return temperatureAlgorithm;
    }
    public void setTemperatureAlgorithm(Float temperatureAlgorithm) {
        this.temperatureAlgorithm = temperatureAlgorithm;
    }
    public Integer getHumidityReal() {
        return humidityReal;
    }
    public void setHumidityReal(Integer humidityReal) {
        this.humidityReal = humidityReal;
    }
    public Integer getLight() {
        return light;
    }
    public void setLight(Integer light) {
        this.light = light;
    }
    public Integer getCurrentPower() {
        return currentPower;
    }
    public void setCurrentPower(Integer currentPower) {
        this.currentPower = currentPower;
    }
    public Float getCurrentElec() {
        return currentElec;
    }
    public void setCurrentElec(Float currentElec) {
        this.currentElec = currentElec;
    }
    public Integer getVoltage() {
        return voltage;
    }
    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }
    public Integer getComfortFlag() {
        return comfortFlag;
    }
    public void setComfortFlag(Integer comfortFlag) {
        this.comfortFlag = comfortFlag;
    }
    public Integer getActualTotalPowerFlag() {
        return actualTotalPowerFlag;
    }
    public void setActualTotalPowerFlag(Integer actualTotalPowerFlag) {
        this.actualTotalPowerFlag = actualTotalPowerFlag;
    }
    public Integer getContralFailFlag() {
        return contralFailFlag;
    }
    public void setContralFailFlag(Integer contralFailFlag) {
        this.contralFailFlag = contralFailFlag;
    }
    public Integer getUserSleep() {
        return userSleep;
    }
    public void setUserSleep(Integer userSleep) {
        this.userSleep = userSleep;
    }
    public Integer getAlgorithmInterval() {
        return algorithmInterval;
    }
    public void setAlgorithmInterval(Integer algorithmInterval) {
        this.algorithmInterval = algorithmInterval;
    }
    public Float getTotalElectric() {
        return totalElectric;
    }
    public void setTotalElectric(Float totalElectric) {
        this.totalElectric = totalElectric;
    }
    public Float getDutyCycle0() {
        return dutyCycle0;
    }
    public void setDutyCycle0(Float dutyCycle0) {
        this.dutyCycle0 = dutyCycle0;
    }
    public Float getDutyCycle1() {
        return dutyCycle1;
    }
    public void setDutyCycle1(Float dutyCycle1) {
        this.dutyCycle1 = dutyCycle1;
    }
    public Integer getTempture_type() {
        return tempture_type;
    }
    public void setTempture_type(Integer tempture_type) {
        this.tempture_type = tempture_type;
    }
    public Float getTemperature_wireless() {
        return temperature_wireless;
    }
    public void setTemperature_wireless(Float temperature_wireless) {
        this.temperature_wireless = temperature_wireless;
    }
    public Integer getTemperatureOut() {
        return temperatureOut;
    }
    public void setTemperatureOut(Integer temperatureOut) {
        this.temperatureOut = temperatureOut;
    }
    public Float getUse_elec() {
        return use_elec;
    }
    public void setUse_elec(Float use_elec) {
        this.use_elec = use_elec;
    }
    public Float getSave_elec() {
        return save_elec;
    }
    public void setSave_elec(Float save_elec) {
        this.save_elec = save_elec;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
