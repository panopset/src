module com.panopset.opmysql {
	requires transitive com.panopset.flywheel;
	requires java.sql;
	requires mysql.connector.java;

	exports com.panopset.opmysql;
}
