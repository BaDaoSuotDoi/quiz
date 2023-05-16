package com.badao.quiz.model;

public class RecordDestroySync {
    private int id;
    private int objectId ;
    private String tableName;
    private int parentId;
    private String parentName;

    public RecordDestroySync(int id, int objectId, String tableName, int parentId, String parentName) {
        this.id = id;
        this.objectId = objectId;
        this.tableName = tableName;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public RecordDestroySync( int objectId, String tableName, int parentId, String parentName) {
        this.objectId = objectId;
        this.tableName = tableName;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "RecordDestroySync{" +
                "id=" + id +
                ", objectId=" + objectId +
                ", tableName='" + tableName + '\'' +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
