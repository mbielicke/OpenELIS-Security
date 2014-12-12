/**
 * Exhibit A - UIRF Open-source Based Public Software License.
 * 
 * The contents of this file are subject to the UIRF Open-source Based Public
 * Software License(the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * openelis.uhl.uiowa.edu
 * 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * 
 * The Original Code is OpenELIS code.
 * 
 * The Initial Developer of the Original Code is The University of Iowa.
 * Portions created by The University of Iowa are Copyright 2006-2008. All
 * Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Alternatively, the contents of this file marked "Separately-Licensed" may be
 * used under the terms of a UIRF Software license ("UIRF Software License"), in
 * which case the provisions of a UIRF Software License are applicable instead
 * of those above.
 */
package org.openelis.security.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openelis.ui.util.TestingUtil.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openelis.security.domain.SectionViewDO;
import org.openelis.security.entity.Section;
import org.openelis.ui.common.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestSectionBean {
    
    SectionBean bean;
    
    final String FETCH_BY_APP_ID = "Section.FetchByApplicationId";
    
    @Before
    public void preArrange() {
        bean = new SectionBean();
        
        bean.manager = mock(EntityManager.class);
        bean.systemUserSection = mock(SystemUserSectionBean.class);
        bean.userCache = mock(UserCacheBean.class);
    }
    
    @Test
    public void fetchByApplicationId() {
        ArrayList<SectionViewDO> list = new ArrayList<SectionViewDO>();
        list.add(mock(SectionViewDO.class));
        mockNamedQueryWithResultList(bean.manager,FETCH_BY_APP_ID, list);
        
        try {
            assertEquals(list,bean.fetchByApplicationId(new Integer(1)));
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test(expected=NotFoundException.class)
    public void fetchByApplicationId_throwsNotFound() throws Exception {
        mockNamedQueryWithResultList(bean.manager,FETCH_BY_APP_ID, new ArrayList<SectionViewDO>());
        
        bean.fetchByApplicationId(new Integer(1));
    }
    
    @Test
    public void add() throws Exception {
        bean.add(mock(SectionViewDO.class));
    }
    
    @Test
    public void update() throws Exception {
        SectionViewDO data = mock(SectionViewDO.class);
        when(data.isChanged()).thenReturn(true);
        when(data.getId()).thenReturn(new Integer(1));
        when(bean.manager.find(Section.class, new Integer(1))).thenReturn(new Section());
        bean.update(data);
        verify(bean.manager).find(Section.class, new Integer(1));
    }
    
    @Test
    public void update_noChange() throws Exception {
        SectionViewDO data = mock(SectionViewDO.class);
        when(data.isChanged()).thenReturn(false);
        when(data.getId()).thenReturn(new Integer(1));
        bean.update(data);
        verify(bean.manager,never()).find(Section.class, new Integer(1));
    }
    
    @Test
    public void delete() throws Exception {
        
    }
}
