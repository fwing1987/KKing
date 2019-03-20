package com.kking.dao.mapper;

import com.kking.dao.entity.TSysDept;
import java.util.List;

public interface TSysDeptMapper {
    public TSysDept selectById(TSysDept tSysDept);
    public List<TSysDept> selectList(TSysDept tSysDept);
    public TSysDept selectOneByProperty(String key, Object value);
    public List<TSysDept> selectListByProperty(String key, Object value);
    public int insert(TSysDept tSysDept);
    public int deleteById(TSysDept tSysDept);
    public int update(TSysDept tSysDept);

    List<TSysDept> getDeptWithRoleStatus(TSysDept dept);
    List<TSysDept> getChildrenDept(TSysDept dept);

    public int updateDeptListPids(List<TSysDept> childDeptList);
}
