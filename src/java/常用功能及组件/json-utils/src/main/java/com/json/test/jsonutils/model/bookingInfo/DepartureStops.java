package com.json.test.jsonutils.model.bookingInfo;

public class DepartureStops {
    private String _id;

    private boolean IsSelect;

    private int StopID;

    private String StopName;

    private String DepartureTime;

    private String RangeBeginTime;

    private String RangeEndTime;

    private int StopType;

    private int Priority;

    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setIsSelect(boolean IsSelect){
        this.IsSelect = IsSelect;
    }
    public boolean getIsSelect(){
        return this.IsSelect;
    }
    public void setStopID(int StopID){
        this.StopID = StopID;
    }
    public int getStopID(){
        return this.StopID;
    }
    public void setStopName(String StopName){
        this.StopName = StopName;
    }
    public String getStopName(){
        return this.StopName;
    }
    public void setDepartureTime(String DepartureTime){
        this.DepartureTime = DepartureTime;
    }
    public String getDepartureTime(){
        return this.DepartureTime;
    }
    public void setRangeBeginTime(String RangeBeginTime){
        this.RangeBeginTime = RangeBeginTime;
    }
    public String getRangeBeginTime(){
        return this.RangeBeginTime;
    }
    public void setRangeEndTime(String RangeEndTime){
        this.RangeEndTime = RangeEndTime;
    }
    public String getRangeEndTime(){
        return this.RangeEndTime;
    }
    public void setStopType(int StopType){
        this.StopType = StopType;
    }
    public int getStopType(){
        return this.StopType;
    }
    public void setPriority(int Priority){
        this.Priority = Priority;
    }
    public int getPriority(){
        return this.Priority;
    }
}
