package com.example;

import de.greenrobot.daogenerator.*;

public class Generator {

	public static void main(String[] args) throws Exception{
		Schema schema = new Schema(1, "ch.appropppo.greendao.srcgen");

		Entity person = schema.addEntity("Person");
		person.addIdProperty();
		person.addStringProperty("firstname");
		person.addStringProperty("name");
		person.addDateProperty("timestamp");

		Entity address = schema.addEntity("Address");
		address.addIdProperty();
		address.addStringProperty("street");
		address.addIntProperty("streetNumber");
		address.addStringProperty("zip");
		Property city = address.addStringProperty("city").getProperty();

		// add relationships
		Property personId = address.addLongProperty("personId").notNull().getProperty();
		address.addToOne(person, personId);

		ToMany addresses = person.addToMany(address, personId);
		addresses.setName("addresses");
		addresses.orderAsc(city);
		new DaoGenerator().generateAll(schema, "src-gen/");
	}

}
