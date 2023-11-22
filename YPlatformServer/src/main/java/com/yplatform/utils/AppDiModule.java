package com.yplatform.utils;

import com.google.inject.AbstractModule;
import com.yplatform.database.dao.implementations.UserDAOImpl;
import com.yplatform.database.dao.interfaces.UserDAO;

public class AppDiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDAO.class).to(UserDAOImpl.class);
    }
}
