package org.openelis.security.modules.application.client;

import java.util.ArrayList;

import org.openelis.security.domain.ApplicationDO;
import org.openelis.security.domain.IdNameVO;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.domain.SystemModuleViewDO;
import org.openelis.security.manager.ApplicationManager;
import org.openelis.ui.common.data.Query;
import org.openelis.ui.screen.Callback;
import org.openelis.ui.services.TokenService;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;

public class ApplicationService implements ApplicationServiceIntAsync, ApplicationServiceInt {

    private ApplicationServiceIntAsync service;

    private static ApplicationService  instance;
    
    public static ApplicationService get() {
        if (instance == null)
            instance = new ApplicationService();

        return instance;
    }

    private ApplicationService() {
        service = (ApplicationServiceIntAsync)GWT.create(ApplicationServiceInt.class);
        ((HasRpcToken)service).setRpcToken(TokenService.getToken());
        
    }

    @Override
    public void abortUpdate(Integer id, AsyncCallback<ApplicationManager> callback) {
        service.abortUpdate(id, callback);
    }

    @Override
    public void add(ApplicationManager man, AsyncCallback<ApplicationManager> callback) {
        service.add(man, callback);
    }

    @Override
    public void fetchById(Integer id, ApplicationManager.Load[] elements, AsyncCallback<ApplicationManager> callback) {
        service.fetchById(id, elements, callback);
    }

    @Override
    public void fetchForUpdate(Integer id, AsyncCallback<ApplicationManager> callback) {
        service.fetchForUpdate(id, callback);
    }

    @Override
    public void fetchList(AsyncCallback<ArrayList<ApplicationDO>> callback) {
        service.fetchList(callback);
    }

    @Override
    public void query(Query query, AsyncCallback<ArrayList<IdNameVO>> callback) {
        service.query(query, callback);
    }

    @Override
    public void update(ApplicationManager man, AsyncCallback<ApplicationManager> callback) {
        service.update(man, callback);
    }

    @Override
    public ApplicationManager fetchById(Integer id, ApplicationManager.Load... elements) throws Exception {
        Callback<ApplicationManager> callback;

        callback = new Callback<ApplicationManager>();
        service.fetchById(id, elements, callback);
        return callback.getResult();
    }


    @Override
    public ArrayList<ApplicationDO> fetchList() throws Exception {
        Callback<ArrayList<ApplicationDO>> callback;

        callback = new Callback<ArrayList<ApplicationDO>>();
        service.fetchList(callback);
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
    public ApplicationManager add(ApplicationManager man) throws Exception {
        Callback<ApplicationManager> callback;

        callback = new Callback<ApplicationManager>();
        service.add(man, callback);
        return callback.getResult();
    }

    @Override
    public ApplicationManager update(ApplicationManager man) throws Exception {
        Callback<ApplicationManager> callback;

        callback = new Callback<ApplicationManager>();
        service.update(man, callback);
        return callback.getResult();
    }

    @Override
    public ApplicationManager fetchForUpdate(Integer id) throws Exception {
        Callback<ApplicationManager> callback;

        callback = new Callback<ApplicationManager>();
        service.fetchForUpdate(id, callback);
        return callback.getResult();
    }

    @Override
    public ApplicationManager abortUpdate(Integer id) throws Exception {
        Callback<ApplicationManager> callback;

        callback = new Callback<ApplicationManager>();
        service.abortUpdate(id, callback);
        return callback.getResult();
    }

    @Override
    public ArrayList<SystemModuleViewDO> fetchModulesByAppId(Integer id) throws Exception {
        Callback<ArrayList<SystemModuleViewDO>> callback;
        
        callback = new Callback<ArrayList<SystemModuleViewDO>>();
        service.fetchModulesByAppId(id, callback);
        return callback.getResult();
    }

    @Override
    public ArrayList<SectionViewDO> fetchSectionsByAppId(Integer id) throws Exception {
        Callback<ArrayList<SectionViewDO>> callback;
        
        callback = new Callback<ArrayList<SectionViewDO>>();
        service.fetchSectionsByAppId(id, callback);
        return callback.getResult();
    }

    @Override
    public void fetchModulesByAppId(Integer id, AsyncCallback<ArrayList<SystemModuleViewDO>> callback) {
        service.fetchModulesByAppId(id, callback);
    }

    @Override
    public void fetchSectionsByAppId(Integer id, AsyncCallback<ArrayList<SectionViewDO>> callback) {
        service.fetchSectionsByAppId(id, callback);
    }

}
