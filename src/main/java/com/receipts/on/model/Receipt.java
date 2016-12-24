package com.receipts.on.model;

import com.mongodb.DBObject;

public class Receipt {

    public Receipt() {
    }

    public Receipt(DBObject object) {
        this.id = object.get("_id").toString();
        this.patientName = (String) object.get("name");
        this.age = (Integer)object.get("age");
    }

    private String id;
	private String patientName;
	private int age;

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}