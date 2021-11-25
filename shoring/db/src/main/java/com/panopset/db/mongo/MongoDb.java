package com.panopset.db.mongo;

import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.panopset.compat.Stringop;

public class MongoDb {
  
  public static final String TEST = "test";
  
  private final String mdbs;
  private final String user;
  private final String pass;

  public MongoDb(String mdbs, String user, String pass) {
    this.mdbs = mdbs;
    this.user = user;
    this.pass = pass;
  }

  public static void main(String[] args) {
    new MongoDb(args[0], args[1], args[2]).go();
  }

  private void go() {
    String tplt =
        "mongodb+srv://%s:%s@cluster0.mxuky.mongodb.net/%s?retryWrites=true&w=majority";
    String cs = String.format(tplt, user, pass, mdbs);
    System.out.println(cs);
    ConnectionString connectionString = new ConnectionString(cs);
    MongoClientSettings settings =
        MongoClientSettings.builder().applyConnectionString(connectionString).build();
    MongoClient mongoClient = MongoClients.create(settings);
    MongoDatabase database = mongoClient.getDatabase(Stringop.TEST);
//    database.createCollection(Stringop.FOO);
    MongoCollection<Document> collection = database.getCollection(Stringop.FOO);
//    Document doc = new Document(Stringop.BAR, Stringop.BAT);
//    collection.insertOne(doc);
    Document rdoc = collection.find().first();
    System.out.println(rdoc.toJson());
  }

  
  
}
