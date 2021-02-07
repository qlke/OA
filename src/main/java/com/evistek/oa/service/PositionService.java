package com.evistek.oa.service;

import com.evistek.oa.entity.Position;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/6
 */
public interface PositionService {
    List<Position> findPosition(String name, int page, int pageSize);

    Integer findPositionTotal(String name);

    Position findPositionById(String id);

    ResponseCode addPosition(Position position, String authorityId, String organizationId, String departmentId);

    ResponseCode deletePosition(String id);

    ResponseCode updatePosition(Position position, String authorityId, String organizationId, String departmentId);
}
