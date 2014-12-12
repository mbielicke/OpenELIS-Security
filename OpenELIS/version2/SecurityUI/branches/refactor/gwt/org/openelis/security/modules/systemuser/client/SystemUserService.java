package org.openelis.security.modules.systemuser.client;

import java.util.ArrayList;

import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SystemUserDO;
import org.openelis.security.manager.SystemUserManager;
import org.openelis.ui.common.data.Query;
import org.openelis.ui.screen.Callback;
import org.openelis.ui.services.TokenService;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;

public class SystemUserService implements SystemUserServiceIntAsync, SystemUserServiceInt {

    private SystemUserServiceIntAsync service;

    private static SystemUserService  instance;

    public static SystemUserService get() {
        if (instance == null)
            instance = new SystemUserService();

        return instance;
    }

    private SystemUserService() {
        service = (SystemUserServiceIntAsync)GWT.create(SystemUserServiceInt.class);
        ((HasRpcToken)service).setRpcToken(TokenService.getToken());
    }

    @Override
    public void abortUpdate(Integer id, AsyncCallback<SystemUserManager> callback) {
        service.abortUpdate(id, callback);
    }

    @Override
    public void add(SystemUserManager man, AsyncCallback<SystemUserManager> callback) {
        service.add(man, callback);
    }

    @Override
    public void delete(SystemUserManager man, AsyncCallback<SystemUserManager> callback) {
        service.delete(man, callback);
    }

    @Override
    public void fetchById(Integer id, SystemUserManager.Load[] elements, AsyncCallback<SystemUserManager> callback) {
        service.fetchById(id, elements, callback);
    }

    @Override
    public void fetchForUpdate(Integer id, AsyncCallback<SystemUserManager> callback) {
        service.fetchForUpdate(id, callback);
    }
    
    @Override
    public void fetchTemplateList(AsyncCallback<ArrayList<SystemUserDO>> callback) {
        service.fetchTemplateList(callback);
    }

    @Override
    public void query(Query query, AsyncCallback<ArrayList<IdNameVO>> callback) {
        service.query(query, callback);
    }

    @Override
    public void update(SystemUserManager man, AsyncCallback<SystemUserManager> callback) {
        service.update(man, callback);
    }

    @Override
    public SystemUserManager fetchById(Integer id, SystemUserManager.Load... elements) throws Exception {
        Callback<SystemUserManager> callback;

        callback = new Callback<SystemUserManager>();
        service.fetchById(id, elements, callback);
        return callback.getResult();
    }

    @Override
    public ArrayList<IdNameVO> query(Query query) throws Exception {
        Callback<ArrayList<IdNameVO>> callback;

        callback = new Callback<ArrayList<IdNameVO>>();
        service.query(query, callback);
        return callback.getResult();
    }

    @Override
    public ArrayList<SystemUserDO> fetchTemplateList() throws Exception {
        Callback<ArrayList<SystemUserDO>> callback;

        callback = new Callback<ArrayList<SystemUserDO>>();
        service.fetchTemplateList(callback);
        return callback.getResult();
    }

    @Override
    public SystemUserManager add(SystemUserManager man) throws Exception {
        Callback<SystemUserManager> callback;

        callback = new Callback<SystemUserManager>();
        service.add(man, callback);
        return callback.getResult();
    }

    @Override
    public SystemUserManager update(SystemUserManager man) throws Exception {
        Callback<SystemUserManager> callback;

        callback = new Callback<SystemUserManager>();
        service.update(man, callback);
        return callback.getResult();
    }

    @Override
    public SystemUserManager delete(SystemUserManager man) throws Exception {
        Callback<SystemUserManager> callback;

        callback = new Callback<SystemUserManager>();
        service.delete(man, callback);
        return callback.getResult();
    }

    @Override
    public SystemUserManager fetchForUpdate(Integer id) throws Exception {
        Callback<SystemUserManager> callback;

        callback = new Callback<SystemUserManager>();
        service.fetchForUpdate(id, callback);
        return callback.getResult();
    }

    @Override
    public SystemUserManager abortUpdate(Integer id) throws Exception {
        Callback<SystemUserManager> callback;

        callback = new Callback<SystemUserManager>();
        service.abortUpdate(id, callback);
        return callback.getResult();
    }

}
