package com.tuniu.bi.umsj.uds.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuniu.bi.umsj.base.constant.UdsConst;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.vo.*;
import com.tuniu.bi.umsj.uds.dao.entity.*;
import com.tuniu.bi.umsj.uds.dao.mapper.UdsPublishItemMapper;
import com.tuniu.bi.umsj.uds.dao.mapper.UdsPublishLogMapper;
import com.tuniu.bi.umsj.uds.dao.mapper.UdsPublishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UdsPublishLogMapper udsPublishLogMapper;

    private static final String APPLY_USER = "zhangjingchao";

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
        UdsPublishItemParamEntity udsPublishItemParamEntity = new UdsPublishItemParamEntity();
        for (UdsPublishEntity udsPublishEntity : many) {
            UdsPublishVO item = new UdsPublishVO();
            BeanUtils.copyProperties(udsPublishEntity, item);
            // 查询udspublishItem
            udsPublishItemParamEntity.setPublishId(udsPublishEntity.getId());
            List<UdsPublishItemEntity> items = udsPublishItemMapper.findMany(udsPublishItemParamEntity);
            List<UdsPublishItemVO> voList = new ArrayList<>();
            for (UdsPublishItemEntity udsPublishItemEntity: items) {
                UdsPublishItemVO udsPublishItemVO = new UdsPublishItemVO();
                BeanUtils.copyProperties(udsPublishItemEntity, udsPublishItemVO);
                voList.add(udsPublishItemVO);
            }
            item.setUdsPublishItemList(voList);
            list.add(item);
        }
        udsPublishListResponseVO.setUdsPublishList(list);
        return udsPublishListResponseVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
    public void createPublish(CreateUdsRequestVO requestVO, String username) throws AbstractException {
        UdsPublishEntity udsPublishEntity = new UdsPublishEntity();
        BeanUtils.copyProperties(requestVO, udsPublishEntity);
        udsPublishEntity.setPublishUser(username);
        udsPublishEntity.setStatus(UdsConst.UDS_PUBLISH_CREATE);
        udsPublishEntity.setApplyUser(APPLY_USER);
        int result = udsPublishMapper.insert(udsPublishEntity);
        if (result > 0) {
            for (CreateUdsPublishItemVO createUdsPublishItemVO : requestVO.getUdsPublishItemList()) {
                UdsPublishItemEntity udsPublishItemEntity = new UdsPublishItemEntity();
                BeanUtils.copyProperties(createUdsPublishItemVO, udsPublishItemEntity);
                udsPublishItemEntity.setPublishId(udsPublishEntity.getId());
                udsPublishItemEntity.setState(UdsConst.UDS_PUBLISH_ITEM_CREATE);
                udsPublishItemMapper.insert(udsPublishItemEntity);
            }
        }
        // 记录发布单表创建日志
        UdsPublishLogEntity udsPublishLogEntity = new UdsPublishLogEntity();
        udsPublishLogEntity.setPublishId(udsPublishEntity.getId());
        udsPublishLogEntity.setContent(username + "创建了发布单");
        udsPublishLogEntity.setData(JSONObject.toJSONString(requestVO));
        udsPublishLogMapper.insert(udsPublishLogEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
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
    @Transactional(rollbackFor = Exception.class, transactionManager = "umsTransactionManager")
    public void updatePublishStatus(UpdateUdsStatusRequestVO updateUdsStatusRequestVO, String username) throws AbstractException {
        if (UdsConst.UDS_PUBLISH_WAIT_APPROVE == updateUdsStatusRequestVO.getStatus()) {
            // 申请审核，校验当前的发布人员是否和username一致
            checkUpdatePublish(updateUdsStatusRequestVO, username);
        }
        // 更新发布单的状态
        UdsPublishEntity udsPublishEntity = new UdsPublishEntity();
        udsPublishEntity.setStatus(updateUdsStatusRequestVO.getStatus());
        udsPublishEntity.setId(updateUdsStatusRequestVO.getId());
        udsPublishMapper.update(udsPublishEntity);
        // 记录发布日志
        UdsPublishLogEntity udsPublishLogEntity = new UdsPublishLogEntity();
        udsPublishLogEntity.setPublishId(updateUdsStatusRequestVO.getId());
        udsPublishLogEntity.setPublishId(udsPublishEntity.getId());
        udsPublishLogEntity.setContent(username + "创建了发布单");
        udsPublishLogEntity.setData(JSONObject.toJSONString(updateUdsStatusRequestVO));
        udsPublishLogMapper.insert(udsPublishLogEntity);
    }

    private void checkUpdatePublish(UpdateUdsStatusRequestVO updateUdsStatusRequestVO, String username) throws AbstractException {
        // 查询发布单是否存在
        UdsPublishEntity byPk = udsPublishMapper.findByPk(updateUdsStatusRequestVO.getId());
        if (byPk == null) {
            throw new CommonException("当前发布单不存在");
        }
        // 提交审核
        if (UdsConst.UDS_PUBLISH_WAIT_APPROVE == updateUdsStatusRequestVO.getStatus()) {
            // 只有新建状态和审核不通过才能发起审核
            if (!(UdsConst.UDS_PUBLISH_CREATE == byPk.getStatus() || UdsConst.UDS_PUBLISH_APPROVE_FAILED == byPk.getStatus())) {
                throw new CommonException("当前状态不允许更新发布单");
            }
            if (username.equals(byPk.getPublishUser())) {
                throw new CommonException("当前发布单的发布人与操作人不一致");
            }
        } else if (UdsConst.UDS_PUBLISH_APPROVE_SUCCESS == updateUdsStatusRequestVO.getStatus() ||
                UdsConst.UDS_PUBLISH_APPROVE_FAILED == updateUdsStatusRequestVO.getStatus()) {
            // 只有待审核状态才能审核
            if (!(UdsConst.UDS_PUBLISH_WAIT_APPROVE == byPk.getStatus())) {
                throw new CommonException("当前状态不允许更新发布单");
            }
            if (!username.equals(byPk.getApplyUser())) {
                throw new CommonException("当前发布单的审核人与操作人不一致");
            }
        }
    }

    @Override
    public UdsPublishVO publishDetail(Integer id) throws AbstractException {
        UdsPublishEntity byPk = udsPublishMapper.findByPk(id);
        if (byPk == null) {
            throw new CommonException("根据ID查询不到对应的发布");
        }
        UdsPublishVO udsPublishVO = new UdsPublishVO();
        BeanUtils.copyProperties(byPk, udsPublishVO);
        // 根据publishId查询item
        UdsPublishItemParamEntity udsPublishItemParamEntity = new UdsPublishItemParamEntity();
        udsPublishItemParamEntity.setPublishId(id);
        List<UdsPublishItemEntity> many = udsPublishItemMapper.findMany(udsPublishItemParamEntity);
        List<UdsPublishItemVO> list = new ArrayList<>();
        for (UdsPublishItemEntity udsPublishItemEntity : many) {
            UdsPublishItemVO udsPublishItemVO = new UdsPublishItemVO();
            BeanUtils.copyProperties(udsPublishItemEntity, udsPublishItemVO);
            list.add(udsPublishItemVO);
        }
        udsPublishVO.setUdsPublishItemList(list);
        return udsPublishVO;
    }
}
