package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Project;
import com.cyber.authing.mapper.ProjectMapper;
import com.cyber.authing.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger( ProjectServiceImpl.class );

    @Autowired
    ProjectMapper projectMapper;
    
    
    @Override
    @Transactional(readOnly = false)
    public Integer save(Project project) {

        if( null == project) {
            LOGGER.warn("save project, but project is null...");
            return 0;
        }

        return projectMapper.insert(project);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Project project) {

        if( null == project) {
            LOGGER.warn("delete project, but project is null  or project id is null...");
            return 0;
        }

        return projectMapper.deleteById(project);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Project project) {

        if( null == project) {
            LOGGER.warn("update project, but project is null  or project id is null...");
            return 0;
        }

        return projectMapper.updateById(project);
    }

    @Override
    public Project selectOne(Project project) {
        if( project == null) {
            LOGGER.warn("select project one, but project is null ...");
            return null;
        }
        project = projectMapper.selectOne( project );
        return project;
    }


//    @Override
//    public PagingData<Project> selectPage(Project project) {
//        PagingData<Project> pagingData = new PagingData<Project>();
//
//        if( null == project ) {
//            LOGGER.warn("select project page, but project is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = projectMapper.selectByIndexCount( project );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select project page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<Project> projects =  selectByIndex( project );
//        pagingData.setRows(projects);
//        return pagingData;
//    }
//
//    @Override
//    public List<Project> selectByIndex(Project project) {
//        List<Project> projects = new ArrayList<Project>();
//        if( project == null) {
//            LOGGER.warn("select project by index, but project is null ...");
//            return projects;
//        }
//
//        projects = projectMapper.selectByIndex( project );
//
//        return projects;
//    }
}