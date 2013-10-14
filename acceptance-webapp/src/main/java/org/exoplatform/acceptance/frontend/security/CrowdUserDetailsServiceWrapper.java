package org.exoplatform.acceptance.frontend.security;

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetailsServiceImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This Spring {@link UserDetailsService} is used to wrap the user created by the {@link CrowdUserDetailsServiceImpl} to replace its crowd groups by our application roles
 *
 * @see CrowdGrantedAuthoritiesMapper
 */
public class CrowdUserDetailsServiceWrapper implements UserDetailsService {

  private CrowdUserDetailsServiceImpl crowdUserDetailsServiceImpl;

  private CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper;

  public CrowdUserDetailsServiceWrapper(CrowdUserDetailsServiceImpl crowdUserDetailsServiceImpl, CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper) {
    this.crowdUserDetailsServiceImpl = crowdUserDetailsServiceImpl;
    this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
  }

  /**
   * Locates the user based on the username. In the actual implementation, the search may possibly be case
   * insensitive, or case insensitive depending on how the implementation instance is configured. In this case, the
   * <code>UserDetails</code> object that comes back may have a username that is of a different case than what was
   * actually requested..
   *
   * @param username the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
   */
  @Override
  public ICrowdUserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    return new CrowdUserDetailsWrapper(crowdUserDetailsServiceImpl.loadUserByUsername(username), grantedAuthoritiesMapper);
  }

}
