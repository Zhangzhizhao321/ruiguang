package com.dream.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.common.utils.FuiouUtil;
import com.dream.chat.entity.Bank;
import com.dream.chat.entity.Institution;
import com.dream.chat.entity.UserBank;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.BankMapper;
import com.dream.chat.mapper.UserBankMapper;
import com.dream.chat.service.InstitutionService;
import com.dream.chat.service.UserBankService;
import com.dream.chat.service.UserService;
import com.dream.chat.vo.req.InstitutionReqVo;
import com.dream.chat.vo.req.UserBankReqVo;
import com.dream.chat.vo.res.UserBankResVo;
import com.dream.common.core.api.R;
import com.dream.common.core.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-05-10
 */
@Service
public class UserBankServiceImpl extends SuperServiceImpl<UserBankMapper, UserBank> implements UserBankService {

    @Resource
    private BankMapper bankMapper;

    @Resource
    private UserBankMapper userBankMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @Override
    public R verifyBankCard(UserBankReqVo userBankReqVo) {
        Bank bank = bankMapper.selectById(userBankReqVo.getBankNo());
        if (bank == null) {
            return R.failed("没有找到该银行信息");
        }
        Map<String, Object> result = FuiouUtil.editContract(userBankReqVo);
        return R.ok(result);
    }

    @Override
    @Transactional
    public R bindBankCard(UserBankReqVo userBankReqVo) {
        boolean flag = FuiouUtil.verifyMsg(userBankReqVo);
        if (flag) {
            UserBank userBank = this.addUserBank(userBankReqVo);
            userService.bindUserIdCard(userBankReqVo.getUserId(),userBankReqVo.getIdCard());
            if (userBank != null) {
                return R.ok("绑定成功");
            }
        }
        return R.failed("绑定失败");
    }

    @Override
    public UserBank addUserBank(UserBankReqVo userBankReqVo) {
        UserBank bank = this.getOne(new QueryWrapper<UserBank>().eq(UserBank.USER_ID,userBankReqVo.getUserId()).eq(UserBank.BANK_NUM,userBankReqVo.getBankNum()));
        if(bank != null){
            return null;
        }
        UserBank userBank = new UserBank();
        BeanUtil.copyProperties(userBankReqVo, userBank);
        userBank.setCreateTime(new Date());
        this.save(userBank);
        return userBank;
    }

    @Override
    public List<UserBankResVo> getMyBankCards(String userId) {
        List<UserBankResVo> userBankResVos = userBankMapper.getMyCardList(userId);
        for(UserBankResVo userBankResVo : userBankResVos){
            String bankNum = userBankResVo.getBankNum();
            String pNum = bankNum.substring(0,bankNum.length()-4);
            String sNum = bankNum.substring(bankNum.length()-4);

            Pattern p2 = Pattern.compile("[0-9]"); // 只允数字
            Matcher m = p2.matcher(pNum);
            String trim = m.replaceAll("*").trim();

            userBankResVo.setBankNum(trim+sNum);
        }
        return userBankResVos;
    }

    @Override
    public R bindInstitutionBankCard(UserBankReqVo userBankReqVo) {
        Institution institution = institutionService.getById(userBankReqVo.getUserId());
        if(institution == null){
            return R.failed("未找到该信息");
        }
        if(StringUtils.isNotBlank(institution.getBankNum())){
            return R.failed("已绑定银行卡，如需换卡请联系管理员");
        }
        boolean flag = FuiouUtil.verifyMsg(userBankReqVo);
        if (flag) {
            institution.setBankName(userBankReqVo.getBankName());
            institution.setBankNo(userBankReqVo.getBankNo());
            institution.setBankNum(userBankReqVo.getBankNum());
            institution.setIdCard(userBankReqVo.getIdCard());
            institution.setUpdateTime(new Date());
            institutionService.updateById(institution);
        }
        return R.failed("绑定失败");
    }
}
