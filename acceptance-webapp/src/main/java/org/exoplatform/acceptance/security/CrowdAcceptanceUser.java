package org.exoplatform.acceptance.security;

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import lombok.Delegate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrowdAcceptanceUser implements AcceptanceUser {

  @NonNull
  @Delegate
  private CrowdUserDetails crowdUserDetails;

}
