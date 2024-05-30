package com.triplan.planner.tlog.repository;

import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TlogMapper {

    public long saveTlog(Tlog tlog);
    public void saveTlogImage(List<TlogImage> tlogImageList);

    public List<TlogList> getTlogs();

    public Tlog getTlogByNo(long tlogNo);
    public List<TlogImage> getTlogImageByNo(long tlogNo);
}