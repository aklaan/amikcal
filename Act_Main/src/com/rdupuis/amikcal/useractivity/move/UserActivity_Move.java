package com.rdupuis.amikcal.useractivity.move;

import java.util.ArrayList;
import java.util.Calendar;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Food;
import com.rdupuis.amikcal.components.Component_Move;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UA_CLASS_CD;
import com.rdupuis.amikcal.useractivity.UserActivity_Commons;

public class UserActivity_Move extends UserActivity_Commons {

    private ArrayList<Component_Move> mComponentsList;

    public UserActivity_Move() {
	super();
	this.setComponentsList(new ArrayList<Component_Move>());
    }

    public UserActivity_Move(Calendar day) {
	super();
	this.setComponentsList(new ArrayList<Component_Move>());
    }

    @Override
    public UA_CLASS_CD getActivityType() {
	return UA_CLASS_CD.MOVE;
    }

    @Override
    public void setComponentsList(ArrayList<? extends Component> arrayList) {
	this.mComponentsList = (ArrayList<Component_Move>) arrayList;
    }
}
