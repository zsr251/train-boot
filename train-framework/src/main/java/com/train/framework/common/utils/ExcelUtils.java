package com.train.framework.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.train.framework.common.excel.CommonDataListener;
import com.train.framework.common.excel.ExcelDataListener;
import com.train.framework.common.excel.ExcelFinishCallBack;
import com.train.framework.common.excel.NoSaveDataListener;
import com.train.framework.common.exception.ExcelException;
import com.train.framework.common.query.Query;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The type Excel utils.
 * {@link <a href="https://easyexcel.opensource.alibaba.com/"></a>}
 *
 * @author eden
 */
@Slf4j
public class ExcelUtils {
    /**
     * 写入时每页个数
     */
    public static final int WRITE_PAGE_SIZE = 500;
    /**
     * 读取excel文件
     *
     * @param <T>      数据类型
     * @param file     excel文件
     * @param head     列名
     * @param callBack 回调 导入时传入定义好的回调接口，excel数据解析完毕之后监听器将数据传入回调函数
     *                 这样调用工具类时可以通过回调函数获取导入的数据，如果数据量过大可根据实际情况进行分配入库
     */
    public static <T> void readAnalysis(MultipartFile file, Class<T> head, ExcelFinishCallBack<T> callBack) {
        try {
            EasyExcel.read(file.getInputStream(), head, new ExcelDataListener<>(callBack)).sheet().doRead();
        } catch (IOException e) {
            log.error("readAnalysis error", e);
        }
    }

    /**
     * 读取excel文件
     *
     * @param <T>      数据类型
     * @param file     excel文件
     * @param head     列名
     * @param callBack 回调 导入时传入定义好的回调接口，excel数据解析完毕之后监听器将数据传入回调函数
     *                 这样调用工具类时可以通过回调函数获取导入的数据，如果数据量过大可根据实际情况进行分配入库
     */
    public static <T> void readAnalysis(File file, Class<T> head, ExcelFinishCallBack<T> callBack) {
        try {
            EasyExcel.read(new FileInputStream(file), head, new ExcelDataListener<>(callBack)).sheet().doRead();
        } catch (IOException e) {
            log.error("readAnalysis error", e);
        }
    }

    /**
     * 读取excel文件 同步
     *
     * @param <T>   数据类型
     * @param file  文件
     * @param clazz 模板类
     * @return java.util.List
     */
    public static <T> List<T> readSync(File file, Class<T> clazz) {
        return readSync(file, clazz, 1, 0, ExcelTypeEnum.XLSX);
    }

    /**
     * 读取excel文件 同步
     *
     * @param <T>       数据类型
     * @param file      文件
     * @param clazz     模板类
     * @param rowNum    数据开始行 1
     * @param sheetNo   第几张表
     * @param excelType 数据表格式类型
     * @return java.util.List list
     */
    public static <T> List<T> readSync(File file, Class<T> clazz, Integer rowNum, Integer sheetNo, ExcelTypeEnum excelType) {
        return EasyExcel.read(file).headRowNumber(rowNum).excelType(excelType).head(clazz).sheet(sheetNo).doReadSync();
    }


    /**
     * 读取Excel 不进行处理
     *
     * @param inputStream Excel文件
     * @param clz         读取对象类型 Bean上不能使用 @accessors(chain = true) 注解
     * @param <T>         读取对象类型 Bean上不能使用 @accessors(chain = true) 注解
     * @return 读取记录
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clz) {
        return readExcel(inputStream, clz, 0);
    }

    /**
     * 读取Excel 不进行处理
     *
     * @param inputStream Excel文件
     * @param clz         读取对象类型 Bean上不能使用 @accessors(chain = true) 注解
     * @param <T>         读取对象类型 Bean上不能使用 @accessors(chain = true) 注解
     * @param sheetNo     Sheet编号
     * @return 读取记录
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clz, int sheetNo) {
        ExcelReader excelReader = null;
        NoSaveDataListener<T> noSaveDataListener = new NoSaveDataListener<T>();
        try {
            excelReader = EasyExcel.read(inputStream).head(clz).registerReadListener(noSaveDataListener)
                    .autoCloseStream(true).ignoreEmptyRow(true)
                    .build();
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
            excelReader.read(readSheet);
        } catch (ExcelException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ExcelException("Excel读取失败", e);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        return noSaveDataListener.getList();
    }


    /**
     * 读取Excel 并进行处理
     *
     * @param inputStream Excel文件
     * @param clz         读取对象类型 Bean上不能使用 @accessors(chain = true) 注解
     * @param service     服务类
     * @param methodName  指定方法名 方法参数为 list 返回值为int
     * @param <M>         服务实现类
     * @param <T>         读取对象类型 Bean上不能使用 @accessors(chain = true) 注解
     * @return 读取并成功处理条数
     */
    public static <M, T> int readExcel(InputStream inputStream, Class<T> clz, M service, String methodName) {
        ExcelReader excelReader = null;
        try {
            CommonDataListener<M, T> commonDataListener = new CommonDataListener<M, T>(service, methodName);
            excelReader = EasyExcel.read(inputStream).head(clz).registerReadListener(commonDataListener)
                    .autoCloseStream(true).ignoreEmptyRow(true)
                    .build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            return commonDataListener.getSuccessCount();
        } catch (ExcelException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ExcelException("Excel读取失败", e);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /**
     * 导出数据到文件
     *
     * @param <T>  数据类型
     * @param head 类名
     * @param file 导入到文件
     * @param data 数据
     */
    public static <T> void excelExport(Class<T> head, File file, List<T> data) {
        excelExport(head, file, "sheet1", data);
    }

    /**
     * 导出数据到文件
     *
     * @param <T>       写入格式
     * @param head      类名
     * @param file      写入到文件
     * @param sheetName sheet名称
     * @param data      数据列表
     */
    public static <T> void excelExport(Class<T> head, File file, String sheetName, List<T> data) {
        try {
            EasyExcel.write(file, head).sheet(sheetName).registerConverter(new LongStringConverter()).doWrite(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出数据到web
     * 文件下载（失败了会返回一个有部分数据的Excel）
     *
     * @param head      类名
     * @param excelName excel名字
     * @param sheetName sheet名称
     * @param data      数据
     */
    public static <T> void excelExport(Class<T> head, String excelName, String sheetName, List<T> data) {
        try {
            HttpServletResponse response = getExportResponse(excelName);

            EasyExcel.write(response.getOutputStream(), head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
                    .registerConverter(new LongStringConverter()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出数据到web
     * 文件下载（失败了会返回一个有部分数据的Excel）
     *
     * @param head      类名
     * @param excelName excel名字
     * @param sheetName sheet名称
     * @param data      数据
     */
    public static <T> void excelExport(List<List<String>> head, String excelName, String sheetName, List<T> data) {
        try {
            HttpServletResponse response = getExportResponse(excelName);

            EasyExcel.write(response.getOutputStream()).head(head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
                    .registerConverter(new LongStringConverter()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 导出Excel
     *
     * @param targetObj    导出的目标 File / HttpServletResponse
     * @param clz          写入对象类型
     * @param excelName    指定Excel的名称
     * @param excludeFiled 排除的字段
     * @param includeFiled 包含的字段
     * @param <T>          写入对象类型
     */
    public static <T> void writeListToExcel(Object targetObj, Class<T> clz, List<T> list, String excelName, Set<String> excludeFiled, Set<String> includeFiled) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        ExcelWriter excelWriter = null;
        try {
            ExcelWriterBuilder builder = null;
            if(targetObj instanceof File) {
                File targetFile = (File) targetObj;
                // 这里 需要指定写用哪个class去写
                builder = EasyExcelFactory.write(targetFile, clz);
            }else {
                HttpServletResponse response = (HttpServletResponse) targetObj;
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
                String fileName = StrUtil.isBlank(excelName) ? "export-excel" : URLEncoder.encode(excelName, "UTF-8");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
                // 这里 需要指定写用哪个class去写
                builder = EasyExcelFactory.write(response.getOutputStream(), clz);
            }

            // 这里 需要指定写用哪个class去写
            if (includeFiled != null && !includeFiled.isEmpty()) {
                builder.includeColumnFiledNames(includeFiled);
            }
            if (excludeFiled != null && !excludeFiled.isEmpty()) {
                builder.excludeColumnFiledNames(excludeFiled);
            }
            excelWriter = builder.build();

            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("数据").build();
            list = list == null ? new ArrayList<>() : list;
            excelWriter.write(list, writeSheet);
        } catch (Exception e) {
            throw new ExcelException("Excel导出失败", e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }


    /**
     * 导出Excel
     *
     * @param targetObj    目标类型 文件/HttpServletResponse
     * @param service      服务类
     * @param clz          写入对象类型
     * @param methodName   指定方法名 参数和返回值参考 page方法
     * @param queryObj     搜索对象
     * @param excelName    指定Excel的名称
     * @param excludeFiled 排除的字段
     * @param includeFiled 包含的字段
     * @param <M>          服务类
     * @param <T>          写入对象类型
     * @param <Q>          搜索参数对象类型
     */
    public static <M, T, Q extends Query> void writeExcel(Object targetObj, Class<T> clz, M service, String methodName, Class<Q> clq, Q queryObj, String excelName, Set<String> excludeFiled, Set<String> includeFiled, Integer pageSize) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        ExcelWriter excelWriter = null;
        pageSize = pageSize == null || pageSize < 1 ? WRITE_PAGE_SIZE : pageSize;
        try {
            ExcelWriterBuilder builder = null;
            if(targetObj instanceof File) {
                File targetFile = (File) targetObj;
                // 这里 需要指定写用哪个class去写
                builder = EasyExcelFactory.write(targetFile, clz);
            }else {
                HttpServletResponse response = (HttpServletResponse) targetObj;
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
                String fileName = StrUtil.isBlank(excelName) ? "export—excel" : URLEncoder.encode(excelName, "UTF-8");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
                // 这里 需要指定写用哪个class去写
                builder = EasyExcelFactory.write(response.getOutputStream(), clz);
            }
            if (includeFiled != null && !includeFiled.isEmpty()) {
                builder.includeColumnFiledNames(includeFiled);
            }
            if (excludeFiled != null && !excludeFiled.isEmpty()) {
                builder.excludeColumnFiledNames(excludeFiled);
            }
            excelWriter = builder.build();

            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("数据").build();

            queryObj.setPage(1);
            queryObj.setLimit(pageSize);
            PageResult<T> pageList = ReflectUtil.invoke(service, methodName, queryObj);
            // 第一页
            excelWriter.write(pageList.getList(), writeSheet);
            int totalPages = pageList.getTotal() % pageSize == 0 ? pageList.getTotal() / pageSize : pageList.getTotal() / pageSize + 1;
            // 第二页到最后一页
            for (int i = 2; i <= totalPages; i++) {
                queryObj.setPage(i);
                queryObj.setLimit(pageSize);
                pageList = ReflectUtil.invoke(service, methodName, queryObj);
                excelWriter.write(pageList.getList(), writeSheet);
            }
        } catch (Exception e) {
            throw new ExcelException("Excel导出失败", e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    /**
     * 导出Excel
     *
     * @param response
     * @param service      服务类
     * @param clz          写入对象类型
     * @param methodName   指定方法名 参数和返回值参考 page方法
     * @param queryObj     搜索对象
     * @param excelName    指定Excel的名称
     * @param excludeFiled 排除的字段
     * @param includeFiled 包含的字段
     * @param <M>          服务类
     * @param <T>          写入对象类型
     * @param <Q>          搜索参数对象类型
     */
    public static <M, T, Q extends Query> void writeExcel(HttpServletResponse response, Class<T> clz, M service, String methodName, Class<Q> clq, Q queryObj, String excelName, Set<String> excludeFiled, Set<String> includeFiled) {
        writeExcel(response, clz, service, methodName, clq, queryObj, excelName, excludeFiled, includeFiled, null);
    }

    /**
     * 过去导出文件名
     * @param excelName
     * @return
     */
    private static HttpServletResponse getExportResponse(String excelName) {
        HttpServletResponse response = HttpContextUtils.getHttpServletResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setCharacterEncoding("UTF-8");

        excelName += DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String fileName = URLUtil.encode(excelName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        return response;
    }

}
