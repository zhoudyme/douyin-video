package me.zhoudongyu.mapper;

import me.zhoudongyu.pojo.SearchRecords;
import me.zhoudongyu.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

    List<String> getHotWords();
}