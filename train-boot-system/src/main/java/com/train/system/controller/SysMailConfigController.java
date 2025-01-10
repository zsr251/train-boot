package com.train.system.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.train.email.config.EmailConfig;
import com.train.email.param.EmailAliyunBatchSendParam;
import com.train.email.param.EmailAliyunSendParam;
import com.train.email.param.EmailLocalSendParam;
import com.train.email.service.EmailService;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.system.convert.SysMailConfigConvert;
import com.train.system.entity.SysMailConfigEntity;
import com.train.system.enums.MailFormatEnum;
import com.train.system.enums.MailPlatformEnum;
import com.train.system.query.SysMailConfigQuery;
import com.train.system.service.SysMailConfigService;
import com.train.system.vo.SysMailConfigVO;
import com.train.system.vo.SysMailSendVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮件配置
 *
 */
@RestController
@RequestMapping("sys/mail/config")
@Tag(name = "邮件配置")
@AllArgsConstructor
public class SysMailConfigController {
    private final SysMailConfigService sysMailConfigService;
    private final EmailService emailService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<PageResult<SysMailConfigVO>> page(@ParameterObject @Valid SysMailConfigQuery query) {
        PageResult<SysMailConfigVO> page = sysMailConfigService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public Result<List<SysMailConfigVO>> list(Integer platform) {
        List<SysMailConfigVO> list = sysMailConfigService.list(platform);

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<SysMailConfigVO> get(@PathVariable("id") Long id) {
        SysMailConfigEntity entity = sysMailConfigService.getById(id);

        return Result.ok(SysMailConfigConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<String> save(@RequestBody SysMailConfigVO vo) {
        sysMailConfigService.save(vo);

        return Result.ok();
    }

    @PostMapping("send")
    @Operation(summary = "发送邮件")
    @OperateLog(type = OperateTypeEnum.OTHER)
    public Result<String> send(@RequestBody SysMailSendVO vo) {
        SysMailConfigEntity entity = sysMailConfigService.getById(vo.getId());
        EmailConfig config = SysMailConfigConvert.INSTANCE.convert2(entity);

        // 发送本地邮件
        if (vo.getPlatform() == MailPlatformEnum.LOCAL.getValue()) {
            EmailLocalSendParam local = new EmailLocalSendParam();
            local.setTos(vo.getMailTos());
            local.setSubject(vo.getSubject());
            local.setContent(vo.getContent());
            local.setHtml(StrUtil.equalsIgnoreCase(vo.getMailFormat(), MailFormatEnum.HTML.name()));
            boolean flag = emailService.sendLocal(local, config);

            return flag ? Result.ok() : Result.error("发送失败");
        }


        // 发送阿里云模板邮件
        if (vo.getPlatform() == MailPlatformEnum.ALIYUN.getValue()
                && StrUtil.equalsIgnoreCase(vo.getMailFormat(), MailFormatEnum.TEMPLATE.name())) {
            EmailAliyunBatchSendParam aliyun = new EmailAliyunBatchSendParam();
            aliyun.setFrom(vo.getMailFrom());
            aliyun.setReceiversName(vo.getReceiversName());
            aliyun.setTagName(vo.getTagName());
            aliyun.setTemplateName(vo.getTemplateName());
            boolean flag = emailService.batchSendAliyun(aliyun, config);

            return flag ? Result.ok() : Result.error("发送失败");
        }

        // 发送阿里云邮件
        if (vo.getPlatform() == MailPlatformEnum.ALIYUN.getValue()) {
            EmailAliyunSendParam aliyun = new EmailAliyunSendParam();
            aliyun.setFrom(vo.getMailFrom());
            aliyun.setFormAlias(vo.getFormAlias());
            aliyun.setTos(vo.getMailTos());
            aliyun.setSubject(vo.getSubject());
            aliyun.setContent(vo.getContent());
            aliyun.setHtml(StrUtil.equalsIgnoreCase(vo.getMailFormat(), MailFormatEnum.HTML.name()));
            boolean flag = emailService.sendAliyun(aliyun, config);

            return flag ? Result.ok() : Result.error("发送失败");
        }

        return Result.error("不支持的邮件平台或邮件格式");
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<String> update(@RequestBody @Valid SysMailConfigVO vo) {
        sysMailConfigService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysMailConfigService.delete(idList);

        return Result.ok();
    }
}