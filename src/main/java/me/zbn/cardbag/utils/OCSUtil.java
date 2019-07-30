package me.zbn.cardbag.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;


/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: 腾讯对象存储工具
 */
@Component
public class OCSUtil {
    @Value("${bucketName}")
    private String bucketName;
    @Value("${secretId}")
    private String secretId;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${region}")
    private String region;



    public String upload(byte[] fileByte, String fileName) {
        /**
         * 1 初始化用户身份信息（secretId, secretKey）。
         */
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        /**
         * // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
         * // clientConfig中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者接口文档 FAQ 中说明。
         */
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        /**
         * / 3 生成 cos 客户端。
         */
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传到 COS 上对象键
        ByteArrayInputStream stream = new ByteArrayInputStream(fileByte);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, stream, null);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethodName.GET);
        // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
        //// 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);
        req.setExpiration(expirationDate);
        URL downloadUrl = cosClient.generatePresignedUrl(req);
        String downloadUrlStr = downloadUrl.toString();
        cosClient.shutdown();
        return downloadUrlStr;
    }

    /*public void download() {
        // 指定要下载到的本地路径
        File downFile = new File("src/test/resources/mydown.txt");
        // 指定文件所在的存储桶
        String bucketName = "demoBucket-1250000000";
        // 指定文件在 COS 上的对象键
        String key = "upload_single_demo.txt";

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        cosClient.shutdown();
    }


    public void delete() {
        // 指定文件所在的存储桶
        String bucketName = "demoBucket-1250000000";
        // 指定文件在 COS 上的对象键
        String key = "upload_single_demo.txt";
        cosClient.deleteObject(bucketName, key);
        cosClient.shutdown();
    }*/

}
