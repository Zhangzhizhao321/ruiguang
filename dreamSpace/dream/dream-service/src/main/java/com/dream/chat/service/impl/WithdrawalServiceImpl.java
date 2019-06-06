package com.dream.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.chat.common.utils.FuiouUtil;
import com.dream.chat.constant.OrderStatusEnum;
import com.dream.chat.constant.WithdrawTypeEnum;
import com.dream.chat.entity.UserBank;
import com.dream.chat.entity.UserWallet;
import com.dream.chat.entity.Withdrawal;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.WithdrawalMapper;
import com.dream.chat.service.UserBankService;
import com.dream.chat.service.UserWalletService;
import com.dream.chat.service.WithdrawalService;
import com.dream.chat.vo.req.BsOrderReqVo;
import com.dream.chat.vo.req.WithdrawlReqVo;
import com.dream.chat.vo.res.BsWithdrawResVo;
import com.dream.chat.vo.res.WalletResVo;
import com.dream.chat.vo.res.WithdrawResVo;
import com.dream.common.core.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-10
 */
@Service
public class WithdrawalServiceImpl extends SuperServiceImpl<WithdrawalMapper, Withdrawal> implements WithdrawalService {

    @Autowired
    private UserBankService userBankService;

    @Autowired
    private UserWalletService userWalletService;

    @Resource
    private WithdrawalMapper withdrawalMapper;

    @Override
    public Withdrawal createWithdrawalOrder(WithdrawlReqVo withdrawlReqVo) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(withdrawlReqVo.getAmount());
        withdrawal.setBn(Withdrawal.generateBn());
        withdrawal.setCreateTime(new Date());
        withdrawal.setStatus(OrderStatusEnum.PENDING_PAY.code);
        withdrawal.setType(withdrawlReqVo.getType());
        withdrawal.setUpdateTime(new Date());
        withdrawal.setBankNum(withdrawlReqVo.getBankNum());
        withdrawal.setUserId(withdrawlReqVo.getUserId());
        BigDecimal fee = withdrawlReqVo.getAmount().multiply(BigDecimal.valueOf(0.03));
        withdrawal.setArrivalAccount(withdrawlReqVo.getAmount().subtract(fee));
        this.save(withdrawal);
        return withdrawal;
    }

    @Override
    public boolean withdrawal(WithdrawlReqVo vo) {
        UserWallet userWallet = userWalletService.getMyWallet(vo.getUserId());
        if(vo.getAmount().compareTo(userWallet.getWithdrawal()) > 0){
            return false;
        }
        if(vo.getType() == 1){
            UserBank userBank = userBankService.getById(vo.getUserBankId());
            if(userBank != null){
                vo.setBankNo(userBank.getBankNo());
                vo.setBankNum(userBank.getBankNum());
                vo.setUserName(userBank.getUserName());
                Withdrawal withdrawal = this.createWithdrawalOrder(vo);
                vo.setBn(withdrawal.getBn());
                boolean flag = FuiouUtil.addTk(vo);
                if(!flag){
                    withdrawal.setStatus(OrderStatusEnum.PAYED_FAIL.code);
                    withdrawal.setUpdateTime(new Date());
                    this.updateById(withdrawal);
                }
                return flag;
            }
        }
        return false;
    }

    @Override
    public Map getBsWithdrawList(BsOrderReqVo vo) {
        List<BsWithdrawResVo> bsWithdrawList = withdrawalMapper.getBsWithdrawList(vo);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(BsWithdrawResVo bs : bsWithdrawList){
            bs.setStatus(OrderStatusEnum.of(Integer.parseInt(bs.getStatus())).alias);
            bs.setType(WithdrawTypeEnum.of(Integer.parseInt(bs.getType())).alias);
            totalAmount = totalAmount.add(bs.getAmount() == null ? BigDecimal.ZERO : bs.getAmount());
        }
        Map<String, Object> map = PageUtil.ListByPage(vo.getCurrent().intValue(), vo.getSize().intValue(), bsWithdrawList);
        map.put("totalAmount",totalAmount);
        return map;
    }

    @Override
    public List<WithdrawResVo> getWithdrawInount(String userId, Long current, Long size) {
        IPage<Withdrawal> iPage = new Page<>();
        iPage.setCurrent(current==null?1:current);
        iPage.setSize(size=size==null?10:size);
        IPage<Withdrawal> page = this.page(iPage, new QueryWrapper<Withdrawal>().eq(Withdrawal.USER_ID, userId).orderByDesc(Withdrawal.CREATE_TIME));
        List<WithdrawResVo> withdrawResVos = new ArrayList<>();
        for(Withdrawal withdrawal : page.getRecords()){
            WithdrawResVo withdrawResVo  = new WithdrawResVo();
            BeanUtils.copyProperties(withdrawal,withdrawResVo);
            withdrawResVo.setStatus(OrderStatusEnum.of(withdrawal.getStatus()).alias);
            withdrawResVos.add(withdrawResVo);
        }
        return withdrawResVos;
    }

    @Override
    public Withdrawal updateWithdrawStatus(String id, Integer status,String remark) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(id);
        withdrawal.setStatus(status);
        withdrawal.setUpdateTime(new Date());
        withdrawal.setRemark(remark);
        this.updateById(withdrawal);
        return withdrawal;
    }
}
