package com.opportunity.webservices.model.dto;

public class FileMeta {

  String name;

  long size;

  String key;

  String delete_url;

  String delete_type;

  public FileMeta(String filename, long size, String url) {
    this.name = filename;
    this.size = size;
    this.key = url;
    this.delete_url = url;
    this.delete_type = "DELETE";
  }

  public FileMeta() {
  }

}