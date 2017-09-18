package com.ey.accumulo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.hadoop.io.Text;

public class AccumuloRead {

 public static void main(String[] args) throws AccumuloException, AccumuloSecurityException, TableNotFoundException, TableExistsException {
  String instanceName = "accumulo";
  String zooServers = "localhost:2181";

  ZooKeeperInstance instance = new ZooKeeperInstance(instanceName, zooServers);
  Connector connector = instance.getConnector("eyuser", "eyuser");

  Scanner scan = connector.createScanner("usertable1", new Authorizations("public"));
  scan.setRange(new Range("rowId", "rowId"));

  Iterator<Map.Entry<Key,Value>> iterator = scan.iterator();
  while (iterator.hasNext()) {
   Map.Entry<Key,Value> entry = iterator.next();
   Key key = entry.getKey();
   Value value = entry.getValue();
   System.out.println(key + " ==> " + value);
  }
 }
}