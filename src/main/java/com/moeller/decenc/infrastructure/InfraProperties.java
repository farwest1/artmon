package com.moeller.decenc.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Bernd on 03.01.2018.
 *
 * Package com.moeller.decenc.infrastructure
 */
@Component
@ConfigurationProperties(prefix = "infra")
public class InfraProperties {

  private String destination;

  private String connectionUri;

  private String user;

  private String password;

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getConnectionUri() {
    return connectionUri;
  }

  public void setConnectionUri(String connectionUri) {
    this.connectionUri = connectionUri;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
