package com.joshuarichardson.fivewaystowellbeing.ui.inspire;

public class InspireRecord {
    private long inspireId;
    private String activityName;
    private String activityType;
    private String activityWayToWellbeing;
    private int emotion;
    private boolean isFavourite;
    private InspireType inspireType;

    public InspireRecord(long id, String activityName, String activityType, String activityWayToWellbeing, int emotion, boolean isFavourite) {
        this.setInspireId(id);
        this.setActivityName(activityName);
        this.setActivityType(activityType);
        this.setWayToWellbeing(activityWayToWellbeing);
        this.setEmotion(emotion);
        this.isFavourite(isFavourite);
        this.setInspireType(InspireType.Text);
    }

    public InspireRecord(long id, String activityName, String activityType, String activityWayToWellbeing, int emotion, boolean isFavourite, InspireType inspireType) {
        this.setInspireId(id);
        this.setActivityName(activityName);
        this.setActivityType(activityType);
        this.setWayToWellbeing(activityWayToWellbeing);
        this.setEmotion(emotion);
        this.isFavourite(isFavourite);
        this.setInspireType(inspireType);
    }

    private void setInspireType(InspireType inspireType) {
        this.inspireType = inspireType;
    }

    public void isFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    private void setWayToWellbeing(String wayToWellbeing) {
        this.activityWayToWellbeing = wayToWellbeing;
    }

    public void setInspireId(long inspireId) {
        this.inspireId = inspireId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityType(String type) {
        this.activityType = type;
    }

    private void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getActivityWayToWellbeing() {
        return this.activityWayToWellbeing;
    }

    public int getEmotion() {
        return this.emotion;
    }

    public long getInspireId() {
        return this.inspireId;
    }

    public boolean isFavourite() {
        return this.isFavourite;
    }

    public InspireType getInspireType() {
        return inspireType;
    }
}
