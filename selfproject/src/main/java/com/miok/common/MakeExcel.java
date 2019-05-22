package com.miok.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class MakeExcel {
    static final Logger LOGGER = LoggerFactory.getLogger(AdminInterceptor.class);

    public MakeExcel() {}
    
    // 다운로드 일자를 파일이름에 추가
    public String get_Filename() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddmmmm");
        return ft.format(new Date());
    }

    // String 파라미터를 전달 받아 파일이름 앞에 추가 
    public String get_Filename(String pre) {
        return pre + get_Filename();
    }
    
    // 엑셀파일 다운로드
    public void download(HttpServletRequest request, HttpServletResponse response, Map<String , Object> beans, String filename, String templateFile) {
        // 템플릿 파일이 있는 위치 지정
    	String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/templete");
        
        try {
        	// 템플릿 파일을 입력
            InputStream is = new BufferedInputStream(new FileInputStream(tempPath + "/" + templateFile));
            XLSTransformer transformer = new XLSTransformer();
            Workbook resultWorkbook = transformer.transformXLS(is, beans);
            
            // 파일 다운로드 대화상자가 뜨도록 헤더를 설정
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".xlsx\"");
            
            // 결과를 출력
            OutputStream os = response.getOutputStream();
            resultWorkbook.write(os);
            
        } catch (ParsePropertyException | InvalidFormatException | IOException ex) {
            LOGGER.error("MakeExcel");
        }
    }
}
