package com.ai.przychodnia.helpers;

public enum Type
{
	admins(2), doctors(1), patients(0);
	int value;

	Type(int type)
	{
		this.value = type;
	}

	public int getValue(){
		return value;
	}
}
