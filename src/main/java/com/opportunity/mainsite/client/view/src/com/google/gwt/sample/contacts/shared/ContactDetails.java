package com.google.gwt.sample.contacts.shared;

import java.io.Serializable;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ContactDetails implements Serializable {
  private String id;
  private String displayName;
  
  public ContactDetails() {
    new ContactDetails("0", "");
  }

  public ContactDetails(String id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }
  
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getDisplayName() { return displayName; }
  public void setDisplayName(String displayName) { this.displayName = displayName; } 
}
