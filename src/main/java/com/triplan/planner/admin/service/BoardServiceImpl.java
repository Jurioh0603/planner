package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Board;
import com.triplan.planner.admin.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<Board> getPostsByCategory(String category) {
        return boardMapper.getPostsByCategory(category);
    }

    @Override
    public List<Board> getPagedPostsByCategory(String category, String searchType, String searchQuery, int startRow, int size) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery,
                "startRow", startRow,
                "size", size
        );
        return boardMapper.getPagedPostsByCategory(params);
    }

    @Override
    public int getCountByCategory(String category, String searchType, String searchQuery) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery
        );
        return boardMapper.getCountByCategory(params);
    }

    @Override
    public void deletePosts(List<Integer> boardIdxArray, String category) {
        Map<String, Object> params = Map.of(
                "boardIdxArray", boardIdxArray,
                "category", category
        );
        boardMapper.deletePosts(params);
    }

    @Override
    public List<Board> searchPosts(String category, String searchType, String searchQuery) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery
        );
        return boardMapper.searchPosts(params);
    }
}
