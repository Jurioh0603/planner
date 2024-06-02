package com.triplan.planner.mypage.repository;

import com.triplan.planner.mypage.dto.MyTlogList;
import com.triplan.planner.mypage.dto.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapper {
    public void updateProfile(Profile profile);
    public Profile getProfileList(String memberId);
    public void updateInfo(Profile profile);
    public List<MyTlogList> myFavList(@Param("memberId") String memberId,
                                      @Param("startRow") int startRow,
                                      @Param("size") int size);

    public int getCount();

    public int favCount(String memberId);

    public List<MyTlogList> myTlogList(@Param("memberId") String memberId,
                                       @Param("startRow") int startRow,
                                       @Param("size") int size);
}
