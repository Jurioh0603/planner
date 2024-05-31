package com.triplan.planner.tlog.repository;

import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TlogMapper {

    public long saveTlog(Tlog tlog);
    public void saveTlogImage(List<TlogImage> tlogImageList);

    public List<TlogList> getTlogs(int startRow, int size);
    public int getCount();

    public Tlog getTlogByNo(long tlogNo);
    public List<TlogImage> getTlogImageByNo(long tlogNo);

    public void deleteTlogByNo(long tlogNo);

    public void updateTlog(Tlog tlog);
    public void deleteTlogImageByNo(long tlogNo);

    public List<Long> getFavList(String memberId);
    public void saveFav(@Param("tlogNo") long tlogNo, @Param("memberId") String memberId);
    public void deleteFav(@Param("tlogNo") long tlogNo, @Param("memberId") String memberId);
}
