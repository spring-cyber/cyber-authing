package com.cyber.authing.mapper;

import com.cyber.authing.entity.domain.ProjectUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectUserMapper extends BaseMapper<ProjectUser> {
    Integer batchSaveAndUpdate(List<ProjectUser> projectUserList);

    Integer deleteByIds(List<Long> ids);
}