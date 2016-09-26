package ar.com.zumma.platform.services.currentUser;

import ar.com.zumma.platform.domain.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
