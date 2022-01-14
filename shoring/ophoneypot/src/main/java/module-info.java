module com.panopset.ophoneypot {
	requires com.panopset.compat;
	// TODO: Above shouldn't be necessary, since opmysql depends on it and it's transitive, Eclipse bug?
	requires transitive com.panopset.opmysql;
}
