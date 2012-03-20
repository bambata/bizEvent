package com.opportunity.poimanageservice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.opportunity.poimanageservice.model.dto.FileMeta;
import com.opportunity.poimanageservice.model.dto.FileUrl;

@Controller
@RequestMapping("/fileService")
public class UploadFileService {

  private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  private final BlobInfoFactory blobInfoFactory = new BlobInfoFactory();

  @RequestMapping(value = "/callbackUrl", method = RequestMethod.POST)
  public @ResponseBody
  FileUrl getCallbackUrl(HttpServletRequest req, HttpServletResponse res) {

    String url = blobstoreService.createUploadUrl("/uploadService");
    return new FileUrl(url);

  }

  @RequestMapping(value = "/persistFile", method = RequestMethod.POST)
  public void post(HttpServletRequest req, HttpServletResponse res) throws IOException {
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
    List<BlobKey> blobKey = blobs.get("files[]");

    StringBuilder keys = new StringBuilder();
    for (int i = 0; i < blobKey.size(); i++) {
      keys.append(blobKey.get(i).getKeyString());

      if (i < keys.length() - 1)
        keys.append(",");
    }

    res.sendRedirect("/meta/" + keys.toString());
  }

  @RequestMapping(value = "/meta/{keys:\\w+(?:,\\w*)*}", method = RequestMethod.GET)
  public @ResponseBody
  List<FileMeta> getBlobMetadata(@PathVariable String keys) {

    String[] keysArray = keys.split(",");

    List<FileMeta> toReturn = new ArrayList<FileMeta>();

    for (String key : keysArray) {
      BlobKey blobKey = new BlobKey(key);

      BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);

      FileMeta meta = new FileMeta(info.getFilename(), info.getSize(), key);

      toReturn.add(meta);
    }

    return toReturn;

  }

  @RequestMapping(value = "/file/download/{key:\\w+}", method = RequestMethod.GET)
  public void serveForDownload(@PathVariable("key") String key, HttpServletResponse response) throws IOException {

    BlobKey blobKey = new BlobKey(key);
    final BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
    response.setHeader("Content-Disposition", "attachment; filename=" + blobInfo.getFilename());
    BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);

  }

  @RequestMapping(value = "/file//{key:\\w+}", method = RequestMethod.GET)
  public void serveForDisplay(@PathVariable("key") String key, HttpServletResponse response) throws IOException {

    BlobKey blobKey = new BlobKey(key);
    BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);

  }

}
