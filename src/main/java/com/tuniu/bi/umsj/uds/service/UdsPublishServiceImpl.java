package com.tuniu.bi.umsj.uds.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuniu.bi.umsj.base.constant.UdsConst;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.vo.*;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishEntity;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishItemEntity;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishItemParamEntity;
import com.tuniu.bi.umsj.uds.dao.entity.UdsPublishParamEntity;
import com.tuniu.bi.umsj.uds.dao.mapper.UdsPublishItemMapper;
import com.tuniu.bi.umsj.uds.dao.mapper.UdsPublishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwei21
 */
@Service
public class UdsPublishServiceImpl implements UdsPublishService {

    @Autowired
    private UdsPublishMapper udsPublishMapper;

    @Autowired
    private UdsPublishItemMapper udsPublishItemMapper;

    @Override
    public UdsPublishListResponseVO findMany(UdsPublishListRequestVO udsPublishQueryVO) {
        UdsPublishListResponseVO udsPublishListResponseVO = new UdsPublishListResponseVO();
        PageHelper.startPage(udsPublishQueryVO.getPageNum(), udsPublishQueryVO.getPageSize());
        PageHelper.orderBy("id");
        UdsPublishParamEntity udsPublishParamEntity = new UdsPublishParamEntity();
        udsPublishParamEntity.setTitle(udsPublishQueryVO.getTitle());
        udsPublishParamEntity.setApplyUser(udsPublishQueryVO.getApplyUser());
        udsPublishParamEntity.setPublishUser(udsPublishQueryVO.getPublishUser());
        udsPublishParamEntity.setJiraId(udsPublishQueryVO.getJiraId());
        udsPublishParamEntity.setStatus(udsPublishQueryVO.getStatus());
        List<UdsPublishEntity> many = udsPublishMapper.findMany(udsPublishParamEntity);
        PageInfo pageInfo = new PageInfo(many, udsPublishQueryVO.getPageSize());
        udsPublishListResponseVO.injectPageInfo(pageInfo);
        List<UdsPublishVO> list = new ArrayList<>();
        for (UdsPublishEntity udsPublishEntity : many) {
            UdsPublishVO item = new UdsPublishVO();
            BeanUtils.copyProperties(udsPublishEntity, item);
            list.add(item);
        }
        udsPublishListResponseVO.setUdsPublishList(list);
        return udsPublishListResponseVO;
    }

    @Override
    public void createPublish(CreateUdsRequestVO requestVO) {
        UdsPublishEntity udsPublishEntity = new UdsPublishEntity();
        BeanUtils.copyProperties(requestVO, udsPublishEntity);
        udsPublishEntity.setStatus(UdsConst.UDS_PUBLISH_CREATE);
        List<CreateUdsPublishItemVO> udsPublishItemList = requestVO.getUdsPublishItemList();
        int result = udsPublishMapper.insert(udsPublishEntity);
        // 外部发布单插入成功，插入子的item项目
        if (result > 0) {
            for (CreateUdsPublishItemVO udsPublishItemVO : udsPublishItemList) {
                UdsPublishItemEntity udsPublishItemEntity = new UdsPublishItemEntity();
                BeanUtils.copyProperties(udsPublishItemVO, udsPublishItemEntity);
                udsPublishItemEntity.setPublishId(udsPublishEntity.getId());
                udsPublishItemEntity.setState(UdsConst.UDS_PUBLISH_ITEM_CREATE);
                udsPublishItemMapper.insert(udsPublishItemEntity);
            }
        }
    }

    @Override
    public void updatePublish(UpdateUdsRequestVO updateUdsRequestVO, String username) throws AbstractException {
        // 查询发布单的信息
        UdsPublishEntity oldUdsPublishEntity = udsPublishMapper.findByPk(updateUdsRequestVO.getId());
        if (oldUdsPublishEntity == null) {
            throw new CommonException("根据id[" + updateUdsRequestVO.getId() + "]查询不到发布单信息");
        }
        if (!username.equals(oldUdsPublishEntity.getPublishUser())) {
            throw new CommonException("对不起！您只能修改自己创建的发布单！");
        }
        UdsPublishEntity udsPublishEntity = new UdsPublishEntity();
        // 忽略字段有
        BeanUtils.copyProperties(updateUdsRequestVO, udsPublishEntity);
        int result = udsPublishMapper.update(udsPublishEntity);
        if (result > 0) {
            // 查询已有的发布项
            UdsPublishItemParamEntity udsPublishItemParamEntity = new UdsPublishItemParamEntity();
            udsPublishItemParamEntity.setPublishId(udsPublishEntity.getId());
            List<UdsPublishItemEntity> many = udsPublishItemMapper.findMany(udsPublishItemParamEntity);
            List<UdsPublishItemVO> udsPublishItemList = updateUdsRequestVO.getUdsPublishItemList();
            for (UdsPublishItemVO tmp : udsPublishItemList) {
                UdsPublishItemEntity udsPublishItemEntity = new UdsPublishItemEntity();
                BeanUtils.copyProperties(tmp, udsPublishItemEntity);
                if (udsPublishItemEntity.getId() == null) {
                    // 新增
                    udsPublishItemEntity.setState(UdsConst.UDS_PUBLISH_ITEM_CREATE);
                    udsPublishItemEntity.setPublishId(oldUdsPublishEntity.getId());
                    udsPublishItemMapper.insert(udsPublishItemEntity);
                } else {
                    // 更新
                    udsPublishItemMapper.update(udsPublishItemEntity);
                }
            }
            // 删除
            for (UdsPublishItemEntity tmp : many) {
                boolean delFlag = true;
                for (UdsPublishItemVO tmp1 : udsPublishItemList) {
                    if (tmp1.getId() != null && tmp1.getId().equals(tmp.getId())) {
                        delFlag = false;
                        break;
                    }
                }
                if (delFlag) {
                    // 删除
                    udsPublishItemMapper.delete(tmp.getId());
                }
            }
        }
    }

    @Override
    public void updatePublishStatus(UdsPublishVO udsPublishVO) {
        UdsPublishEntity udsPublishEntity = new UdsPublishEntity();
        udsPublishEntity.setStatus(udsPublishVO.getStatus());
        udsPublishEntity.setId(udsPublishVO.getId());
        udsPublishMapper.update(udsPublishEntity);
    }
}
