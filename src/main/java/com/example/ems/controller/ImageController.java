package com.example.ems.controller;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author guoce
 * @Date 18-4-23 下午6:33
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private Environment env;

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap upload(@RequestParam(value = "file") MultipartFile file) {
        ModelMap mp = new ModelMap();
        String fileName = file.getOriginalFilename();
        String filePath = env.getProperty("path.download");
        File dest = new File(filePath+fileName);
        try {
            file.transferTo(dest);
            mp.addAttribute("result",fileName+"上传完成");
        } catch (Exception e) {
            e.printStackTrace();
            mp.addAttribute("result",fileName+"上传失败\n"+e.getMessage());
        }
        return mp;
    }

    @RequestMapping(value="/download", method=RequestMethod.GET,produces="application/zip")
    public boolean downloadFile(HttpServletResponse response) throws IOException {
        System.out.println("\n********** Download File : ************\n");
        byte[] buffer = new byte[1024];
        String zipName = "image"+new Date().getTime()+".zip";
        String path = env.getProperty("path.download");
        String strZipPath=path+"/"+zipName;

        File dirFile = new File(path);
        //遍历该目录下的所有png
        String[] fileList = dirFile.list();
        List<File> file1 =new ArrayList<File>();

        for (int i = 0; i < fileList.length; i++) {
            //遍历文件目录
            String string = fileList[i];
            File file = new File(dirFile.getPath(),string);
            String name = file.getName();
            if(name.endsWith(".png")){
                file1.add(file);
            }
        }
        try{
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
            for (int i = 0; i < file1.size(); i++) {
                FileInputStream fis = new FileInputStream(file1.get(i));
                out.putNextEntry(new ZipEntry(file1.get(i).getName()));
                //设置压缩文件内的字符编码，不然会变成乱码
                out.setEncoding("UTF-8");
                int len;
                // 读入需要下载的文件的内容，打包到zip文件
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                fis.close();
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipName, "UTF-8"));


        File file = new File(strZipPath);

        // 以流的形式下载文件。
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
        byte[] buffer1 = new byte[fis.available()];
        fis.read(buffer1);
        fis.close();
        // 清空response
        response.reset();
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + zipName);
        toClient.write(buffer1);
        toClient.flush();
        toClient.close();

        return true;
    }
}
